package com.example.wigellrepairs.dto;

import com.example.wigellrepairs.entities.Service;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceDto {
    private Long id;
    private String Name;
    private String Type;
    private int priceInSEK;
    private double priceInEURO;

    public static ServiceDto serviceDto(Service service) {
        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setId(service.getWigellRepairsServiceId());
        serviceDto.setName(service.getWigellRepairsServiceName());
        serviceDto.setType(service.getWigellRepairsServiceType());
        serviceDto.setPriceInSEK(service.getWigellRepairsServicePrice());
        serviceDto.setPriceInEURO(convertToEuros(service.getWigellRepairsServicePrice()));
        return serviceDto;
    }

    public static List<ServiceDto> serviceDtoList(List<Service> services) {
        return services.stream()
                .map(ServiceDto::serviceDto)
                .collect(Collectors.toList());
    }

    // EURO CONVERTER
    private static double convertToEuros(int sek) {
        return sek * 0.091;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public double getPriceInEURO() {
        return priceInEURO;
    }

    public void setPriceInEURO(double euro) {
        this.priceInEURO = euro;
    }

    public int getPriceInSEK() {
        return priceInSEK;
    }

    public void setPriceInSEK(int priceInSEK) {
        this.priceInSEK = priceInSEK;
    }
}
