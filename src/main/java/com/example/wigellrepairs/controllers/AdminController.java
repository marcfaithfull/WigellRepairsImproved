package com.example.wigellrepairs.controllers;

import com.example.wigellrepairs.dto.TechnicianDto;
import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.Service;
import com.example.wigellrepairs.entities.Technician;
import com.example.wigellrepairs.exceptions.ValidationException;
import com.example.wigellrepairs.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wigellrepairs")
@Secured("ROLE_ADMIN")
public class AdminController {
    private final BookingServiceImpl bookingService;
    private final ServicesServiceImpl servicesService;
    private final TechnicianServiceImpl technicianService;

    @Autowired
    public AdminController(BookingServiceImpl bookingService, ServicesServiceImpl serviceService, TechnicianServiceImpl technicianService) {
        this.bookingService = bookingService;
        this.servicesService = serviceService;
        this.technicianService = technicianService;
    }

    // Bookings

    @GetMapping("/listcancelled")
    public ResponseEntity<List<Booking>> listCancelledBookings() {
        return ResponseEntity.ok(bookingService.listCancelled());
    }

    @GetMapping("/listupcoming")
    public ResponseEntity<List<Booking>> listUpcomingBookings() {
        return ResponseEntity.ok(bookingService.listUpcoming());
    }

    @GetMapping("/listpast")
    public ResponseEntity<List<Booking>> listPastBookings() {
        return ResponseEntity.ok(bookingService.listPast());
    }

    // Service

    @PostMapping("/addservice")
    public ResponseEntity<String> addService(@RequestBody Service service) {
        return servicesService.addService(service);
    }

    @PutMapping("/updateservice")
    public ResponseEntity<String> updateService(@RequestBody Service service) {
        return servicesService.updateService(service);
    }

    @DeleteMapping("/remservice/{id}")
    public ResponseEntity<String> remService(@PathVariable Long id) {
        return servicesService.remService(id);
    }

    // Technician

    @PostMapping("/addtechnician")
    public ResponseEntity<String> addTechnician(@RequestBody Technician technician) {
        try {
            technicianService.addTechnician(technician);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Technician added successfully");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/technicians")
    public ResponseEntity<List<TechnicianDto>> technicians() {
        List<TechnicianDto> technicians = technicianService.getTechnicians();
        return ResponseEntity.ok(technicians);
    }
}
