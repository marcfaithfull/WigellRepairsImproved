package com.example.wigellrepairs.controllers;

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
import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javax.security.auth.callback.ConfirmationCallback.OK;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @AfterEach
    void tearDown() {
        bookingRepository.deleteAll();
        serviceRepository.deleteAll();
        technicianRepository.deleteAll();
    }

    @Test()
    @WithMockUser(username = "Kurt", roles = "USER")
    void ShouldReturnListOfServices_WhilstUsingAuthorisedUser() throws Exception {
        Technician tech = new Technician("Josh", "Electronics");
        technicianRepository.save(tech);

        ServiceEntity service = new ServiceEntity();
        service.setWigellRepairsServiceName("Phone Repair");
        service.setWigellRepairsServiceType("Electronics");
        service.setWigellRepairsServicePrice(1000);
        service.setWigellRepairsServiceTechnician(tech);
        serviceRepository.save(service);


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
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("Kurt");

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(principal, "Cobain"));

        Technician tech = new Technician("Josh", "Electronics");
        technicianRepository.save(tech);

        ServiceEntity service = new ServiceEntity();
        service.setWigellRepairsServiceName("Phone Repair");
        service.setWigellRepairsServiceType("Electronics");
        service.setWigellRepairsServicePrice(1000);
        service.setWigellRepairsServiceTechnician(tech);
        serviceRepository.save(service);

        Booking booking = new Booking();
        booking.setWigellRepairsBookingCustomer("Kurt");
        booking.setWigellRepairsBookingDate(LocalDate.now().minusDays(10));
        booking.setWigellRepairsBookingService(service);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonResponse = mapper.writeValueAsString(booking);

        mockMvc.perform(post("/api/wigellrepairs/bookservice")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonResponse)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.service.name").value("Phone Repair"));

        assertEquals(1, bookingRepository.findAll().size());
    }

    @Test
    void cancelBooking() {
    }

    @Test
    void myBookings() {
    }
}