CREATE TABLE water_temp
(
    year INT,
    month INT,
    day INT,
    hour INT,
    siteID INT,
    temperature INT,
    PRIMARY KEY(year, month, day, hour, siteID)
);

CREATE TABLE waterlevel_flow
(
    year INT,
    month INT,
    day INT,
    hour INT,
    level INT,
    flow INT,
    PRIMARY KEY(year, month, day, hour)
);
