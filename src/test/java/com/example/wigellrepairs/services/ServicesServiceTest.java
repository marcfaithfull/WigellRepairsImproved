package com.example.wigellrepairs.services;

import com.example.wigellrepairs.dto.ServiceDto;
import com.example.wigellrepairs.entities.Service;
import com.example.wigellrepairs.repositories.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicesServiceTest {

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private ServicesServiceImpl serviceService;

    @Test
    void ShouldReturnListOfServices() {
        // Arrange
        Service testServiceOne = new Service();
        testServiceOne.setWigellRepairsServiceId(1L);
        testServiceOne.setWigellRepairsServiceName("Service A");
        testServiceOne.setWigellRepairsServiceType("Car");
        testServiceOne.setWigellRepairsServicePrice(1000);

        Service testServiceTwo = new Service();
        testServiceTwo.setWigellRepairsServiceId(2L);
        testServiceTwo.setWigellRepairsServiceName("Service B");
        testServiceTwo.setWigellRepairsServiceType("Electronics");
        testServiceTwo.setWigellRepairsServicePrice(2000);

        List<Service> servicesTestList = Arrays.asList(testServiceOne, testServiceTwo);

        when(serviceRepository.findAll()).thenReturn(servicesTestList);

        // Expected
        List<ServiceDto> expected = ServiceDto.serviceDtoList(servicesTestList);

        // Act
        List<ServiceDto> actual = serviceService.services();

        // Assert
        assertEquals(expected, actual);
        verify(serviceRepository, times(1)).findAll();
    }

}