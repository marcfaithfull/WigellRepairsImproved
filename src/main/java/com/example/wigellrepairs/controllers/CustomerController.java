package com.example.wigellrepairs.controllers;

import com.example.wigellrepairs.entities.ServiceEntity;
import com.example.wigellrepairs.services.RepairsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wigellrepairs")
public class CustomerController {
    private RepairsServiceImpl repairsService;

    @Autowired
    public CustomerController(RepairsServiceImpl repairsService) {
        this.repairsService =  repairsService;
    }

    @GetMapping("/test")
    @ResponseBody
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }

    @GetMapping("/services")
    @ResponseBody
    public ResponseEntity<List<ServiceEntity>> listAllServices() {
        return ResponseEntity.ok(repairsService.listAllServices());
    }
}
