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
    @Column(name = "wigell_repairs_booking_id")
    private Long wigellRepairsBookingId;

    @Column(name = "wigell_repairs_booking_customer")
    private String wigellRepairsBookingCustomer;

    @ManyToOne
    @JoinColumn(name = "wigell_repairs_service_id", nullable = false)
    @JsonBackReference
    private ServiceEntity wigellRepairsBookingService;

    @Column(name = "wigell_repairs_booking_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate wigellRepairsBookingDate;

    @Column(name = "wigell_repairs_booking_cancelled", nullable = false)
    private Boolean wigellRepairsBookingCancelled;


    public Booking() {
        this.wigellRepairsBookingCancelled = false;
    }


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

    public ServiceEntity getWigellRepairsBookingService() {
        return wigellRepairsBookingService;
    }

    public void setWigellRepairsBookingService(ServiceEntity wigellRepairsBookingService) {
        this.wigellRepairsBookingService = wigellRepairsBookingService;
    }

    public Boolean getWigellRepairsBookingCancelled() {
        return wigellRepairsBookingCancelled;
    }

    public void setWigellRepairsBookingCancelled(Boolean wigellRepairsBookingCancelled) {
        this.wigellRepairsBookingCancelled = wigellRepairsBookingCancelled;
    }
}
