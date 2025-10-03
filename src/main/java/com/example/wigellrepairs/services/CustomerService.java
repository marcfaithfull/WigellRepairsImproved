package com.example.wigellrepairs.services;

import com.example.wigellrepairs.entities.BookingsEntity;
import com.example.wigellrepairs.entities.ServiceEntity;

import java.util.List;

public interface CustomerService {

    List<ServiceEntity> listAllServices();

    void bookService(BookingsEntity bookingsEntity);

}
