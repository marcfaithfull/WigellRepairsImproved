package com.example.wigellrepairs.controllers;

import com.example.wigellrepairs.dto.BookingDto;
import com.example.wigellrepairs.dto.ServiceDto;
import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.services.BookingServiceImpl;
import com.example.wigellrepairs.services.ServicesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/wigellrepairs")
@Secured("ROLE_USER")
public class CustomerController {
    private final BookingServiceImpl BOOKING_SERVICE;
    private final ServicesServiceImpl SERVICE_SERVICE;

    @Autowired
    public CustomerController(BookingServiceImpl bookingService, ServicesServiceImpl serviceService) {
        this.BOOKING_SERVICE = bookingService;
        this.SERVICE_SERVICE = serviceService;
    }



    // Services

    @GetMapping("/services")
    public ResponseEntity<List<ServiceDto>> listAllServices() {
        return ResponseEntity.ok(SERVICE_SERVICE.services());
    }



    // Bookings

    @PostMapping("/bookservice")
    public ResponseEntity<String> bookAService(@RequestBody Booking booking, Principal principal) {
        return BOOKING_SERVICE.bookService(booking, principal);
    }

    @PutMapping("/cancelbooking")
    public ResponseEntity<String> cancelBooking(@RequestBody Booking booking, Principal principal) {
        return BOOKING_SERVICE.cancelBooking(booking, principal);
    }

    @GetMapping("/mybookings")
    public ResponseEntity<List<BookingDto>> myBookings(Principal principal) {
        BOOKING_SERVICE.myBookings(principal);
        return ResponseEntity.ok(BOOKING_SERVICE.myBookings(principal));
    }
}
