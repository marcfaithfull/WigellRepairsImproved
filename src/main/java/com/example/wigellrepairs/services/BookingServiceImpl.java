package com.example.wigellrepairs.services;

import com.example.wigellrepairs.dto.BookingDto;
import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.Service;
import com.example.wigellrepairs.repositories.BookingRepository;
import com.example.wigellrepairs.repositories.ServiceRepository;
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
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ServiceRepository serviceRepository;
    private final Logger CUSTOMER_SERVICE_LOGGER = LogManager.getLogger(BookingServiceImpl.class);

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, ServiceRepository serviceRepository) {
        this.bookingRepository = bookingRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public ResponseEntity<String> bookService(Booking booking, Principal principal) {
        Service serviceToBook = serviceRepository.findServiceByWigellRepairsServiceId(booking.getWigellRepairsBookingService().getWigellRepairsServiceId());
        if (serviceToBook == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no service with this id");
        }
        if (booking.getWigellRepairsBookingDate().isBefore(LocalDate.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You cannot book using a past date");
        }
        List<Booking> bookings = bookingRepository.findAll();
        for (Booking bookingInList : bookings) {
            if (bookingInList.getWigellRepairsBookingDate().equals(booking.getWigellRepairsBookingDate())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This date is not available. Try another date");
            }
        }
        booking.setWigellRepairsBookingCustomer(principal.getName());
        bookingRepository.save(booking);
        CUSTOMER_SERVICE_LOGGER.info("{} booked service with id:{}",
                booking.getWigellRepairsBookingCustomer(), booking.getWigellRepairsBookingService().getWigellRepairsServiceId());
        return ResponseEntity.status(HttpStatus.CREATED).body("The service has been booked");
    }

    @Override
    public ResponseEntity<String> cancelBooking(Booking booking, Principal principal) {
        Optional<Booking> optionalBooking = bookingRepository.findById(booking.getWigellRepairsBookingId());
        if (optionalBooking.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no booking with this id");
        }
        Booking bookingToCancel = optionalBooking.get();
        if (!bookingToCancel.getWigellRepairsBookingCustomer().equals(principal.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorised to cancel this booking");
        }
        if (bookingToCancel.getWigellRepairsBookingDate().minusDays(1).isBefore(LocalDate.now())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("It is too late to cancel this booking");
        }
        if (booking.getWigellRepairsBookingCancelled().equals(false)) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("wigellRepairsBookingCancelled must be set to 'true' to cancel a booking");
        }
        bookingToCancel.setWigellRepairsBookingCancelled(true);
        bookingRepository.save(bookingToCancel);
        return ResponseEntity.status(HttpStatus.OK).body("Your booking has been cancelled");
    }

    @Override
    public List<BookingDto> myBookings(Principal principal) {
        List<Booking> allBookings = bookingRepository.findAll();
        List<Booking> myBookings = new ArrayList<>();
        for (Booking booking : allBookings) {
            if (booking.getWigellRepairsBookingCustomer().equals(principal.getName()) && (booking.getWigellRepairsBookingCancelled().equals(false))) {
                myBookings.add(booking);
            }
        }
        return BookingDto.bookingDtoList(myBookings);
    }

    @Override
    public List<Booking> listCancelled() {
        return bookingRepository.findByWigellRepairsBookingCancelledTrue();
    }

    @Override
    public List<Booking> listUpcoming() {
        List<Booking> allBookings = bookingRepository.findAll();
        List<Booking> upcomingBookings = new ArrayList<>();
        for (Booking booking : allBookings) {
            if (booking.getWigellRepairsBookingDate().isAfter(LocalDate.now()) && (!booking.getWigellRepairsBookingCancelled())) {
                upcomingBookings.add(booking);
            }
        }
        return upcomingBookings;
    }

    @Override
    public List<Booking> listPast() {
        List<Booking> allBookings = bookingRepository.findAll();
        List<Booking> pastBookings = new ArrayList<>();
        for (Booking booking : allBookings) {
            if (booking.getWigellRepairsBookingDate().isBefore(LocalDate.now())) {
                pastBookings.add(booking);
            }
        }
        return pastBookings;
    }
}
