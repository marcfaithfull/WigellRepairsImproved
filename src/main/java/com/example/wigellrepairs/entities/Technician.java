package com.example.wigellrepairs.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wigellrepairs_technicians")
public class Technician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wigellRepairsTechnicianId;

    @Column(nullable = false)
    private String wigellRepairsTechnicianName;

    @Column(nullable = false)
    private String wigellRepairsAreaOfExpertise;

    @OneToMany(mappedBy = "wigellRepairsServiceTechnician", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ServiceEntity> services;

    public Technician() {
    }

    public Technician(String wigellRepairsTechnicianName, String wigellRepairsAreaOfExpertise) {
        this.wigellRepairsTechnicianName = wigellRepairsTechnicianName;
        this.wigellRepairsAreaOfExpertise = wigellRepairsAreaOfExpertise;
        this.services= new ArrayList<>();

    }

    // Getters and Setters
    public Long getWigellRepairsTechnicianId() {
        return wigellRepairsTechnicianId;
    }

    public void setWigellRepairsTechnicianId(Long wigellRepairsTechnicianId) {
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

    public List<ServiceEntity> getServices() {
        return services;
    }

    public void setServices(List<ServiceEntity> services) {
        this.services = services;
    }
}
