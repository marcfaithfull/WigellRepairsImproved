package com.example.wigellrepairs.services;

import com.example.wigellrepairs.dto.TechnicianDto;
import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.Service;
import com.example.wigellrepairs.entities.Technician;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {

    List<Booking> listCancelled();

    List<Booking> listUpcoming();

    List<Booking> listPast();

    ResponseEntity<String> addService(Service service);

    ResponseEntity<String> updateService(Service service);

    ResponseEntity<String> remService(Long id);

    ResponseEntity<String> addTechnician(Technician technician);

    List<TechnicianDto> technicians();
}
