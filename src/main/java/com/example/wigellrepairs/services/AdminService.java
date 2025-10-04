package com.example.wigellrepairs.services;

import com.example.wigellrepairs.entities.Service;
import com.example.wigellrepairs.entities.Technician;

import java.util.List;

public interface AdminService {

    // listcancelled

    // listupcoming

    // listpast

    void addService(Service service);

    // updateservice
    void updateService(Service service);

    void remservice(Long id);

    void addTechnician(Technician technician);

    List<String> technicians();
}
