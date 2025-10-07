package com.example.wigellrepairs.controllers;

import com.example.wigellrepairs.dto.BookingDto;
import com.example.wigellrepairs.dto.ServiceDto;
import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.services.CustomerServiceImpl;
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
    private final CustomerServiceImpl customerService;

    @Autowired
    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService =  customerService;
    }

    @GetMapping("/services")
    @ResponseBody
    public ResponseEntity<List<ServiceDto>> listAllServices() {
        return ResponseEntity.ok(customerService.services());
    }

    @PostMapping("/bookservice")
    @ResponseBody
    public ResponseEntity<String> bookAService(@RequestBody Booking booking, Principal principal) {
        return customerService.bookService(booking, principal);
    }

    @PutMapping("/cancelbooking")
    @ResponseBody
    public ResponseEntity<String> cancelBooking(@RequestBody Booking booking, Principal principal) {
        return customerService.cancelBooking(booking, principal);
    }

    @GetMapping("/mybookings")
    @ResponseBody
    public ResponseEntity<List<BookingDto>> myBookings(Principal principal) {
        customerService.myBookings(principal);
        return ResponseEntity.ok(customerService.myBookings(principal));
    }
}
