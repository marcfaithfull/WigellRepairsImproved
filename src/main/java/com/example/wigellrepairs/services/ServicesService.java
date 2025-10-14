package com.example.wigellrepairs.services;

import com.example.wigellrepairs.dto.ServiceDto;
import com.example.wigellrepairs.entities.Service;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ServicesService {

    // User

    List<ServiceDto> services();


    // Admin

    ResponseEntity<String> addService(Service service);

    ResponseEntity<String> updateService(Service service);

    ResponseEntity<String> remService(Long id);

}
