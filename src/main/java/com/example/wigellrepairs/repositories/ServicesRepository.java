package com.example.wigellrepairs.repositories;

import com.example.wigellrepairs.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicesRepository extends JpaRepository<Service, Long> {

    @Query("SELECT s.wigellRepairsServiceName FROM Service s")
    List<String> findAllServiceNames();

    void deleteServiceByWigellRepairsServiceId(@NonNull Long id);

    Service findServiceByWigellRepairsServiceId(Long wigellRepairsServiceId);
}
