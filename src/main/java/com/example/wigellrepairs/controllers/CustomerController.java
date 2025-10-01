package com.example.wigellrepairs.controllers;

import com.example.wigellrepairs.entities.ServiceEntity;
import com.example.wigellrepairs.services.RepairsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wigellrepairs")
public class CustomerController {
    private RepairsServiceImpl repairsService;

    @Autowired
    public CustomerController(RepairsServiceImpl repairsService) {
        this.repairsService =  repairsService;
    }

    // Test
    @GetMapping("/test")
    @ResponseBody
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }

    // Customer controller
    @GetMapping("/services")
    @ResponseBody
    public ResponseEntity<List<ServiceEntity>> listAllServices() {
        return ResponseEntity.ok(repairsService.listAllServices());
    }

    @PostMapping("/bookservice")
    @ResponseBody
    public ResponseEntity<String> bookAService() {
        return ResponseEntity.ok("bookAService");
    }

    @PutMapping("/cancelbooking")
    @ResponseBody
    public ResponseEntity<String> cancelBooking() {
        return ResponseEntity.ok("cancelBooking");
    }

    @GetMapping("/mybookings")
    @ResponseBody
    public ResponseEntity<String> myBookings() {
        return ResponseEntity.ok("myBookings");
    }
}
