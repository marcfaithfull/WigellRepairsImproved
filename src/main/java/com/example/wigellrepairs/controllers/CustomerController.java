package com.example.wigellrepairs.controllers;

import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.services.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<String>> listAllServices() {
        return ResponseEntity.ok(customerService.listAllServices());
    }

    @PostMapping("/bookservice")
    @ResponseBody
    public ResponseEntity<String> bookAService(@RequestBody Booking booking, Principal principal) {
        customerService.bookService(booking, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body("The service has been booked");
    }

    @PutMapping("/cancelbooking")
    @ResponseBody
    public ResponseEntity<String> cancelBooking(@RequestBody Booking booking) {
        customerService.cancelBooking(booking);
        return ResponseEntity.ok("Your booking was cancelled");
    }

    @GetMapping("/mybookings")
    @ResponseBody
    public ResponseEntity<String> myBookings() {
        return ResponseEntity.ok("myBookings");
    }
}
