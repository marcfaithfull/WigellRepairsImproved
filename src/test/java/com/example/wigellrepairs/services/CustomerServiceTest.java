package com.example.wigellrepairs.services;

import com.example.wigellrepairs.dto.ServiceDto;
import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.Service;
import com.example.wigellrepairs.repositories.BookingsRepository;
import com.example.wigellrepairs.repositories.ServicesRepository;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private BookingsRepository bookingsRepository;

    @Mock
    private ServicesRepository servicesRepository;

    @Mock
    private Principal principal;

    @Mock
    private static Logger CUSTOMER_SERVICE_LOGGER;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void ShouldReturnListOfServices() {
        // Arrange
        Service testServiceOne = new Service();
        testServiceOne.setWigellRepairsServiceId(1L);
        testServiceOne.setWigellRepairsServiceName("Service A");
        testServiceOne.setWigellRepairsServiceType("Car");
        testServiceOne.setWigellRepairsServicePrice(1000);

        Service testServiceTwo = new Service();
        testServiceTwo.setWigellRepairsServiceId(2L);
        testServiceTwo.setWigellRepairsServiceName("Service B");
        testServiceTwo.setWigellRepairsServiceType("Electronics");
        testServiceTwo.setWigellRepairsServicePrice(2000);
        List<Service> servicesTestList = Arrays.asList(testServiceOne, testServiceTwo);

        when(servicesRepository.findAll()).thenReturn(servicesTestList);

        // Expected
        List<ServiceDto> expected = ServiceDto.serviceDtoList(servicesTestList);

        // Act
        List<ServiceDto> actual = customerService.services();

        // Assert
        assertEquals(expected, actual);
        verify(servicesRepository, times(1)).findAll();
    }

    @Test
    void ShouldReturnBadRequest_WhenServiceIsNull() {
        // Arrange
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "Darth Vader";
            }
        };
        Long testId = 1L;
        Booking booking = new Booking();
        Service service = new Service();
        service.setWigellRepairsServiceId(testId);
        booking.setWigellRepairsBookingService(service);

        when(servicesRepository.findServiceByWigellRepairsServiceId(testId)).thenReturn(null);

        // Act
        ResponseEntity<String> response = customerService.bookService(booking, principal);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("There is no service with this id", response.getBody());
        verify(servicesRepository).findServiceByWigellRepairsServiceId(testId);
    }

    @Test
    void ShouldReturnBadRequest_WhenDateIsInPast() {
        // Arrange
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "Han Solo";
            }
        };
        Booking invalidBooking = new Booking();
        Service service = new Service();
        service.setWigellRepairsServiceId(1L);
        invalidBooking.setWigellRepairsBookingDate(LocalDate.now().minusDays(1));
        invalidBooking.setWigellRepairsBookingService(service);
        invalidBooking.getWigellRepairsBookingService().setWigellRepairsServiceId(1L);

        when(servicesRepository.findServiceByWigellRepairsServiceId(1L)).thenReturn(service);

        // Act
        ResponseEntity<String> response = customerService.bookService(invalidBooking, principal);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("You cannot book using a past date", response.getBody());
        verify(servicesRepository, times(1)).findServiceByWigellRepairsServiceId(any());
    }

    @Test
    void ShouldReturnBadRequest_WhenDateNotAvailable() {
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "Chewbacca";
            }
        };
        LocalDate futureDate = LocalDate.now().plusWeeks(1);

        Booking booking = new Booking();
        booking.setWigellRepairsBookingDate(futureDate);

        Service service = new Service();
        service.setWigellRepairsServiceId(1L);
        booking.setWigellRepairsBookingService(service);

        Booking existingBooking = new Booking();
        existingBooking.setWigellRepairsBookingDate(futureDate);
        existingBooking.setWigellRepairsBookingId(1L);

        List<Booking> allBookings = List.of(existingBooking);

        when(servicesRepository.findServiceByWigellRepairsServiceId(anyLong())).thenReturn(service);
        when(bookingsRepository.findAll()).thenReturn(allBookings);

        ResponseEntity<String> response = customerService.bookService(booking, principal);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("This date is not available. Try another date", response.getBody());
        verify(servicesRepository, times(1)).findServiceByWigellRepairsServiceId(anyLong());
        verify(bookingsRepository, times(1)).findAll();
    }

    @Test
    void ShouldReturnCreated_WhenMakingABooking() {
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "C3P0";
            }
        };
        Booking booking = new Booking();
        booking.setWigellRepairsBookingId(1L);
        booking.setWigellRepairsBookingDate(LocalDate.now());
        Service service = new Service();
        service.setWigellRepairsServiceId(1L);
        booking.setWigellRepairsBookingService(service);

        when(servicesRepository.findServiceByWigellRepairsServiceId(service.getWigellRepairsServiceId())).thenReturn(service);
        when(bookingsRepository.save(booking)).thenReturn(booking);

        ResponseEntity<String> response = customerService.bookService(booking, principal);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("The service has been booked", response.getBody());
        verify(bookingsRepository, times(1)).save(booking);
    }

    @Test
    void ShouldReturnBadRequest_WhenIdIsNotFound() {
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "Sand Person";
            }
        };
        Booking booking = new Booking();
        booking.setWigellRepairsBookingId(999L);

        when(bookingsRepository.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<String> result = customerService.cancelBooking(booking, principal);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("There is no booking with this id", result.getBody());

        verify(bookingsRepository, times(1)).findById(999L);
        verify(bookingsRepository, never()).save(any(Booking.class));
    }

    @Test
    void ShouldReturnForbidden_WhenUserIsNotAuthorised() {
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "R2D2";
            }
        };
        Booking booking = new Booking();
        booking.setWigellRepairsBookingId(1L);
        booking.setWigellRepairsBookingDate(LocalDate.now().plusDays(1));
        booking.setWigellRepairsBookingCancelled(true);
        booking.setWigellRepairsBookingCustomer("NotR2D2");

        when(bookingsRepository.findById(1L)).thenReturn(Optional.of(booking));
        //when(bookingsRepository.save(any(Booking.class))).thenReturn(booking);

        ResponseEntity<String> result = customerService.cancelBooking(booking, principal);

        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
        assertEquals("You are not authorised to cancel this booking", result.getBody());

        verify(bookingsRepository, times(1)).findById(booking.getWigellRepairsBookingId());
        verify(bookingsRepository, times(0)).save(any(Booking.class));
    }

    @Test
    void myBookings() {
    }
}