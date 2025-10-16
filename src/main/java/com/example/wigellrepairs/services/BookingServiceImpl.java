package com.example.wigellrepairs.services;

import com.example.wigellrepairs.dto.BookingDto;
import com.example.wigellrepairs.dto.BookingRequestDto;
import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.ServiceEntity;
import com.example.wigellrepairs.exceptions.*;
import com.example.wigellrepairs.repositories.BookingRepository;
import com.example.wigellrepairs.repositories.ServiceRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
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
    public void bookService(BookingRequestDto bookingRequestDto, Principal principal) throws ServiceNotFoundException, DateException {
        ServiceEntity serviceToBook = serviceRepository.findServiceByWigellRepairsServiceId(bookingRequestDto.getServiceId());

        if (serviceToBook == null) {
            throw new ServiceNotFoundException();
        }

        LocalDate requestedDate = bookingRequestDto.getDateOfService();
        if (requestedDate.isBefore(LocalDate.now())) {
            throw new DateException("You cannot booking using a past date");
        }

        List<Booking> existingBookings = bookingRepository.findAll();
        for (Booking bookingInList : existingBookings) {
            if (bookingInList.getWigellRepairsBookingDate().equals(requestedDate)) {
            throw new DateException("This date is not available. Try another date");}
        }

        Booking booking = new Booking();
        booking.setWigellRepairsBookingCustomer(principal.getName());
        booking.setWigellRepairsBookingDate(requestedDate);
        booking.setWigellRepairsBookingService(serviceToBook);
        bookingRepository.save(booking);
        CUSTOMER_SERVICE_LOGGER.info("{} booked service with id:{}",
                booking.getWigellRepairsBookingCustomer(), booking.getWigellRepairsBookingService().getWigellRepairsServiceId());
    }

    @Override
    public void cancelBooking(Booking booking, Principal principal) throws BookingException, BookingNotFoundException, UnauthorisedUserException {
        Optional<Booking> optionalBooking = bookingRepository.findById(booking.getWigellRepairsBookingId());

        if (optionalBooking.isEmpty()) {
            throw new BookingNotFoundException("There is no booking with this id in the database");
        }

        Booking bookingToCancel = optionalBooking.get();

        if (!bookingToCancel.getWigellRepairsBookingCustomer().equals(principal.getName())) {
            throw new UnauthorisedUserException("You are not unauthorised to cancel this booking");
        }

        if (bookingToCancel.getWigellRepairsBookingDate().minusDays(1).isBefore(LocalDate.now())) {
            throw new BookingException("It is too late to cancel this booking");
        }

        bookingToCancel.setWigellRepairsBookingCancelled(true);

        bookingRepository.save(bookingToCancel);
        CUSTOMER_SERVICE_LOGGER.info("{} cancelled booking with id:{}",
                principal.getName(), bookingToCancel.getWigellRepairsBookingCustomer());
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
        return bookingRepository.findByWigellRepairsBookingDateAfterAndWigellRepairsBookingCancelledFalse(LocalDate.now());
    }

    @Override
    public List<Booking> listPast() {
        return bookingRepository.findByWigellRepairsBookingDateBefore(LocalDate.now());
    }
}
