package com.example.wigellrepairs.services;

import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.Service;

import java.security.Principal;
import java.util.List;

public interface CustomerService {

    List<Service> services();

    void bookService(Booking booking, Principal principal);

    void cancelBooking(Booking booking, Principal principal);

    List<Booking> myBookings(Principal principal);
}
