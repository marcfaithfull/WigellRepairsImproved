package com.example.wigellrepairs.repositories;

import com.example.wigellrepairs.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepository extends JpaRepository<Service,Integer> {
// CRUD
}
