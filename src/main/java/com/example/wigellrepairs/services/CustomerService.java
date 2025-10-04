package com.example.wigellrepairs.services;

import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.Service;

import java.security.Principal;
import java.util.List;

public interface CustomerService {

    List<Service> listAllServices();

    void bookService(Booking booking, Principal principal);

    // cancelbooking

    // mybookings
}
