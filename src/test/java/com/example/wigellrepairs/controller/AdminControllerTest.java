package com.example.wigellrepairs.controller;


import com.example.wigellrepairs.dto.TechnicianDto;
import com.example.wigellrepairs.entities.Technician;
import com.example.wigellrepairs.repositories.TechnicianRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TechnicianRepository techRepo;

    @Test()
    void cantAccessAdminControllerWithoutCredentials() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/wigellrepairs/technicians"))
                .andExpect(status().is(401));

    }
    @WithMockUser(username = "Courtney", password = "Love", roles = "ADMIN")
    @Test
    void canGetTechnicians() throws Exception {
        // Arrange
        Technician tech = new Technician("aName","anExpertise");
        techRepo.save(tech);


        var response = mockMvc.perform(MockMvcRequestBuilders.get("/api/wigellrepairs/technicians"))
                .andExpect(status().isOk()).andReturn();

        var jsonResponse = response.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        List<TechnicianDto> technicians = mapper.readValue(jsonResponse, new TypeReference<List<TechnicianDto>>() {});

        var persistedTechnicianInList = technicians.stream().filter(dto -> dto.getAreaOfExpertise().equals(tech.getWigellRepairsAreaOfExpertise()) && dto.getName().equals(tech.getWigellRepairsTechnicianName())).toList();

        Assertions.assertFalse(technicians.isEmpty());;//we received a list

        Assertions.assertEquals(1, persistedTechnicianInList.size()); //the persisted technician is in listResponse. // Maybe we can test AddTechnician, and later use a test that addstechnician via controller and later checks calling technicians from the endpoint

    }


}