package com.example.wigellrepairs.services;

import com.example.wigellrepairs.dto.BookingDto;
import com.example.wigellrepairs.dto.BookingDtoFactory;
import com.example.wigellrepairs.dto.ServiceDto;
import com.example.wigellrepairs.dto.ServiceDtoFactory;
import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.ServiceEntity;
import com.example.wigellrepairs.repositories.BookingRepository;
import com.example.wigellrepairs.repositories.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceUnitTests {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private BookingDtoFactory bookingDtoFactory;

    @Mock
    private ServiceDtoFactory serviceDtoFactory;

    @InjectMocks
    private BookingServiceImpl bookingService;

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

        ServiceEntity service1 = new ServiceEntity();
        booking1.setWigellRepairsBookingService(service1);


        Booking booking2 = new Booking();
        booking2.setWigellRepairsBookingId(2L);
        booking2.setWigellRepairsBookingDate(LocalDate.now().plusDays(1));
        booking2.setWigellRepairsBookingCancelled(false);
        booking2.setWigellRepairsBookingCustomer("R2D2");

        ServiceEntity service2 = new ServiceEntity();
        booking2.setWigellRepairsBookingService(service2);

        List<Booking> bookings = Arrays.asList(booking1, booking2);

        BookingDto bookingDto1 = new BookingDto();
        bookingDto1.setBookingId(1L);
        bookingDto1.setDateOfService(booking1.getWigellRepairsBookingDate());
        bookingDto1.setCustomer(principal.getName());

        BookingDto bookingDto2 = new BookingDto();
        bookingDto2.setBookingId(2L);
        bookingDto2.setDateOfService(booking2.getWigellRepairsBookingDate());
        bookingDto2.setCustomer(principal.getName());

        List<BookingDto> expected = List.of(bookingDto1, bookingDto2);

        when(bookingDtoFactory.bookingDtoList(Mockito.anyList())).thenReturn(expected);

        List<BookingDto> bookingDtos = bookingService.myBookings(principal);

        assertEquals(expected, bookingDtos);
        verify(bookingRepository, times(1)).findAll();
    }
}