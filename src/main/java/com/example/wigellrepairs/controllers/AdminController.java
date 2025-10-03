package com.example.wigellrepairs.controllers;

import com.example.wigellrepairs.entities.ServiceEntity;
import com.example.wigellrepairs.services.AdminServiceImpl;
import com.example.wigellrepairs.services.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wigellrepairs")
@Secured("ROLE_ADMIN")
public class AdminController {
    AdminServiceImpl adminService;

    @Autowired
    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/listcancelled")
    @ResponseBody
    public ResponseEntity<String> listCancelledBookings() {
        return ResponseEntity.ok("Demo");
    }

    @GetMapping("/listupcoming")
    @ResponseBody
    public ResponseEntity<String> listUpcomingBookings() {
        return ResponseEntity.ok("Demo");
    }

    @GetMapping("/listpast")
    @ResponseBody
    public ResponseEntity<String> listPastBookings() {
        return ResponseEntity.ok("Demo");
    }

    @PostMapping("/addservice")
    public ResponseEntity<String> addService(@RequestBody ServiceEntity serviceEntity) {
        /*ServiceEntity savedServiceEntity = new ServiceEntity();
        savedServiceEntity.setWigellRepairsServiceName(serviceEntity.getWigellRepairsServiceName());
        savedServiceEntity.setWigellRepairsServiceType(serviceEntity.getWigellRepairsServiceType());
        savedServiceEntity.setWigellRepairsServicePrice(serviceEntity.getWigellRepairsServicePrice());
        savedServiceEntity.setWigellRepairsServiceTechnician(serviceEntity.getWigellRepairsServiceTechnician());*/
        adminService.addService(serviceEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("The service has been added");
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
        return ResponseEntity.ok("technicians");
    }

}
