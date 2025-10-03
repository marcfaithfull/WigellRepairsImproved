package com.example.wigellrepairs.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "wigellrepairs_bookings")
public class BookingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long wigellRepairsBookingId;

    private String wigellRepairsBookingCustomer;

    private String wigellRepairsBookingService;

    private String wigellRepairsBookingDate;

    private String wigellRepairsBookingTotalPrice;

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

    public String getWigellRepairsBookingService() {
        return wigellRepairsBookingService;
    }

    public void setWigellRepairsBookingService(String wigellRepairsBookingService) {
        this.wigellRepairsBookingService = wigellRepairsBookingService;
    }

    public String getWigellRepairsBookingDate() {
        return wigellRepairsBookingDate;
    }

    public void setWigellRepairsBookingDate(String wigellRepairsBookingDate) {
        this.wigellRepairsBookingDate = wigellRepairsBookingDate;
    }

    public String getWigellRepairsBookingTotalPrice() {
        return wigellRepairsBookingTotalPrice;
    }

    public void setWigellRepairsBookingTotalPrice(String wigellRepairsBookingTotalPrice) {
        this.wigellRepairsBookingTotalPrice = wigellRepairsBookingTotalPrice;
    }
}
