package com.example.wigellrepairs.services;

import com.example.wigellrepairs.dto.TechnicianDto;
import com.example.wigellrepairs.entities.Technician;
import com.example.wigellrepairs.exceptions.ValidationException;
import com.example.wigellrepairs.repositories.TechnicianRepository;
import com.example.wigellrepairs.services.validators.technicianservice.AddTechnicianValidator;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TechnicianServiceImpl implements TechnicianService {
    private final TechnicianRepository technicianRepository;
    private final AddTechnicianValidator addTechnicianValidator;
    private final Logger TECHNICIAN_LOGGER = LogManager.getLogger(TechnicianServiceImpl.class);

    @Autowired
    public TechnicianServiceImpl(TechnicianRepository technicianRepository, AddTechnicianValidator addTechnicianValidator) {
        this.technicianRepository = technicianRepository;
        this.addTechnicianValidator = addTechnicianValidator;
    }

    @Override
    public void addTechnician(Technician technician) throws ValidationException {
        addTechnicianValidator.validateExpertise(technician.getWigellRepairsAreaOfExpertise());
        technicianRepository.save(technician);
        TECHNICIAN_LOGGER.info("Technician '{}' with expertise '{}' was added to the database",
                technician.getWigellRepairsTechnicianName(),
                technician.getWigellRepairsAreaOfExpertise());
    }

    public List<TechnicianDto> getTechnicians() {
        return TechnicianDto.technicianDtoList(technicianRepository.findAll());
    }
}
