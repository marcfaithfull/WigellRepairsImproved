package com.example.wigellrepairs.repositories;

import com.example.wigellrepairs.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookingsRepository extends JpaRepository<Booking, Long> {


}
