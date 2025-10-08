package com.example.wigellrepairs.services;

import com.example.wigellrepairs.dto.BookingDto;
import com.example.wigellrepairs.dto.ServiceDto;
import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.Service;
import com.example.wigellrepairs.repositories.BookingsRepository;
import com.example.wigellrepairs.repositories.ServicesRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private BookingsRepository bookingsRepository;
    private ServicesRepository servicesRepository;
    private static final Logger CUSTOMER_SERVICE_LOGGER = LogManager.getLogger(CustomerServiceImpl.class);

    @Autowired
    public CustomerServiceImpl(BookingsRepository bookingsRepository, ServicesRepository servicesRepository) {
        this.bookingsRepository = bookingsRepository;
        this.servicesRepository = servicesRepository;
    }

    @Override
    public List<ServiceDto> services() {
        return ServiceDto.serviceDtoList(servicesRepository.findAll());
    }

    @Override
    public ResponseEntity<String> bookService(Booking booking, Principal principal) {
        Long serviceId = booking.getWigellRepairsBookingService().getWigellRepairsServiceId();
        Service serviceToBook = servicesRepository.findServiceByWigellRepairsServiceId(serviceId);
        if (serviceToBook == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no service with this id");
        }
        if (booking.getWigellRepairsBookingDate().isBefore(LocalDate.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You cannot book a service in the past");
        }
        List<Booking> bookings = bookingsRepository.findAll();
        for (Booking bookingInList : bookings) {
            if (bookingInList.getWigellRepairsBookingDate().equals(booking.getWigellRepairsBookingDate())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This date is not available. Try another date");
            }
        }
        booking.setWigellRepairsBookingCustomer(principal.getName());
        bookingsRepository.save(booking);
        CUSTOMER_SERVICE_LOGGER.info("{} booked service with id:{}",
                booking.getWigellRepairsBookingCustomer(), booking.getWigellRepairsBookingService().getWigellRepairsServiceId());
        return ResponseEntity.status(HttpStatus.CREATED).body("The service has been booked");
    }

    @Override
    public ResponseEntity<String> cancelBooking(Booking booking, Principal principal) {
        Optional<Booking> optionalBooking = bookingsRepository.findById(booking.getWigellRepairsBookingId());
        if (!optionalBooking.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no booking with this id");
        }
        Booking bookingToCancel = bookingsRepository.findById(booking.getWigellRepairsBookingId())
                .orElseThrow(EntityNotFoundException::new);
        if (!bookingToCancel.getWigellRepairsBookingCustomer().equals(principal.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorised to cancel this booking");
        }
        if (bookingToCancel.getWigellRepairsBookingDate().minusDays(1).isBefore(LocalDate.now())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("It is too late to cancel this service");
        }
        if (booking.getWigellRepairsBookingCancelled().equals(false)) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("wigellRepairsBookingCancelled must be set to 'true' to cancel a booking");
        }
        bookingToCancel.setWigellRepairsBookingCancelled(true);
        bookingsRepository.save(bookingToCancel);
        return ResponseEntity.status(HttpStatus.OK).body("Your booking has been cancelled");
    }

    @Override
    public List<BookingDto> myBookings(Principal principal) {
        List<Booking> allBookings = bookingsRepository.findAll();
        List<Booking> myBookings = new ArrayList<>();
        for (Booking booking : allBookings) {
            if (booking.getWigellRepairsBookingCustomer().equals(principal.getName()) && (booking.getWigellRepairsBookingCancelled().equals(false))) {
                myBookings.add(booking);
            }
        }
        return BookingDto.bookingDtoList(myBookings);
    }
}
