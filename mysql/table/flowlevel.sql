CREATE TABLE FlowLevel
(
    year INT,
    month INT,
    day INT,
    hour INT,
    waterlevel varchar(128),
    outflow varchar(128),
    rainfall varchar(128),
    primary key(year, month, day, hour)
)
