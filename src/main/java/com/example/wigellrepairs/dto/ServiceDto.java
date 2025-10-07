package com.example.wigellrepairs.dto;

import com.example.wigellrepairs.entities.Service;

public class ServiceDto {
    private String Name;
    private String Type;
    private int totalPriceInSEK;
    private double totalPriceInEURO;

    public static ServiceDto serviceDto(Service service) {
        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setName(service.getWigellRepairsServiceName());
        serviceDto.setType(service.getWigellRepairsServiceType());
        serviceDto.setTotalPriceInSEK(service.getWigellRepairsServicePrice());
        serviceDto.setTotalPriceInEURO(convertToEuros(service.getWigellRepairsServicePrice()));
        return serviceDto;
    }

    private static double convertToEuros(int sek) {
        return sek * 0.091;
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

    public double getTotalPriceInEURO() {
        return totalPriceInEURO;
    }

    public void setTotalPriceInEURO(double euro) {
        this.totalPriceInEURO = euro;
    }

    public int getTotalPriceInSEK() {
        return totalPriceInSEK;
    }

    public void setTotalPriceInSEK(int totalPriceInSEK) {
        this.totalPriceInSEK = totalPriceInSEK;
    }
}
