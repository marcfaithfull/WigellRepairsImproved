package com.example.wigellrepairs.dto;

import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.ServiceEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BookingDto {
    private Long bookingId;
    private String customer;
    private ServiceDto serviceDto;
    private LocalDate dateOfService;

    public static BookingDto bookingDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setBookingId(booking.getWigellRepairsBookingId());
        bookingDto.setCustomer(booking.getWigellRepairsBookingCustomer());
        ServiceEntity service = booking.getWigellRepairsBookingService();
        bookingDto.setService(ServiceDto.serviceDto(service));
        bookingDto.setDateOfService(booking.getWigellRepairsBookingDate());
        return bookingDto;
    }

    public static List<BookingDto> bookingDtoList(List<Booking> bookings) {
        return bookings.stream()
                .map(BookingDto::bookingDto)
                .collect(Collectors.toList());
    }

    // Getters and Setters
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
