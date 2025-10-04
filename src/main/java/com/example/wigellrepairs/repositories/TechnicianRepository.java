package com.example.wigellrepairs.repositories;

import com.example.wigellrepairs.entities.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Long> {
// CRUD
}
