package com.example.wigellrepairs.services;

import com.example.wigellrepairs.entities.Service;
import com.example.wigellrepairs.entities.Technician;

public interface AdminService {

    // listcancelled

    // listupcoming

    // listpast

    void addService(Service service);

    // updateservice

    // remservice{id}

    void addTechnician(Technician technician);

    // technicians
}
