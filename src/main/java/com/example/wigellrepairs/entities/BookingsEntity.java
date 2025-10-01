package com.example.wigellrepairs.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "wigellrepairs_bookings")
public class BookingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String wigellRepairsCustomer;
    private String wigellRepairsService;
    private String wigellRepairsDate;
    private String wigellRepairsTotalPrice;
}
