package com.example.wigellrepairs.repositories;

import com.example.wigellrepairs.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;


@Repository
public interface ServicesRepository extends JpaRepository<Service, Long> {

    void deleteServiceByWigellRepairsServiceId(@NonNull Long id);

    Service findServiceByWigellRepairsServiceId(Long id);
}
