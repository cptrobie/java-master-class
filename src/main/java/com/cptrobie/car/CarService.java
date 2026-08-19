package com.cptrobie.car;

import com.cptrobie.booking.CarBooking;

import javax.management.InstanceNotFoundException;
import java.util.UUID;

public class CarService {

  private final CarDao carDao = new CarDao();

  public Car[] getAvailableCars(CarBooking[] allBookings) {

    Car[] allCars = carDao.getAllCars();
    if (allCars.length == 0) {
      return new Car[0];
    }

    Car[] bookedCars = new Car[allBookings.length];
    if (bookedCars.length == 0) {
      return allCars;
    }

    if (allCars.length == allBookings.length) {
      return new Car[0];
    }

    var bookingIndex = 0;
    for (CarBooking carBooking : allBookings) {
      if (carBooking != null) {
        bookedCars[bookingIndex++] = carBooking.getCar();
      }
    }

    // Create a tracking array to avoid deleting the same index twice
    boolean[] matchedCars = new boolean[allCars.length];
    var matchedCarCount = 0;

    for (Car bookedCar : bookedCars) {
      for (int j = 0; j < allCars.length; j++) {
        if (!matchedCars[j] && allCars[j] == bookedCar) {
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

  public Car[] getAvailableElectricCars(CarBooking[] electricCarBookings) {

    Car[] allElectricCars = carDao.getAllElectricCars();
    if (allElectricCars.length == 0) {
      return new Car[0];
    }

    Car[] bookedCars = new Car[electricCarBookings.length];
    if (bookedCars.length == 0) {
      return allElectricCars;
    }

    if (allElectricCars.length == electricCarBookings.length) {
      return new Car[0];
    }

    var bookingIndex = 0;
    for (CarBooking carBooking : electricCarBookings) {
      if (carBooking != null) {
        bookedCars[bookingIndex++] = carBooking.getCar();
      }
    }

// Create a tracking array to avoid deleting the same index twice
    boolean[] matchedCars = new boolean[allElectricCars.length];
    var matchedCarCount = 0;

    for (Car bookedCar : bookedCars) {
      for (int j = 0; j < allElectricCars.length; j++) {
        if (!matchedCars[j] && allElectricCars[j] == bookedCar) {
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

  public Car[] getCarsByBrand(Brand brand) throws InstanceNotFoundException {
    Car[] allCars = carDao.getAllCars();
    var brandCount = 0;

    for (Car car : allCars) {
      if (car.getBrand() == brand) {
        brandCount++;
      }
    }

    if (brandCount == 0) {
      throw new InstanceNotFoundException("No " + brand  + " brand of cars were found.");
    }

    Car[] carsByBrand = new Car[brandCount];
    var i = 0;

    for (Car car : allCars) {
      if (car.getBrand() == brand) {
        carsByBrand[i++] = car;
      }
    }

    return carsByBrand;
  }

  public Car getCarById(String carId) throws InstanceNotFoundException {
    Car[] allCars = carDao.getAllCars();

    for (Car car : allCars) {
      if (car.id().equals(UUID.fromString(carId))) {
        return car;
      }
    }
    // If we get here then the car was not found
    throw new InstanceNotFoundException("Car with Id of " + carId + " was not found");
  }
}
