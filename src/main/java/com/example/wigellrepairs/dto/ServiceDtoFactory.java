package com.example.wigellrepairs.dto;

import com.example.wigellrepairs.entities.ServiceEntity;
import com.example.wigellrepairs.services.converters.ConvertSekToEuro;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceDtoFactory {

    private final ConvertSekToEuro convertSekToEuro;

    public ServiceDtoFactory(ConvertSekToEuro convertSekToEuro) {
        this.convertSekToEuro = convertSekToEuro;
    }

    public ServiceDto fromEntity(ServiceEntity entity) {
        int priceSEK = entity.getWigellRepairsServicePrice();
        double priceEUR = convertSekToEuro.convertSEKtoEUR(priceSEK);

        ServiceDto dto = new ServiceDto();
        dto.setServiceId(entity.getWigellRepairsServiceId());
        dto.setServiceName(entity.getWigellRepairsServiceName());
        dto.setServiceType(entity.getWigellRepairsServiceType());
        dto.setPriceInSEK(priceSEK);
        dto.setPriceInEURO(priceEUR);
        return dto;
    }

    public List<ServiceDto> fromEntityList(List<ServiceEntity> entities) {
        return entities.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }
}
