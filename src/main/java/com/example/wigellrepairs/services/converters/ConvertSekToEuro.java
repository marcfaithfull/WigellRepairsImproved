package com.example.wigellrepairs.services.converters;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ConvertSekToEuro {

    private final RestClient restClient;

    public ConvertSekToEuro(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://wigellrepairscurrencyconverter:8081").build();
    }

    public double convertSEKtoEUR(double amount) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/convert/sekToEuro")
                        .queryParam("amount", amount)
                        .build())
                .retrieve()
                .body(Double.class);
    }
}