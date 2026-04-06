INSERT INTO pricing_config (
    base_delivery_price,
    price_per_km,
    min_km,
    service_fee,
    commission_rate,
    boost_multiplier,
    active,
    date_created,
    user_create
) VALUES (
    8.00,
    0.50,
    3.00,
    1.50,
    0.1000,
    null,
    true,
    NOW(),
    'admin'
);
