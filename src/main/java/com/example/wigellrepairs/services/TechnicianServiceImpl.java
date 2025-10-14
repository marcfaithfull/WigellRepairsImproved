package com.example.wigellrepairs.services;

import com.example.wigellrepairs.dto.TechnicianDto;
import com.example.wigellrepairs.entities.Technician;
import com.example.wigellrepairs.repositories.TechnicianRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnicianServiceImpl implements TechnicianService {
    private final TechnicianRepository TECHNICIAN_REPOSITORY;
    private final Logger TECHNICIAN_LOGGER = LogManager.getLogger(TechnicianServiceImpl.class);

    @Autowired
    public TechnicianServiceImpl(TechnicianRepository technicianRepository) {
        this.TECHNICIAN_REPOSITORY = technicianRepository;
    }

    @Transactional
    @Override
    public ResponseEntity<String> addTechnician(Technician technician) {
        technician.setWigellRepairsTechnicianId(null);
        String expertise = technician.getWigellRepairsAreaOfExpertise();
        if (!expertise.equals("Car") && !expertise.equals("White goods") && !expertise.equals("Electronics")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Area of expertise must be Car, White goods or Electronics (Case sensitive)");
        }
        TECHNICIAN_REPOSITORY.save(technician);
        TECHNICIAN_LOGGER.info("Technician '{}' with the area of expertise '{}' was added to the database",
                technician.getWigellRepairsTechnicianName(), technician.getWigellRepairsAreaOfExpertise());
        return ResponseEntity.status(HttpStatus.CREATED).body("Technician added successfully");
    }

    public List<TechnicianDto> technicians() {
        return TechnicianDto.technicianDtoList(TECHNICIAN_REPOSITORY.findAll());
    }
}
