package com.example.wigellrepairs;

import com.example.wigellrepairs.services.calculators.CurrencyConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WigellRepairsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WigellRepairsApplication.class, args);
    }

    CurrencyConverter currencyConverter = new CurrencyConverter();
    int testSek = 1000;

    public int test() {
        return testSek;
    }
}
