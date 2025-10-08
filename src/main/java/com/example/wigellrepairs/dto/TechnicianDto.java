package com.example.wigellrepairs.dto;

import com.example.wigellrepairs.entities.Technician;

import java.util.List;
import java.util.stream.Collectors;

public class TechnicianDto {
    private Long technicianId;
    private String name;
    private String areaOfExpertise;

    public static TechnicianDto technicianDto(Technician technician) {
        TechnicianDto technicianDto = new TechnicianDto();
        technicianDto.setTechnicianId(technician.getWigellRepairsTechnicianId());
        technicianDto.setName(technician.getWigellRepairsTechnicianName());
        technicianDto.setAreaOfExpertise(technician.getWigellRepairsAreaOfExpertise());
        return technicianDto;
    }

    public static List<TechnicianDto> technicianDtoList(List<Technician> technicians) {
        return technicians.stream()
                .map(TechnicianDto::technicianDto)
                .collect(Collectors.toList());
    }

    public Long getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(Long technicianId) {
        this.technicianId = technicianId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaOfExpertise() {
        return areaOfExpertise;
    }

    public void setAreaOfExpertise(String areaOfExpertise) {
        this.areaOfExpertise = areaOfExpertise;
    }
}
