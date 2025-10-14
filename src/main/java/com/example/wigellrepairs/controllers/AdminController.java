package com.example.wigellrepairs.controllers;

import com.example.wigellrepairs.dto.TechnicianDto;
import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.Service;
import com.example.wigellrepairs.entities.Technician;
import com.example.wigellrepairs.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wigellrepairs")
@Secured("ROLE_ADMIN")
public class AdminController {
    private final BookingServiceImpl BOOKING_SERVICE;
    private final ServicesServiceImpl SERVICE_SERVICE;
    private final TechnicianService TECHNICIAN_SERVICE;

    @Autowired
    public AdminController(BookingServiceImpl bookingService, ServicesServiceImpl serviceService, TechnicianServiceImpl technicianService) {
        this.BOOKING_SERVICE = bookingService;
        this.SERVICE_SERVICE = serviceService;
        this.TECHNICIAN_SERVICE = technicianService;
    }

    // Bookings

    @GetMapping("/listcancelled")
    public ResponseEntity<List<Booking>> listCancelledBookings() {
        return ResponseEntity.ok(BOOKING_SERVICE.listCancelled());
    }

    @GetMapping("/listupcoming")
    public ResponseEntity<List<Booking>> listUpcomingBookings() {
        return ResponseEntity.ok(BOOKING_SERVICE.listUpcoming());
    }

    @GetMapping("/listpast")
    public ResponseEntity<List<Booking>> listPastBookings() {
        return ResponseEntity.ok(BOOKING_SERVICE.listPast());
    }

    // Service

    @PostMapping("/addservice")
    public ResponseEntity<String> addService(@RequestBody Service service) {
        return SERVICE_SERVICE.addService(service);
    }

    @PutMapping("/updateservice")
    public ResponseEntity<String> updateService(@RequestBody Service service) {
        return SERVICE_SERVICE.updateService(service);
    }

    @DeleteMapping("/remservice/{id}")
    public ResponseEntity<String> remService(@PathVariable Long id) {
        return SERVICE_SERVICE.remService(id);
    }

    // Technician

    @PostMapping("/addtechnician")
    public ResponseEntity<String> addTechnician(@RequestBody Technician technician) {
        return TECHNICIAN_SERVICE.addTechnician(technician);
    }

    @GetMapping("/technicians")
    public ResponseEntity<List<TechnicianDto>> technicians() {
        return ResponseEntity.ok(TECHNICIAN_SERVICE.technicians());
    }
}
