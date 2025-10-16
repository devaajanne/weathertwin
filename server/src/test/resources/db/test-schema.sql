CREATE TABLE IF NOT EXISTS weatherdatatable (
    id BIGINT PRIMARY KEY,
    city VARCHAR(255),
    country_code VARCHAR(2),
    country_name VARCHAR(255),
    lat FLOAT,
    lon FLOAT,
    temp FLOAT,
    temp_unit VARCHAR(10),
    weather_group VARCHAR(50),
    weather_id VARCHAR(10)
);