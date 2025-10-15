package com.example.wigellrepairs.controllers;

import com.example.wigellrepairs.dto.TechnicianDto;
import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.ServiceEntity;
import com.example.wigellrepairs.entities.Technician;
import com.example.wigellrepairs.exceptions.*;
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
    private final BookingService bookingService;
    private final ServiceEntityService serviceEntityService;
    private final TechnicianService technicianService;

    @Autowired
    public AdminController(BookingService bookingService, ServiceEntityService serviceService, TechnicianService technicianService) {
        this.bookingService = bookingService;
        this.serviceEntityService = serviceService;
        this.technicianService = technicianService;
    }

    // Booking

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
    public ResponseEntity<String> addService(@RequestBody ServiceEntity service) {
        try {
            serviceEntityService.addService(service);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Service added successfully");
        } catch (TechnicianNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InvalidExpertiseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/updateservice")
    public ResponseEntity<String> updateService(@RequestBody ServiceEntity service) {
        try {
            serviceEntityService.updateService(service);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Service updated successfully");
        } catch (ServiceNotFoundException | TechnicianNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InvalidExpertiseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/remservice/{id}")
    public ResponseEntity<String> remService(@PathVariable Long id) {
        try {
            serviceEntityService.remService(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Service removed successfully");
        } catch (ServiceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ServiceHasActiveBookingsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Technician

    @PostMapping("/addtechnician")
    public ResponseEntity<String> addTechnician(@RequestBody Technician technician) {
        try {
            technicianService.addTechnician(technician);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Technician added successfully");
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/technicians")
    public ResponseEntity<List<TechnicianDto>> technicians() {
        List<TechnicianDto> technicians = technicianService.getTechnicians();
        return ResponseEntity.ok(technicians);
    }
}
