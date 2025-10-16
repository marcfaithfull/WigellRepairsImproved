package com.example.wigellrepairs.dto;

import java.time.LocalDate;

public class BookingRequestDto {
    private String customer;
    private LocalDate dateOfService;
    private Long serviceId;

    public BookingRequestDto() {}

    public BookingRequestDto(String customer, LocalDate dateOfService, Long serviceId) {
        this.customer = customer;
        this.dateOfService = dateOfService;
        this.serviceId = serviceId;
    }

    // Getters and Setters
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public LocalDate getDateOfService() {
        return dateOfService;
    }

    public void setDateOfService(LocalDate dateOfService) {
        this.dateOfService = dateOfService;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}