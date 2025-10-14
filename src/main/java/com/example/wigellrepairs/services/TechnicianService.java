package com.example.wigellrepairs.services;

import com.example.wigellrepairs.dto.TechnicianDto;
import com.example.wigellrepairs.entities.Technician;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TechnicianService {

    // Admin

    ResponseEntity<String> addTechnician(Technician technician);

    List<TechnicianDto> technicians();
}
