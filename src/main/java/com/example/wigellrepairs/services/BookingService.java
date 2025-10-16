package com.example.wigellrepairs.services;

import com.example.wigellrepairs.dto.BookingDto;
import com.example.wigellrepairs.dto.BookingRequestDto;
import com.example.wigellrepairs.entities.Booking;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface BookingService {


    // User

    void bookService(BookingRequestDto bookingRequestDto, Principal principal);

    void cancelBooking(Booking booking, Principal principal);

    List<BookingDto> myBookings(Principal principal);


    // Admin

    List<Booking> listCancelled();

    List<Booking> listUpcoming();

    List<Booking> listPast();
}
