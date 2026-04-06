-- Add rider_id to delivery table
ALTER TABLE delivery ADD COLUMN rider_id INTEGER REFERENCES rider(id);

-- Delivery history table
CREATE TABLE delivery_history (
    id          SERIAL PRIMARY KEY,
    delivery_id INTEGER     NOT NULL REFERENCES delivery(id),
    rider_id    INTEGER     NOT NULL REFERENCES rider(id),
    action      VARCHAR(20) NOT NULL CHECK (action IN ('ACCEPTED', 'REFUSED')),
    date_created TIMESTAMP
);
