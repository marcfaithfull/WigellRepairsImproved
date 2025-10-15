package com.example.wigellrepairs.controllers;

import com.example.wigellrepairs.dto.ServiceDto;
import com.example.wigellrepairs.entities.ServiceEntity;
import com.example.wigellrepairs.services.ServiceEntityService;
import com.example.wigellrepairs.services.ServiceEntityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javax.security.auth.callback.ConfirmationCallback.OK;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @InjectMocks
    private ServiceEntityServiceImpl serviceEntityService;

    @MockitoBean
    private CustomerController customerController;

    @Test
    void services() {
        ServiceDto serviceDto = new ServiceDto(1L, "serviceName");
        List<ServiceDto> expected = List.of(serviceDto);

        when(serviceEntityService.getServices()).thenReturn(expected);

        List<ServiceDto> directCall = serviceEntityService.getServices();
        System.out.println(directCall);

        ResponseEntity<List<ServiceDto>> actual = customerController.services();

        System.out.println("Expected: " + expected);
        System.out.println("Actual: " + actual.getBody());

        verify(serviceEntityService, times(1)).getServices();

        assertEquals(200, actual.getStatusCode().value());
        assertNotNull(actual.getBody());
    }

    @Test
    void bookAService() {
    }

    @Test
    void cancelBooking() {
    }

    @Test
    void myBookings() {
    }
}