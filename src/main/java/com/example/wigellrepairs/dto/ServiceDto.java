package com.example.wigellrepairs.dto;

import java.util.Objects;

public class ServiceDto {
    private Long serviceId;
    private String serviceName;
    private String serviceType;
    private int priceInSEK;
    private double priceInEURO;


    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof ServiceDto)) return false;
        ServiceDto serviceDto = (ServiceDto) object;
        return priceInSEK == serviceDto.priceInSEK &&
                Double.compare(serviceDto.priceInEURO, priceInEURO) == 0 &&
                Objects.equals(serviceId, serviceDto.serviceId) &&
                Objects.equals(serviceName, serviceDto.serviceName) &&
                Objects.equals(serviceType, serviceDto.serviceType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, serviceName, serviceType, priceInSEK, priceInEURO);
    }
}
