package com.example.wigellrepairs.services;

import com.example.wigellrepairs.dto.BookingDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingsRepository bookingsRepository;

    @Mock
    private ServicesRepository servicesRepository;

    @Mock
    private Principal principal;

    @Mock
    private static Logger CUSTOMER_SERVICE_LOGGER;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @InjectMocks
    private ServicesServiceImpl serviceService;

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
        ResponseEntity<String> response = bookingService.bookService(booking, principal);

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
        ResponseEntity<String> response = bookingService.bookService(invalidBooking, principal);

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

        ResponseEntity<String> response = bookingService.bookService(booking, principal);

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

        ResponseEntity<String> response = bookingService.bookService(booking, principal);

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

        ResponseEntity<String> result = bookingService.cancelBooking(booking, principal);

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

        ResponseEntity<String> result = bookingService.cancelBooking(booking, principal);

        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
        assertEquals("You are not authorised to cancel this booking", result.getBody());

        verify(bookingsRepository, times(1)).findById(booking.getWigellRepairsBookingId());
        verify(bookingsRepository, times(0)).save(any(Booking.class));
    }

    @Test
    void ShouldReturnOk_WhenUserIsAuthorised() {
        Principal principal = new Principal() {
            @Override
            public String getName() {return "R2D2";}
        };
        Booking booking = new Booking();
        booking.setWigellRepairsBookingId(1L);
        booking.setWigellRepairsBookingDate(LocalDate.now().plusDays(1));
        booking.setWigellRepairsBookingCancelled(true);
        booking.setWigellRepairsBookingCustomer("R2D2");
        when(bookingsRepository.findById(1L)).thenReturn(Optional.of(booking));
        ResponseEntity<String> result = bookingService.cancelBooking(booking, principal);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Your booking has been cancelled", result.getBody());
        verify(bookingsRepository, times(1)).findById(1L);
        verify(bookingsRepository, times(1)).save(booking);
    }

    @Test
    void ShouldReturnForbidden_WhenItIsTooLate() {
        Principal principal = new Principal() {
            @Override
            public String getName() {return "R2D2";}
        };
        Booking booking = new Booking();
        booking.setWigellRepairsBookingId(1L);
        booking.setWigellRepairsBookingDate(LocalDate.now().minusDays(1));
        booking.setWigellRepairsBookingCancelled(true);
        booking.setWigellRepairsBookingCustomer("R2D2");
        when(bookingsRepository.findById(1L)).thenReturn(Optional.of(booking));
        ResponseEntity<String> result = bookingService.cancelBooking(booking, principal);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
        assertEquals("It is too late to cancel this booking", result.getBody());
        verify(bookingsRepository, times(1)).findById(1L);
        verify(bookingsRepository, times(0)).save(booking);
    }

    @Test
    void ShouldReturnIamATeaPot_WhenCancelNotSetToTrue() {
        Principal principal = new Principal() {
            @Override
            public String getName() {return "R2D2";}
        };
        Booking booking = new Booking();
        booking.setWigellRepairsBookingId(1L);
        booking.setWigellRepairsBookingDate(LocalDate.now().plusDays(1));
        booking.setWigellRepairsBookingCancelled(false);
        booking.setWigellRepairsBookingCustomer("R2D2");
        when(bookingsRepository.findById(1L)).thenReturn(Optional.of(booking));
        ResponseEntity<String> result = bookingService.cancelBooking(booking, principal);
        assertEquals(HttpStatus.I_AM_A_TEAPOT, result.getStatusCode());
        assertEquals("wigellRepairsBookingCancelled must be set to 'true' to cancel a booking", result.getBody());
    }

    @Test
    void ShouldReturnOK_WhenBookingIsSuccessful() {
        Principal principal = new Principal() {
            @Override
            public String getName() {return "R2D2";}
        };
        Booking booking = new Booking();
        booking.setWigellRepairsBookingId(1L);
        booking.setWigellRepairsBookingDate(LocalDate.now().plusDays(1));
        booking.setWigellRepairsBookingCancelled(true);
        booking.setWigellRepairsBookingCustomer("R2D2");
        when(bookingsRepository.findById(1L)).thenReturn(Optional.of(booking));
        ResponseEntity<String> result = bookingService.cancelBooking(booking, principal);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Your booking has been cancelled", result.getBody());
        verify(bookingsRepository, times(1)).findById(1L);
        verify(bookingsRepository, times(1)).save(booking);
    }

    @Test
    void ShouldReturnUsersBookings_WhenUserNameMatches() {
        Principal principal = new Principal() {
            @Override
            public String getName() {return "R2D2";}
        };
        Booking booking1 = new Booking();
        booking1.setWigellRepairsBookingId(1L);
        booking1.setWigellRepairsBookingDate(LocalDate.now().plusDays(1));
        booking1.setWigellRepairsBookingCancelled(false);
        booking1.setWigellRepairsBookingCustomer("R2D2");

        Service service1 = new Service();
        booking1.setWigellRepairsBookingService(service1);

        Booking booking2 = new Booking();
        booking2.setWigellRepairsBookingId(2L);
        booking2.setWigellRepairsBookingDate(LocalDate.now().plusDays(1));
        booking2.setWigellRepairsBookingCancelled(false);
        booking2.setWigellRepairsBookingCustomer("R2D2");

        Service service2 = new Service();
        booking2.setWigellRepairsBookingService(service2);

        List<Booking> allBookings = Arrays.asList(booking1, booking2);

        when(bookingsRepository.findAll()).thenReturn(allBookings);

        List<BookingDto> expected = BookingDto.bookingDtoList(allBookings);
        List<BookingDto> actual = bookingService.myBookings(principal);

        assertEquals(expected, actual);
        verify(bookingsRepository, times(1)).findAll();
    }

}