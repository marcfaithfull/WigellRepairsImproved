package com.example.wigellrepairs.services;

import com.example.wigellrepairs.dto.ServiceDto;
import com.example.wigellrepairs.entities.ServiceEntity;
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
class ServiceServiceTest {

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private ServiceEntityServiceImpl serviceService;

    @Test
    void ShouldReturnListOfServices() {
        // Arrange
        ServiceEntity testServiceOne = new ServiceEntity();
        testServiceOne.setWigellRepairsServiceId(1L);
        testServiceOne.setWigellRepairsServiceName("Service A");
        testServiceOne.setWigellRepairsServiceType("Car");
        testServiceOne.setWigellRepairsServicePrice(1000);

        ServiceEntity testServiceTwo = new ServiceEntity();
        testServiceTwo.setWigellRepairsServiceId(2L);
        testServiceTwo.setWigellRepairsServiceName("Service B");
        testServiceTwo.setWigellRepairsServiceType("Electronics");
        testServiceTwo.setWigellRepairsServicePrice(2000);

        List<ServiceEntity> servicesTestList = Arrays.asList(testServiceOne, testServiceTwo);

        when(serviceRepository.findAll()).thenReturn(servicesTestList);

        // Expected
        List<ServiceDto> expected = ServiceDto.serviceDtoList(servicesTestList);

        // Act
        List<ServiceDto> actual = serviceService.getServices();

        // Assert
        assertEquals(expected, actual);
        verify(serviceRepository, times(1)).findAll();
    }

}