package com.example.wigellrepairs.repositories;

import com.example.wigellrepairs.entities.BookingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingsRepository extends JpaRepository<BookingsEntity, Long> {
}
