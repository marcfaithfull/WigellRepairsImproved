package com.example.wigellrepairs.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "wigellrepairs_technician")
public class TechnicianEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long wigellRepairsTechnicianId;

    private String wigellRepairsTechnicianName;

    private String wigellRepairsAreaOfExpertise;

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
}
