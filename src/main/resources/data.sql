INSERT INTO wigellrepairs_technicians (wigell_repairs_technician_name,
                                      wigell_repairs_area_of_expertise)
VALUES
    ('Fred','Car'),
    ('Carl','Electronics');

INSERT INTO wigellrepairs_services (wigell_repairs_service_name,
                                    wigell_repairs_service_type,
                                    wigell_repairs_service_price,
                                    wigell_repairs_technician_id)
VALUES
    ('Oil change','Car',1000,1),
    ('Winter tyre change','Car',300,1);

INSERT INTO wigellrepairs_bookings (wigell_repairs_booking_customer,
                                    wigell_repairs_service_id,
                                    wigell_repairs_booking_date,
                                    wigell_repairs_booking_total_price,
                                    wigell_repairs_booking_cancelled)
VALUES
    ('Kurt',1,'2025-11-01',1000,false),
    ('Dave',2,'2024-11-15',300,false),
    ('Kurt',1,'2025-10-04',1000,false);
