package com.example.wigellrepairs.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "wigellrepairs_technician")
public class Technician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long wigellRepairsTechnicianId;

    @Column(nullable = false)
    private String wigellRepairsTechnicianName;

    @Column(nullable = false)
    private String wigellRepairsAreaOfExpertise;

    @OneToMany(mappedBy = "wigellRepairsServiceTechnician", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Service> services;


    // Getters and Setters
    public long getWigellRepairsTechnicianId() {
        return wigellRepairsTechnicianId;
    }

    public void setWigellRepairsTechnicianId(long wigellRepairsTechnicianId) {
        this.wigellRepairsTechnicianId = wigellRepairsTechnicianId;
    }

    public String getWigellRepairsTechnicianName() {
        return wigellRepairsTechnicianName;
    }

    public void setWigellRepairsTechnicianName(String wigellRepairsTechnicianName) {
        this.wigellRepairsTechnicianName = wigellRepairsTechnicianName;
    }

    public String getWigellRepairsAreaOfExpertise() {
        return wigellRepairsAreaOfExpertise;
    }

    public void setWigellRepairsAreaOfExpertise(String wigellRepairsAreaOfExpertise) {
        this.wigellRepairsAreaOfExpertise = wigellRepairsAreaOfExpertise;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
