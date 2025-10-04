INSERT INTO wigellrepairs_technician (wigell_repairs_technician_name,
                                      wigell_repairs_area_of_expertise)
VALUES
    ('Fred','Car'),
    ('Carl','Electronics');

INSERT INTO wigellrepairs_services (wigell_repairs_service_id,
                                    wigell_repairs_service_name,
                                    wigell_repairs_service_type,
                                    wigell_repairs_service_price,
                                    wigell_repairs_technician_id)
VALUES
    (1,'Oil change','Car',300,1),
    (2,'Winter tyre change','Car',500,1);

INSERT INTO wigellrepairs_bookings (wigell_repairs_booking_customer,
                                    wigell_repairs_service_id,
                                    wigell_repairs_booking_start_date,
                                    wigell_repairs_booking_end_date,
                                    wigell_repairs_booking_total_price,
                                    wigell_repairs_booking_cancelled)
VALUES
    ('Kurt',1,'2025-11-01','2025-12-01',15500, false),
    ('Dave',2,'2025-11-15','2025-12-01',8500, false);
