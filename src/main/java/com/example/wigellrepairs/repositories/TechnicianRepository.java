package com.example.wigellrepairs.repositories;

import com.example.wigellrepairs.entities.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Long> {

    @Query("SELECT s.wigellRepairsTechnicianName FROM Technician s")
    List<String> findAllTechnicianNames();

    Technician findTechniciansByWigellRepairsTechnicianId(Long id);
}
