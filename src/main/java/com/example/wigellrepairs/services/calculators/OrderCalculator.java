package com.example.wigellrepairs.services.calculators;

import com.example.wigellrepairs.entities.Booking;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

@Component
public class OrderCalculator {

    public void calculateTotalCost(Booking booking) {
        long datsRented = ChronoUnit.DAYS.between(booking.getWigellRepairsBookingStartDate(), booking.getWigellRepairsBookingEndDate()) + 1;
        booking.setWigellRepairsBookingTotalPrice((int) (datsRented * 500));
    }
}
