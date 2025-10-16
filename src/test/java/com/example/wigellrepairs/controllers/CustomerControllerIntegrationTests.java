package com.example.wigellrepairs.controllers;

import com.example.wigellrepairs.dto.BookingRequestDto;
import com.example.wigellrepairs.dto.ServiceDto;
import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.ServiceEntity;
import com.example.wigellrepairs.entities.Technician;
import com.example.wigellrepairs.repositories.BookingRepository;
import com.example.wigellrepairs.repositories.ServiceRepository;
import com.example.wigellrepairs.repositories.TechnicianRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CustomerControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private BookingRepository bookingRepository;

    private Technician savedTech;
    private ServiceEntity savedService;

    @BeforeEach
    void setUp() {
        bookingRepository.deleteAll();
        serviceRepository.deleteAll();
        technicianRepository.deleteAll();

        savedTech = technicianRepository.save(new Technician("John", "Electronics"));

        savedService = new ServiceEntity();
        savedService.setWigellRepairsServiceName("Phone Repair");
        savedService.setWigellRepairsServiceType("Electronics");
        savedService.setWigellRepairsServicePrice(1000);
        savedService.setWigellRepairsServiceTechnician(savedTech);
        serviceRepository.save(savedService);
    }

    @Test()
    @WithMockUser(username = "Kurt", roles = "USER")
    void ShouldReturnListOfServices_WhilstUsingAuthorisedUser() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders.get("/api/wigellrepairs/services"))
                .andExpect(status().isOk())
                .andReturn();

        var jsonResponse = response.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        List<ServiceDto> services = mapper.readValue(jsonResponse, new TypeReference<List<ServiceDto>>() {
        });

        assertFalse(services.isEmpty());
        assertTrue(services.stream().anyMatch(s -> s.getServiceName().equals("Phone Repair")));
    }

    @Test
    @WithMockUser(username = "Kurt", roles = "USER")
    void ShouldReturnCreated_WhenServiceBookedWithCorrectUser() throws Exception {
        assertNotNull(savedService, "Service should not be null");

        BookingRequestDto bookingRequestDto = new BookingRequestDto();
        bookingRequestDto.setCustomer("Kurt");
        bookingRequestDto.setDateOfService(LocalDate.now().plusDays(10));
        bookingRequestDto.setServiceId(savedService.getWigellRepairsServiceId());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonResponse = mapper.writeValueAsString(bookingRequestDto);

        mockMvc.perform(post("/api/wigellrepairs/bookservice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonResponse))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("The service was successfully booked"));

        assertEquals(1, bookingRepository.findAll().size());
    }

    @Test
    @WithMockUser(username = "Courtney", roles = "ADMIN")
    void ShouldReturnForbidden_WhenAdminTriesToMakeABooking() throws Exception {
        BookingRequestDto bookingRequestDto = new BookingRequestDto();
        bookingRequestDto.setCustomer("Courtney");
        bookingRequestDto.setDateOfService(LocalDate.now().plusDays(10));
        bookingRequestDto.setServiceId(savedService.getWigellRepairsServiceId());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonResponse = mapper.writeValueAsString(bookingRequestDto);

        mockMvc.perform(post("/api/wigellrepairs/bookservice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonResponse))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "Kurt", roles = "USER")
    void ShouldReturnBadRequest_WhenBookingWithAServiceThatDoesNotExist() throws Exception {
        BookingRequestDto bookingRequestDto = new BookingRequestDto();
        bookingRequestDto.setCustomer("Kurt");
        bookingRequestDto.setDateOfService(LocalDate.now().plusDays(10));
        bookingRequestDto.setServiceId(999L);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonResponse = mapper.writeValueAsString(bookingRequestDto);

        mockMvc.perform(post("/api/wigellrepairs/bookservice")
        .contentType(MediaType.APPLICATION_JSON)
                .content(jsonResponse))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("There is no service with this id in the database"));
    }

    @Test
    @WithMockUser(username = "Kurt", roles = "USER")
    void ShouldReturnBadRequest_WhenBookingDateIsInThePast() throws Exception {
        BookingRequestDto bookingRequestDto = new BookingRequestDto();
        bookingRequestDto.setCustomer("Kurt");
        bookingRequestDto.setDateOfService(LocalDate.now().minusDays(10));
        bookingRequestDto.setServiceId(savedService.getWigellRepairsServiceId());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonResponse = mapper.writeValueAsString(bookingRequestDto);

        mockMvc.perform(post("/api/wigellrepairs/bookservice")
        .contentType(MediaType.APPLICATION_JSON)
                .content(jsonResponse))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("You cannot book using a past date"));
    }

    @Test
    @WithMockUser(username = "Kurt", roles = "USER")
    void ShouldReturnBadRequest_WhenDateIsUnavailable() throws Exception {
        Booking oldBooking = new Booking();
        oldBooking.setWigellRepairsBookingService(savedService);
        oldBooking.setWigellRepairsBookingDate(LocalDate.now().plusDays(10));
        bookingRepository.save(oldBooking);

        BookingRequestDto newBooking = new BookingRequestDto();
        newBooking.setCustomer("Kurt");
        newBooking.setDateOfService(LocalDate.now().plusDays(10));
        newBooking.setServiceId(savedService.getWigellRepairsServiceId());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonResponse = mapper.writeValueAsString(newBooking);

        mockMvc.perform(post("/api/wigellrepairs/bookservice")
        .contentType(MediaType.APPLICATION_JSON)
                .content(jsonResponse))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("This date is not available. Try another date"));
    }

    @Test
    @WithMockUser(username = "Kurt", roles = "USER")
    void ShouldReturn500_SomethingUnexpectedHappens_DateRequestWasRemoved() throws Exception {
        BookingRequestDto bookingRequestDto = new BookingRequestDto();
        bookingRequestDto.setCustomer("Kurt");
        bookingRequestDto.setServiceId(savedService.getWigellRepairsServiceId());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonResponse = mapper.writeValueAsString(bookingRequestDto);

        mockMvc.perform(post("/api/wigellrepairs/bookservice")
        .contentType(MediaType.APPLICATION_JSON)
                .content(jsonResponse))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(username = "Kurt", roles = "USER")
    void ShouldReturn200_WhenBookingIsCancelled() throws Exception {
        Booking currentBooking = new Booking();
        currentBooking.setWigellRepairsBookingCustomer("Kurt");
        currentBooking.setWigellRepairsBookingDate(LocalDate.now().plusDays(10));
        currentBooking.setWigellRepairsBookingService(savedService);
        currentBooking.setWigellRepairsBookingCancelled(false);
        bookingRepository.save(currentBooking);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonResponse = mapper.writeValueAsString(currentBooking);

        mockMvc.perform(put("/api/wigellrepairs/cancelbooking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonResponse))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Booking has been cancelled"));
    }
}