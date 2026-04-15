-- Create Price Config
INSERT INTO public.pricing_config
(id, base_delivery_price, price_per_km, min_km, service_fee, commission_rate, boost_multiplier, active, date_created, date_updated, user_create, user_update)
VALUES(1, 5.00, 1.50, 2.00, 0.50, 0.1000, 1.00, true, '2026-04-04 10:00:00.000', NULL, 'admin', NULL);

--Add Admin
INSERT INTO public.glx_user_account
(user_active, user_id, date_updated, user_date_created, created_by, user_email, user_name, "role", user_password)
VALUES(true, 1, NULL, '2026-04-01 01:00:54.573', 'admin', 'lucas.sdm92@gmail.com', 'lucas.melo', 'ADMIN', '$2a$10$.oJPFtfqiQPgAF/W4ml3yevyZz8jRamZJHWUR0IBiZpBZoL9p5sIO');

--Add Rider Teste

--Create User Account for Rider
INSERT INTO public.glx_user_account
(user_active, user_id, date_updated, user_date_created, created_by, user_email, user_name, "role", user_password)
VALUES(true, 6, NULL, '2026-04-03 15:52:40.889', 'Lucas', 'rider@testegalaxy.com', 'rider.melo', 'RIDER', '$2a$10$y1E93kjLD9yhb.0yUslf3.PbuubiCaDI5hgAmsKZni8tzNNIOMkX.');

--Create a Rider with user account
INSERT INTO public.glx_rider
(id, status, address, created_by, date_created, date_updated, drive_license, identification_document, "name", phone, "rider_type", vat, vat_number, vehicle_plate, "vehicle_type", user_id, unique_code)
VALUES(5, 'OFFLINE', '62 Main Street Test, Dublin', 'Lucas', '2026-04-03 17:44:44.233', NULL, 'DL9876543', 'PA1234567', 'Rider Teste', '+353 00 000 0000', 'STUDENT', 'IE1234567T', '1234567T', NULL, 'BICYCLE', 6, 4313);



--CREATE A CUSTOMER (Store)
-- Create User for Customer
INSERT INTO public.glx_user_account
(user_active, user_id, date_updated, user_date_created, created_by, user_email, user_name, "role", user_password)
VALUES(true, 3, NULL, '2026-04-02 01:17:50.308', 'Lucas', 'jj@teste.com', 'jhon.joe', 'CLIENT', '$2a$10$bj9Cyj7gdURYDUjqhIbkneFbkqt5AwhaoFHpsZoGPiWBHGejFPn9G');

-- Create Store based user account client
INSERT INTO public.glx_client
(id, active, address, COUNTRY_code,created_by, date_created, date_updated, "name", phone, vat, user_id)
VALUES(1, true, '123 Main Street, Dublin', 'IE','admin', '2026-04-02 01:18:17.440', NULL, 'Brazilian Food', '+353 87 123 4567', 'IE1234567T', 3);
