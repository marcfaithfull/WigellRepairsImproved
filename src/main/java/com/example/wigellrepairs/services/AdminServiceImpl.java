package com.example.wigellrepairs.services;

import com.example.wigellrepairs.dto.TechnicianDto;
import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.Service;
import com.example.wigellrepairs.entities.Technician;
import com.example.wigellrepairs.repositories.BookingsRepository;
import com.example.wigellrepairs.repositories.ServicesRepository;
import com.example.wigellrepairs.repositories.TechnicianRepository;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@Transactional
@PersistenceContext
public class AdminServiceImpl implements AdminService {
    private final BookingsRepository bookingsRepository;
    private final ServicesRepository servicesRepository;
    private final TechnicianRepository technicianRepository;
    private static final Logger ADMIN_SERVICE_LOGGER = LogManager.getLogger(AdminServiceImpl.class);

    public AdminServiceImpl(BookingsRepository bookingsRepository, ServicesRepository servicesRepository, TechnicianRepository technicianRepository) {
        this.bookingsRepository = bookingsRepository;
        this.servicesRepository = servicesRepository;
        this.technicianRepository = technicianRepository;
    }

    @Override
    public List<Booking> listCancelled() {
        List<Booking> allBookings = bookingsRepository.findAll();
        List<Booking> cancelledBookings = new ArrayList<>();
        for (Booking booking : allBookings) {
            if (booking.getWigellRepairsBookingCancelled()) {
                cancelledBookings.add(booking);
            }
        }
        return cancelledBookings;
    }

    @Override
    public List<Booking> listUpcoming() {
        List<Booking> allBookings = bookingsRepository.findAll();
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
        List<Booking> allBookings = bookingsRepository.findAll();
        List<Booking> pastBookings = new ArrayList<>();
        for (Booking booking : allBookings) {
            if (booking.getWigellRepairsBookingDate().isBefore(LocalDate.now())) {
                pastBookings.add(booking);
            }
        }
        return pastBookings;
    }

    @Transactional
    @Override
    public ResponseEntity<String> addService(Service service) {
        if (!technicianRepository.existsById(service.getWigellRepairsServiceTechnician().getWigellRepairsTechnicianId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Choose a technician from the database");
        }
        if (!service.getWigellRepairsServiceType().equals("Car") &&
                !service.getWigellRepairsServiceType().equals("White goods") &&
                !service.getWigellRepairsServiceType().equals("Electronics")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
                    ("Area of expertise must be Car, White goods or Electronics (Case sensitive)");
        }
        Service serviceToSave = new Service();
        serviceToSave.setWigellRepairsServiceName(service.getWigellRepairsServiceName());
        serviceToSave.setWigellRepairsServiceType(service.getWigellRepairsServiceType());
        serviceToSave.setWigellRepairsServicePrice(service.getWigellRepairsServicePrice());
        Technician technician = technicianRepository.findTechnicianByWigellRepairsTechnicianId(
                service.getWigellRepairsServiceTechnician().getWigellRepairsTechnicianId());
        if (!technician.getWigellRepairsAreaOfExpertise().equals(service.getWigellRepairsServiceType())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Choose a technician with the correct area of expertise");
        }
        serviceToSave.setWigellRepairsServiceTechnician(technician);
        servicesRepository.save(serviceToSave);
        ADMIN_SERVICE_LOGGER.info("Service: '{}' was added to the database", serviceToSave.getWigellRepairsServiceName());
        return ResponseEntity.status(HttpStatus.OK).body("The service was added successfully");
    }

    @Override
    public ResponseEntity<String> updateService(Service service) {
        if (!servicesRepository.existsById(service.getWigellRepairsServiceId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no service with this id in the database");
        }
        Service serviceToUpdate = servicesRepository.findServiceByWigellRepairsServiceId(service.getWigellRepairsServiceId());
        if (!technicianRepository.existsById(service.getWigellRepairsServiceTechnician().getWigellRepairsTechnicianId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no technician with this id in the database");
        }
        Technician technicianToUpdate = technicianRepository.findTechnicianByWigellRepairsTechnicianId(service.getWigellRepairsServiceTechnician().getWigellRepairsTechnicianId());
        if (!technicianToUpdate.getWigellRepairsAreaOfExpertise().equals(serviceToUpdate.getWigellRepairsServiceType())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Choose a technician with the correct area of expertise");
        }
        serviceToUpdate.setWigellRepairsServiceName(service.getWigellRepairsServiceName());
        serviceToUpdate.setWigellRepairsServicePrice(service.getWigellRepairsServicePrice());
        serviceToUpdate.setWigellRepairsServiceTechnician(technicianToUpdate);
        ADMIN_SERVICE_LOGGER.info("Service with id '{}' was updated", service.getWigellRepairsServiceId());
        servicesRepository.save(serviceToUpdate);
        return ResponseEntity.status(HttpStatus.OK).body("This service has been updated");
    }

    @Transactional
    @Override
    public ResponseEntity<String> remService(Long id) {
        Service serviceToRemove = servicesRepository.findServiceByWigellRepairsServiceId(id);
        if (!servicesRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no service with this id in the database");
        }
        List<Booking> bookings = bookingsRepository.findAll();
        List<Booking> bookingsWithMatchingService = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getWigellRepairsBookingService().getWigellRepairsServiceId().equals(serviceToRemove.getWigellRepairsServiceId()) &&
                    booking.getWigellRepairsBookingCancelled().equals(false) &&
                    booking.getWigellRepairsBookingDate().isAfter(LocalDate.now())) {
                bookingsWithMatchingService.add(booking);
            }
        }
        if (!bookingsWithMatchingService.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You cannot remove this service because it has been booked by one or more users");
        }
        servicesRepository.deleteServiceByWigellRepairsServiceId(serviceToRemove.getWigellRepairsServiceId());
        ADMIN_SERVICE_LOGGER.info("Service: '{}' was removed from the database", serviceToRemove.getWigellRepairsServiceId());
        return ResponseEntity.status(HttpStatus.OK).body("The service has been removed");
    }

    @Transactional
    @Override
    public ResponseEntity<String> addTechnician(Technician technician) {
        technician.setWigellRepairsTechnicianId(null);
        String expertise = technician.getWigellRepairsAreaOfExpertise();
        if (!expertise.equals("Car") && !expertise.equals("White goods") && !expertise.equals("Electronics")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Area of expertise must be Car, White goods or Electronics (Case sensitive)");
        }
        technicianRepository.save(technician);
        ADMIN_SERVICE_LOGGER.info("Technician '{}' with the area of expertise '{}' was added to the database",
                technician.getWigellRepairsTechnicianName(), technician.getWigellRepairsAreaOfExpertise());
        return ResponseEntity.status(HttpStatus.CREATED).body("Technician added successfully");
    }

    public List<TechnicianDto> technicians() {
        return TechnicianDto.technicianDtoList(technicianRepository.findAll());
    }
}
