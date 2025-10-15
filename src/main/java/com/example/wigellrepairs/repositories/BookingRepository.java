package com.example.wigellrepairs.repositories;

import com.example.wigellrepairs.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByWigellRepairsBookingCancelledTrue();

    List<Booking> findByWigellRepairsBookingDateBefore(LocalDate date);

    List<Booking> findByWigellRepairsBookingDateAfterAndWigellRepairsBookingCancelledFalse(LocalDate date);
}