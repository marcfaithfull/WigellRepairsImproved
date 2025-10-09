package com.example.wigellrepairs.repositories;

import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookingsRepository extends JpaRepository<Booking, Long> {

    Booking findServiceByWigellRepairsServiceId(Long id);

}