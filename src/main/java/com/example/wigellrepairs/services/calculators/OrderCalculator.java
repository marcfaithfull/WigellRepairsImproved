package com.example.wigellrepairs.services.calculators;

import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.Service;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

@Component
public class OrderCalculator {

    public void calculateTotalCost(Booking booking, Service service) {
        long datsRented = ChronoUnit.DAYS.between(booking.getWigellRepairsBookingStartDate(), booking.getWigellRepairsBookingEndDate()) + 1;
        booking.setWigellRepairsBookingTotalPrice((int) (datsRented * service.getWigellRepairsServicePrice()));
    }
}
