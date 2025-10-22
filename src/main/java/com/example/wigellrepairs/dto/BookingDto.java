package com.example.wigellrepairs.dto;

import java.time.LocalDate;
import java.util.Objects;

public class BookingDto {
    private Long bookingId;
    private String customer;
    private ServiceDto serviceDto;
    private LocalDate dateOfService;


    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public ServiceDto getService() {
        return serviceDto;
    }

    public void setService(ServiceDto service) {
        this.serviceDto = service;
    }

    public LocalDate getDateOfService() {
        return dateOfService;
    }

    public void setDateOfService(LocalDate dateOfService) {
        this.dateOfService = dateOfService;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof BookingDto)) return false;
        BookingDto bookingDto = (BookingDto) object;
        return customer == bookingDto.customer &&
                dateOfService.equals(bookingDto.dateOfService);
    }

    @Override
    public int hashCode() {return Objects.hash(bookingId, customer, dateOfService);}
}
