package com.example.wigellrepairs.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "wigellrepairs_services")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long wigellRepairsServiceId;

    private String wigellRepairsServiceName;

    private String wigellRepairsServiceType;

    private String wigellRepairsServicePrice;

    private String wigellRepairsServiceTechnician;

    public long getWigellRepairsServiceId() {
        return wigellRepairsServiceId;
    }

    public void setWigellRepairsServiceId(long wigellRepairsServiceId) {
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

    public String getWigellRepairsServicePrice() {
        return wigellRepairsServicePrice;
    }

    public void setWigellRepairsServicePrice(String wigellRepairsServicePrice) {
        this.wigellRepairsServicePrice = wigellRepairsServicePrice;
    }

    public String getWigellRepairsServiceTechnician() {
        return wigellRepairsServiceTechnician;
    }

    public void setWigellRepairsServiceTechnician(String wigellRepairsServiceTechnician) {
        this.wigellRepairsServiceTechnician = wigellRepairsServiceTechnician;
    }
}
