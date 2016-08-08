SELECT tripid, tripdriver, car, fuelrate, startpoint, endpoint, distance, startdate, enddate, sumfuel FROM TRIPS
WHERE (startdate BETWEEN ? AND ?) AND (enddate BETWEEN ? AND ?)