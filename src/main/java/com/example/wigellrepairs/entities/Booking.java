package com.example.wigellrepairs.entities;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "wigellrepairs_bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long wigellRepairsBookingId;

    @Column
    private String wigellRepairsBookingCustomer;

    @ManyToOne
    @JoinColumn(name = "wigell_repairs_service_id", nullable = false)
    private Service wigellRepairsBookingService;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private LocalDate wigellRepairsBookingStartDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private LocalDate wigellRepairsBookingEndDate;

    @Column
    private int wigellRepairsBookingTotalPrice;


    // Getters and Setters
    public long getWigellRepairsBookingId() {
        return wigellRepairsBookingId;
    }

    public void setWigellRepairsBookingId(long wigellRepairsBookingId) {
        this.wigellRepairsBookingId = wigellRepairsBookingId;
    }

    public String getWigellRepairsBookingCustomer() {
        return wigellRepairsBookingCustomer;
    }

    public void setWigellRepairsBookingCustomer(String wigellRepairsBookingCustomer) {
        this.wigellRepairsBookingCustomer = wigellRepairsBookingCustomer;
    }

    public LocalDate getWigellRepairsBookingStartDate() {
        return wigellRepairsBookingStartDate;
    }

    public void setWigellRepairsBookingStartDate(LocalDate wigellRepairsBookingStartDate) {
        this.wigellRepairsBookingStartDate = wigellRepairsBookingStartDate;
    }

    public int getWigellRepairsBookingTotalPrice() {
        return wigellRepairsBookingTotalPrice;
    }

    public void setWigellRepairsBookingTotalPrice(int wigellRepairsBookingTotalPrice) {
        this.wigellRepairsBookingTotalPrice = wigellRepairsBookingTotalPrice;
    }

    public LocalDate getWigellRepairsBookingEndDate() {
        return wigellRepairsBookingEndDate;
    }

    public void setWigellRepairsBookingEndDate(LocalDate wigellRepairsBookingEndDate) {
        this.wigellRepairsBookingEndDate = wigellRepairsBookingEndDate;
    }

    public Service getWigellRepairsBookingService() {
        return wigellRepairsBookingService;
    }

    public void setWigellRepairsBookingService(Service wigellRepairsBookingService) {
        this.wigellRepairsBookingService = wigellRepairsBookingService;
    }
}
