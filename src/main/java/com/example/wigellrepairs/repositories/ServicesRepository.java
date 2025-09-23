package com.example.wigellrepairs.repositories;

import com.example.wigellrepairs.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepository extends JpaRepository<ServiceEntity,Integer> {

}
