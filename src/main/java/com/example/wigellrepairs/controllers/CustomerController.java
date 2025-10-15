package com.example.wigellrepairs.controllers;

import com.example.wigellrepairs.dto.BookingDto;
import com.example.wigellrepairs.dto.ServiceDto;
import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.exceptions.BookingException;
import com.example.wigellrepairs.exceptions.BookingNotFoundException;
import com.example.wigellrepairs.exceptions.UnauthorisedUserException;
import com.example.wigellrepairs.services.BookingService;
import com.example.wigellrepairs.services.ServiceEntityService;
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
    private final BookingService bookingService;
    private final ServiceEntityService serviceService;

    @Autowired
    public CustomerController(BookingService bookingService, ServiceEntityService serviceService) {
        this.bookingService = bookingService;
        this.serviceService = serviceService;
    }

    // Services

    @GetMapping("/services")
    public ResponseEntity<List<ServiceDto>> services() {
        List<ServiceDto> services = serviceService.getServices();
        return ResponseEntity.ok(services);
    }

    // Bookings

    @PostMapping("/bookservice")
    public ResponseEntity<String> bookAService(@RequestBody Booking booking, Principal principal) {
        try {
            bookingService.bookService(booking, principal);
            return ResponseEntity.status(HttpStatus.CREATED).body("The service was successfully booked");
        } catch (BookingNotFoundException | BookingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UnauthorisedUserException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
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
