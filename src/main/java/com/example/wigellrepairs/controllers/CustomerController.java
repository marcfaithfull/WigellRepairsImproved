package com.example.wigellrepairs.controllers;

import com.example.wigellrepairs.dto.BookingDto;
import com.example.wigellrepairs.dto.BookingRequestDto;
import com.example.wigellrepairs.dto.ServiceDto;
import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.exceptions.*;
import com.example.wigellrepairs.services.BookingService;
import com.example.wigellrepairs.services.ServiceEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/wigellrepairs")
@Secured("ROLE_USER")
public class CustomerController {
    private final BookingService bookingService;
    private final ServiceEntityService serviceEntityService;
    private RestClient restClient;

    @Autowired
    public CustomerController(BookingService bookingService, ServiceEntityService serviceEntityService,
                              RestClient.Builder restClientBuilder) {
        this.bookingService = bookingService;
        this.serviceEntityService = serviceEntityService;
        this.restClient = restClientBuilder.build();
    }


    // Service

    @GetMapping("/services")
    public ResponseEntity<List<ServiceDto>> services() {
        List<ServiceDto> services = serviceEntityService.getServices();
        return ResponseEntity.ok(services);
    }


    // Booking

    @PostMapping("/bookservice")
    public ResponseEntity<String> bookAService(@RequestBody BookingRequestDto bookingRequestDto, Principal principal) {
        try {
            bookingService.bookService(bookingRequestDto, principal);
            return ResponseEntity.status(HttpStatus.CREATED).body("The service was successfully booked");
        } catch (ServiceNotFoundException | DateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/cancelbooking")
    public ResponseEntity<String> cancelBooking(@RequestBody Booking booking, Principal principal) {
        try {
            bookingService.cancelBooking(booking, principal);
            return ResponseEntity.status(HttpStatus.OK).body("Booking has been cancelled");
        } catch (BookingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UnauthorisedUserException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (BookingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/mybookings")
    public ResponseEntity<List<BookingDto>> myBookings(Principal principal) {
        bookingService.myBookings(principal);
        return ResponseEntity.ok(bookingService.myBookings(principal));
    }
}
