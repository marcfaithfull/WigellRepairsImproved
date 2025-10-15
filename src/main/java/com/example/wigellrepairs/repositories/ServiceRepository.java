package com.example.wigellrepairs.repositories;

import com.example.wigellrepairs.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;


@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

    void deleteServiceByWigellRepairsServiceId(Long id);

    ServiceEntity findServiceByWigellRepairsServiceId(Long id);
}
