package com.example.wigellrepairs.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "wigellrepairs_bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wigellRepairsBookingId;

    @Column
    private String wigellRepairsBookingCustomer;

    @ManyToOne
    @JoinColumn(name = "wigell_repairs_service_id", nullable = false)
    @JsonBackReference
    private Service wigellRepairsBookingService;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private LocalDate wigellRepairsBookingDate;

    @Column
    private int wigellRepairsBookingTotalPrice;

    @Column
    private double wigellRepairsBookingTotalPriceEuro;

    @Column(nullable = false)
    private Boolean wigellRepairsBookingCancelled;

    // Constructor
    public Booking() {
        this.wigellRepairsBookingCancelled = false;
    }

    // Getters and Setters
    public Long getWigellRepairsBookingId() {
        return wigellRepairsBookingId;
    }

    public void setWigellRepairsBookingId(Long wigellRepairsBookingId) {
        this.wigellRepairsBookingId = wigellRepairsBookingId;
    }

    public String getWigellRepairsBookingCustomer() {
        return wigellRepairsBookingCustomer;
    }

    public void setWigellRepairsBookingCustomer(String wigellRepairsBookingCustomer) {
        this.wigellRepairsBookingCustomer = wigellRepairsBookingCustomer;
    }

    public LocalDate getWigellRepairsBookingDate() {
        return wigellRepairsBookingDate;
    }

    public void setWigellRepairsBookingDate(LocalDate wigellRepairsBookingDate) {
        this.wigellRepairsBookingDate = wigellRepairsBookingDate;
    }

    public int getWigellRepairsBookingTotalPrice() {
        return wigellRepairsBookingTotalPrice;
    }

    public void setWigellRepairsBookingTotalPrice(int wigellRepairsBookingTotalPrice) {
        this.wigellRepairsBookingTotalPrice = wigellRepairsBookingTotalPrice;
    }

    /*public LocalDate getWigellRepairsBookingEndDate() {
        return wigellRepairsBookingEndDate;
    }

    public void setWigellRepairsBookingEndDate(LocalDate wigellRepairsBookingEndDate) {
        this.wigellRepairsBookingEndDate = wigellRepairsBookingEndDate;
    }*/

    public Service getWigellRepairsBookingService() {
        return wigellRepairsBookingService;
    }

    public void setWigellRepairsBookingService(Service wigellRepairsBookingService) {
        this.wigellRepairsBookingService = wigellRepairsBookingService;
    }

    /*public Boolean isCancelled() {
        return wigellRepairsBookingCancelled;
    }

    public void setCancelled(Boolean wigellRepairsBookingCancelled) {
        this.wigellRepairsBookingCancelled = wigellRepairsBookingCancelled;
    }*/

    public double getWigellRepairsBookingTotalPriceEuro() {
        return wigellRepairsBookingTotalPriceEuro;
    }

    public void setWigellRepairsBookingTotalPriceEuro(double wigellRepairsBookingTotalPriceEuro) {
        this.wigellRepairsBookingTotalPriceEuro = wigellRepairsBookingTotalPriceEuro;
    }

    public Boolean getWigellRepairsBookingCancelled() {
        return wigellRepairsBookingCancelled;
    }

    public void setWigellRepairsBookingCancelled(Boolean wigellRepairsBookingCancelled) {
        this.wigellRepairsBookingCancelled = wigellRepairsBookingCancelled;
    }
}
