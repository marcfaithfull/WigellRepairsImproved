package com.example.wigellrepairs.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "wigellrepairs_services")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wigellRepairsServiceId;

    @Column
    private String wigellRepairsServiceName;

    @Column
    private String wigellRepairsServiceType;

    @Column
    private int wigellRepairsServicePrice;

    @ManyToOne
    @JoinColumn(name = "wigell_repairs_technician_id", nullable = false)
    private Technician wigellRepairsServiceTechnician;

    @OneToMany(mappedBy = "wigellRepairsBookingService", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;


    // Getters and Setters
    public Long getWigellRepairsServiceId() {
        return wigellRepairsServiceId;
    }

    public void setWigellRepairsServiceId(Long wigellRepairsServiceId) {
        this.wigellRepairsServiceId = wigellRepairsServiceId;
    }

    public String getWigellRepairsServiceName() {
        return wigellRepairsServiceName;
    }

    public void setWigellRepairsServiceName(String wigellRepairsServiceName) {
        this.wigellRepairsServiceName = wigellRepairsServiceName;
    }

    public String getWigellRepairsServiceType() {
        return wigellRepairsServiceType;
    }

    public void setWigellRepairsServiceType(String wigellRepairsServiceType) {
        this.wigellRepairsServiceType = wigellRepairsServiceType;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public int getWigellRepairsServicePrice() {
        return wigellRepairsServicePrice;
    }

    public void setWigellRepairsServicePrice(int wigellRepairsServicePrice) {
        this.wigellRepairsServicePrice = wigellRepairsServicePrice;
    }

    public Technician getWigellRepairsServiceTechnician() {
        return wigellRepairsServiceTechnician;
    }

    public void setWigellRepairsServiceTechnician(Technician wigellRepairsServiceTechnician) {
        this.wigellRepairsServiceTechnician = wigellRepairsServiceTechnician;
    }
}
