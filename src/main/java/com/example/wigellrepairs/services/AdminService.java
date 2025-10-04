package com.example.wigellrepairs.services;

import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.Service;
import com.example.wigellrepairs.entities.Technician;

import java.util.List;

public interface AdminService {

    List<Booking> listCancelled();

    List<Booking> listUpcoming();

    List<Booking> listPast();

    void addService(Service service);

    void updateService(Service service);

    void remService(Long id);

    void addTechnician(Technician technician);

    List<String> technicians();
}
