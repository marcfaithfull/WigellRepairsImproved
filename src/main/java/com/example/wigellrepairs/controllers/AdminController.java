package com.example.wigellrepairs.controllers;

import com.example.wigellrepairs.dto.ServiceDto;
import com.example.wigellrepairs.dto.TechnicianDto;
import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.Service;
import com.example.wigellrepairs.entities.Technician;
import com.example.wigellrepairs.services.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wigellrepairs")
@Secured("ROLE_ADMIN")
public class AdminController {
    private final AdminServiceImpl adminService;

    @Autowired
    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/listcancelled")
    @ResponseBody
    public ResponseEntity<List<Booking>> listCancelledBookings() {
        return ResponseEntity.ok(adminService.listCancelled());
    }

    @GetMapping("/listupcoming")
    @ResponseBody
    public ResponseEntity<List<Booking>> listUpcomingBookings() {
        return ResponseEntity.ok(adminService.listUpcoming());
    }

    @GetMapping("/listpast")
    @ResponseBody
    public ResponseEntity<List<Booking>> listPastBookings() {
        return ResponseEntity.ok(adminService.listPast());
    }

    @PostMapping("/addservice")
    public ResponseEntity<String> addService(@RequestBody Service service) {
        return adminService.addService(service);
    }

    @PutMapping("/updateservice")
    public ResponseEntity<String> updateService(@RequestBody Service service) {
        return adminService.updateService(service);
    }

    @DeleteMapping("/remservice/{id}")
    @ResponseBody
    public ResponseEntity<String> remService(@PathVariable Long id) {
        return adminService.remService(id);

    }

    @PostMapping("/addtechnician")
    public ResponseEntity<String> addTechnician(@RequestBody Technician technician) {
        return adminService.addTechnician(technician);
    }

    @GetMapping("/technicians")
    @ResponseBody
    public ResponseEntity<List<TechnicianDto>> technicians() {
        return ResponseEntity.ok(adminService.technicians());
    }
}
