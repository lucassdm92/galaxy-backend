CREATE TYPE vehicle_type AS ENUM ('BICYCLE', 'MOTORCYCLE', 'CAR', 'VAN');
CREATE TYPE rider_type AS ENUM ('STUDENT', 'AUTONOMOUS');

CREATE TABLE rider (
    id                      SERIAL PRIMARY KEY,
    name                    VARCHAR(100)    NOT NULL,
    phone                   VARCHAR(20),
    address                 VARCHAR(255),
    vehicle_type            vehicle_type    NOT NULL,
    vehicle_plate           VARCHAR(20),
    identification_document VARCHAR(50)     NOT NULL,
    drive_license           VARCHAR(50)     NOT NULL,
    vat_number              VARCHAR(20),
    vat                     VARCHAR(20),
    rider_type              rider_type      NOT NULL,
    email                   VARCHAR(150)    NOT NULL UNIQUE,
    password                VARCHAR(255)    NOT NULL,
    active                  BOOLEAN         DEFAULT TRUE,
    created_by              VARCHAR(100),
    date_created            TIMESTAMP,
    date_updated            TIMESTAMP
);
