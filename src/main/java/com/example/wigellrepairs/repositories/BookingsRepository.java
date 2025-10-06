package com.example.wigellrepairs.repositories;

import com.example.wigellrepairs.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookingsRepository extends JpaRepository<Booking, Long> {


    Booking findByWigellRepairsBookingId(Long wigellRepairsServiceId);

    boolean existsByServiceIdAndNotCancelledAndDateAfter(
            @Param("wigell_repairs_service_id") Long id,
            @Param("wigell_repairs_booking_cancelled") boolean cancelled,
            @Param("wigell_repairs_booking_date") LocalDate date
    );
}