CREATE TABLE Weather
(
    year INT,
    month INT,
    day INT,
    hour INT,
    placeID INT,
    temperature varchar(128),
    rainfall varchar(128),
    winddirection varchar(128),
    windspeed varchar(128),
    humidity varchar(128),
    primary key(year, month, day, hour, placeID)
)
