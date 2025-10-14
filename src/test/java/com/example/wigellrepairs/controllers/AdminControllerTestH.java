package com.example.wigellrepairs.controllers;

import com.example.wigellrepairs.dto.TechnicianDto;

import com.example.wigellrepairs.entities.Technician;
import com.example.wigellrepairs.repositories.TechnicianRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTestH {


        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private TechnicianRepository techRepo;

        @Test
        void  technicians() throws Exception {
            // Arrange
            Technician tech = new Technician("aName","anExpertise");
            techRepo.save(tech);

            // Act & Assert
            mockMvc.perform(MockMvcRequestBuilders.get("/api/wigellrepairs/technicians"))
                    .andExpect(status().isOk());

        }

}