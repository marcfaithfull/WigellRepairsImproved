package com.example.wigellrepairs.services.calculators;

import com.example.wigellrepairs.entities.Booking;
import org.springframework.stereotype.Component;

@Component
public class CurrencyConverter {

    public void convertSekToEuro(Booking booking) {
        int sek = booking.getWigellRepairsBookingTotalPrice();
        double euro = (double) sek * 0.091;
        booking.setWigellRepairsBookingTotalPriceEuro(euro);
    }
}
