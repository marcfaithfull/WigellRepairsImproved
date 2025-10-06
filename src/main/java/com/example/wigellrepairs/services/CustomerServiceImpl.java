package com.example.wigellrepairs.services;

import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.Service;
import com.example.wigellrepairs.repositories.BookingsRepository;
import com.example.wigellrepairs.repositories.ServicesRepository;
import com.example.wigellrepairs.repositories.TechnicianRepository;
import com.example.wigellrepairs.services.calculators.CurrencyConverter;
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
    private TechnicianRepository technicianRepository;
    private CurrencyConverter currencyConverter;
    private static final Logger CUSTOMER_SERVICE_LOGGER = LogManager.getLogger(CustomerServiceImpl.class);

    @Autowired
    public CustomerServiceImpl(BookingsRepository bookingsRepository, ServicesRepository servicesRepository,
                               TechnicianRepository technicianRepository, CurrencyConverter currencyConverter) {
        this.bookingsRepository = bookingsRepository;
        this.servicesRepository = servicesRepository;
        this.technicianRepository = technicianRepository;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public List<Service> services() {
        return servicesRepository.findAll();
    }

    @Override
    public void bookService(Booking booking, Principal principal) {
        booking.setWigellRepairsBookingCustomer(principal.getName());
        Long serviceId = booking.getWigellRepairsBookingService().getWigellRepairsServiceId();
        Service serviceToBook = servicesRepository.findServiceByWigellRepairsServiceId(serviceId);
        booking.setWigellRepairsBookingTotalPrice(serviceToBook.getWigellRepairsServicePrice());
        currencyConverter.convertSekToEuro(booking);
        bookingsRepository.save(booking);
        CUSTOMER_SERVICE_LOGGER.info("{} booked service with id:{}",
                booking.getWigellRepairsBookingCustomer(), booking.getWigellRepairsBookingService().getWigellRepairsServiceId());
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
        bookingToCancel.setWigellRepairsBookingCancelled(true);
        bookingsRepository.save(bookingToCancel);
        return ResponseEntity.status(HttpStatus.OK).body("Your booking has been cancelled");
    }

    @Override
    public List<Booking> myBookings(Principal principal) {
        List<Booking> allBookings = bookingsRepository.findAll();
        List<Booking> myBookings = new ArrayList<>();
        for (Booking booking : allBookings) {
            if (booking.getWigellRepairsBookingCustomer().equals(principal.getName()) && (booking.getWigellRepairsBookingCancelled().equals(false))) {
                myBookings.add(booking);
            }
        }
        return myBookings;
    }
}
