package com.example.wigellrepairs.services;

import com.example.wigellrepairs.dto.BookingDto;
import com.example.wigellrepairs.dto.ServiceDto;
import com.example.wigellrepairs.entities.Booking;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface CustomerService {

    List<ServiceDto> services();

    ResponseEntity<String> bookService(Booking booking, Principal principal);

    ResponseEntity<String> cancelBooking(Booking booking, Principal principal);

    List<BookingDto> myBookings(Principal principal);
}
