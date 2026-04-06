INSERT INTO price_calculation (
    total_price,
    base_delivery_price,
    commission_value,
    service_fee_value,
    exceeded_km_cost,
    distance_km,
    status,
    client_id,
    date_created
) VALUES (
    12.50,
    8.00,
    1.25,
    1.50,
    1.75,
    5.30,
    'PENDING',
    1,
    NOW()
);
