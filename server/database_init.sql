DROP TABLE IF EXISTS weatherdatatable;

CREATE TABLE weatherdatatable(
id BIGINT PRIMARY KEY,
lat FLOAT,
lon FLOAT,
city VARCHAR,
country_code VARCHAR,
country_name VARCHAR,
temp FLOAT,
temp_unit VARCHAR,
weather_group VARCHAR,
weather_icon VARCHAR
);