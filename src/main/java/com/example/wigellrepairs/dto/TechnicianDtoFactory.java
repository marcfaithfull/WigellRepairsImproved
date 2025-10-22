package com.example.wigellrepairs.dto;

import com.example.wigellrepairs.entities.Technician;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TechnicianDtoFactory {

    public TechnicianDto technicianDto(Technician technician) {
        TechnicianDto technicianDto = new TechnicianDto();
        technicianDto.setTechnicianId(technician.getWigellRepairsTechnicianId());
        technicianDto.setName(technician.getWigellRepairsTechnicianName());
        technicianDto.setAreaOfExpertise(technician.getWigellRepairsAreaOfExpertise());
        return technicianDto;
    }

    public List<TechnicianDto> technicianDtoList(List<Technician> technicians) {
        return technicians.stream()
                .map(this::technicianDto)
                .collect(Collectors.toList());
    }
}
