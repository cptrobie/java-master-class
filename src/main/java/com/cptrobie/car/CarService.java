package com.cptrobie.car;

import com.cptrobie.booking.Booking;

import java.util.*;

public class CarService {
  private final CarDao carDao;

  public CarService(CarDao carDao) {
    this.carDao = carDao;
  }


  public List<Car> getAvailableCars(List<Booking> allBookings) {
    List<Car> allCars = carDao.getAllCars();

    if (allCars.isEmpty()) {
      return Collections.emptyList();
    }

    if(allBookings.isEmpty()) {
      return allCars;
    }

    // Now remove booked cars
    List<Car> availableCars = new ArrayList<Car>(allCars);
    for(Booking booking : allBookings) {
      if(booking != null) {
        availableCars.remove(booking.getCar());
        break;
      }
    }

    return availableCars;
  }

  public List<Car> getAvailableElectricCars(List<Booking> electricBookings) {
    List<Car> allElectricCars = carDao.getAllElectricCars();

    if (allElectricCars.isEmpty()) {
      return Collections.emptyList();
    }

    if (electricBookings.isEmpty()) {
      return allElectricCars;
    }

    for (Booking booking : electricBookings) {
      if (booking != null) {
        allElectricCars.remove(booking.getCar());
        break;
      }
    }
    return allElectricCars;
  }

  public Optional<List<Car>> getCarsByBrand(Brand brand) {
    return carDao.findCarsByBrand(brand);
  }

  public Optional<Car> getCarById(UUID carId) {
    return carDao.findCarById(carId);
  }
}
