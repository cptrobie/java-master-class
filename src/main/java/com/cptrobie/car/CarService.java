package com.cptrobie.car;

import com.cptrobie.booking.Booking;
import java.util.Optional;
import java.util.UUID;

public class CarService {

  private final CarDao carDao;

  public CarService(CarDao carDao) {
    this.carDao = carDao;
  }

  public Car[] getAvailableCars(Booking[] allBookings) {

    Car[] allCars = carDao.getAllCars();
    if (allCars.length == 0) {
      return new Car[0];
    }

    Car[] bookedCars = new Car[allBookings.length];
    if (bookedCars.length == 0) {
      return allCars;
    }

    var bookingIndex = 0;
    for (Booking booking : allBookings) {
      if (booking != null) {
        bookedCars[bookingIndex++] = booking.getCar();
      }
    }

    // Create a tracking array to avoid deleting the same index twice
    boolean[] matchedCars = new boolean[allCars.length];
    var matchedCarCount = 0;

    for (Car bookedCar : bookedCars) {
      for (int j = 0; j < allCars.length; j++) {
        if (!matchedCars[j] && allCars[j].getId().equals(bookedCar.getId())) {
          matchedCars[j] = true;
          matchedCarCount++;
          break;
        }
      }
    }

    Car[] availableCars = new Car[allCars.length - matchedCarCount];
    var availableIndex = 0;

    for (int i = 0; i < allCars.length; i++) {
      if (!matchedCars[i]) {
        availableCars[availableIndex++] = allCars[i];
      }
    }

    return availableCars;
  }

  public Car[] getAvailableElectricCars(Booking[] electricBookings) {

    Car[] allElectricCars = carDao.getAllElectricCars();
    if (allElectricCars.length == 0) {
      return new Car[0];
    }

    Car[] bookedCars = new Car[electricBookings.length];
    if (bookedCars.length == 0) {
      return allElectricCars;
    }

    var bookingIndex = 0;
    for (Booking booking : electricBookings) {
      if (booking != null) {
        bookedCars[bookingIndex++] = booking.getCar();
      }
    }

    // Create a tracking array to avoid deleting the same index twice
    boolean[] matchedCars = new boolean[allElectricCars.length];
    var matchedCarCount = 0;

    for (Car bookedCar : bookedCars) {
      for (int j = 0; j < allElectricCars.length; j++) {
        if (!matchedCars[j] && allElectricCars[j].getId().equals(bookedCar.getId())) {
          matchedCars[j] = true;
          matchedCarCount++;
          break;
        }
      }
    }

    Car[] availableElectricCars = new Car[allElectricCars.length - matchedCarCount];
    var availableIndex = 0;

    for (int i = 0; i < allElectricCars.length; i++) {
      if (!matchedCars[i]) {
        availableElectricCars[availableIndex++] = allElectricCars[i];
      }
    }

    return availableElectricCars;
  }

  public Optional<Car[]> getCarsByBrand(Brand brand) {
    return carDao.findCarsByBrand(brand);
  }

  public Optional<Car> getCarById(UUID carId) {
    return carDao.findCarById(carId);
  }
}
