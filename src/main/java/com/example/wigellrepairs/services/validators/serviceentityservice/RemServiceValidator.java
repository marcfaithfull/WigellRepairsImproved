package com.example.wigellrepairs.services.validators.serviceentityservice;

import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.ServiceEntity;
import com.example.wigellrepairs.exceptions.ServiceHasActiveBookingsException;
import com.example.wigellrepairs.exceptions.ServiceNotFoundException;
import com.example.wigellrepairs.repositories.BookingRepository;
import com.example.wigellrepairs.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RemServiceValidator {
    ServiceRepository serviceRepository;
    BookingRepository bookingRepository;

    @Autowired
    RemServiceValidator(ServiceRepository serviceRepository, BookingRepository bookingRepository) {
        this.serviceRepository = serviceRepository;
        this.bookingRepository = bookingRepository;
    }

    public void validateServiceExists(Long id) {
        if (!serviceRepository.existsById(id)) {
            throw new ServiceNotFoundException();
        }
    }

    public void validateNoActiveBookings(ServiceEntity serviceToRemove) {
        List<Booking> bookings = bookingRepository.findAll();
        List<Booking> activeBookings = bookings.stream()
                .filter(booking ->
                        booking.getWigellRepairsBookingService().getWigellRepairsServiceId()
                                .equals(serviceToRemove.getWigellRepairsServiceId()) &&
                                !booking.getWigellRepairsBookingCancelled() &&
                                booking.getWigellRepairsBookingDate().isAfter(LocalDate.now()))
                .collect(Collectors.toList());

        if (!activeBookings.isEmpty()) {
            throw new ServiceHasActiveBookingsException(
                    "You cannot remove this service because it has been booked by one or more users");
        }
    }
}
