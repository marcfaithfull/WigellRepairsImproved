package com.example.wigellrepairs.dto;

import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.ServiceEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingDtoFactory {

    private final ServiceDtoFactory serviceDtoFactory;

    public BookingDtoFactory(ServiceDtoFactory serviceDtoFactory) {
        this.serviceDtoFactory = serviceDtoFactory;
    }

    public BookingDto fromEntity(Booking booking) {
        ServiceEntity serviceEntity = booking.getWigellRepairsBookingService();
        ServiceDto serviceDto = serviceDtoFactory.fromEntity(serviceEntity);

        BookingDto dto = new BookingDto();
        dto.setBookingId(booking.getWigellRepairsBookingId());
        dto.setCustomer(booking.getWigellRepairsBookingCustomer());
        dto.setService(serviceDto);
        dto.setDateOfService(booking.getWigellRepairsBookingDate());
        return dto;
    }

    public List<BookingDto> fromEntityList(List<Booking> bookings) {
        return bookings.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }
}
