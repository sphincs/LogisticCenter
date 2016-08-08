CREATE TABLE DRIVERS (
  driverid BIGINT IDENTITY ,
  drivername VARCHAR(255) NOT NULL ,
  age INTEGER NOT NULL
);

CREATE TABLE TRIPS (
  tripid BIGINT IDENTITY ,
  tripdriver VARCHAR(255) NOT NULL ,
  car VARCHAR(255) NOT NULL ,
  fuelrate DOUBLE NOT NULL ,
  startpoint VARCHAR(255) NOT NULL ,
  endpoint VARCHAR(255) NOT NULL ,
  distance VARCHAR(255) NOT NULL ,
  startdate DATE NOT NULL ,
  enddate DATE NOT NULL ,
  sumfuel VARCHAR(255) NOT NULL
)