package com.example.wigellrepairs.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "wigellrepairs_services")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long serviceId;

    private String wigellRepairsServiceName;
    private String wigellRepairsServiceType;
    private String wigellRepairsServicePrice;
    private String wigellRepairsServiceTechnician;
}
