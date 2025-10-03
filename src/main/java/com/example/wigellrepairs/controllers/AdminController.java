package com.example.wigellrepairs.controllers;

import com.example.wigellrepairs.services.RepairsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/wigellrepairs")
public class AdminController {
    RepairsServiceImpl repairsService;

    @Autowired
    public AdminController(RepairsServiceImpl repairsService) {
        this.repairsService = repairsService;
    }

    @GetMapping("/listcanceled")
    @ResponseBody
    public ResponseEntity<String> listCancelledBookings() {
        return ResponseEntity.ok("Demo");
    }

    @GetMapping("/listupcoming")
    @ResponseBody
    public ResponseEntity<String> listupcomingBookings() {
        return ResponseEntity.ok("Demo");
    }

    @GetMapping("/listpast")
    @ResponseBody
    public ResponseEntity<String> listpastBookings() {
        return ResponseEntity.ok("Demo");
    }

    @PostMapping("/addservice")
    @ResponseBody
    public ResponseEntity<String> addService() {
        return ResponseEntity.ok("Demo");
    }

    @PutMapping("/updateservice")
    @ResponseBody
    public ResponseEntity<String> updateService() {
        return ResponseEntity.ok("Demo");
    }

    @DeleteMapping("/remservice/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteService(@PathVariable String id) {
        return ResponseEntity.ok("Demo");
    }

    @PostMapping("/addtechnician")
    @ResponseBody
    public ResponseEntity<String> addTechnician() {
        return ResponseEntity.ok("Demo");
    }

    @GetMapping("/technicians")
    @ResponseBody
    public ResponseEntity<String> technicians() {
        return ResponseEntity.ok("Demo");
    }

}
