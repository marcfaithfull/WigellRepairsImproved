package com.example.wigellrepairs.services;

import com.example.wigellrepairs.dto.ServiceDto;
import com.example.wigellrepairs.entities.ServiceEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ServiceEntityService {

    // User

    List<ServiceDto> getServices();


    // Admin

    void addService(ServiceEntity service);

    void updateService(ServiceEntity service);

    void remService(Long id);

}
