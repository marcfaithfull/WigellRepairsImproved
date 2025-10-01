package com.example.wigellrepairs.services.calculators;

import org.springframework.stereotype.Component;

@Component
public class CurrencyConverter {

    public int convertSekToEuro(int sek) {
        int euro = sek/10;
        return euro;
    }


}
