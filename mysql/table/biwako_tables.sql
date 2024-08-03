CREATE TABLE SiteMaster
(
    siteID INT,
    name varchar(512),
    url varchar(1024),
    primary key(siteID)
);

CREATE TABLE Temperature
(
    year INT,
    month INT,
    day INT,
    hour INT,
    siteID INT,
    temperature varchar(128),
    ph varchar(128),
    do varchar(128),
    conductivity varchar(128),
    turb varchar(128),
    index div_index(siteID),
    foreign key fk_siteid(siteID) references SiteMaster(siteID),
    primary key(year, month, day, hour, siteID)
)

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

