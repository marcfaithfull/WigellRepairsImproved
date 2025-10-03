package com.example.wigellrepairs.services;

import com.example.wigellrepairs.entities.ServiceEntity;

import java.security.Principal;
import java.util.List;

public interface RepairsService {

    List<ServiceEntity> listAllServices();

    void addService(ServiceEntity serviceEntity);
}
