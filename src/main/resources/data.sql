INSERT INTO wigellrepairs_technicians (wigell_repairs_technician_name,
                                      wigell_repairs_area_of_expertise)
VALUES
    ('Fred','Car'),
    ('Sven','Car'),
    ('Carl','Electronics'),
    ('Bella','Electronics'),
    ('Göran', 'White goods'),
    ('Katie','White goods');

INSERT INTO wigellrepairs_services (wigell_repairs_service_name,
                                    wigell_repairs_service_type,
                                    wigell_repairs_service_price,
                                    wigell_repairs_technician_id)
VALUES
    ('Front road-side axle replacement','Car',2000,1),
    ('Windscreen wiper replacement','Car',300,2),
    ('Phone screen replacement','Electronic',500,3),
    ('Laptop speaker replacement','Electronic',1000,4),
    ('Washing machine drum replacement','White goods',500,5),
    ('Fridge fan replacement','White goods',400,6);

INSERT INTO wigellrepairs_bookings (wigell_repairs_booking_customer,
                                    wigell_repairs_service_id,
                                    wigell_repairs_booking_date,
                                    wigell_repairs_booking_cancelled)
VALUES
    ('Kurt',1,'2025-11-01',false),
    ('Kurt',2,'2010-10-04',false),
    ('Dave',3,'2025-11-16',false),
    ('Dave',1,'2000-11-23',false),
    ('Krist',2,'2025-12-19',false),
    ('Krist',3,'2020-12-06',false);
