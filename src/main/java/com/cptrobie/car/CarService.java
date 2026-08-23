package com.cptrobie.car;

import com.cptrobie.booking.CarBooking;
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

  public Car[] getAvailableElectricCars(CarBooking[] electricCarBookings) {

    Car[] allElectricCars = carDao.getAllElectricCars();
    if (allElectricCars.length == 0) {
      return new Car[0];
    }

    Car[] bookedCars = new Car[electricCarBookings.length];
    if (bookedCars.length == 0) {
      return allElectricCars;
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

  public Car[] getCarsByBrand(Brand brand) {
    Car[] allCars = carDao.getAllCars();
    var brandCount = 0;

    for (Car car : allCars) {
      if (car.getBrand().equals(brand)) {
        brandCount++;
      }
    }

    if (brandCount == 0) {
      throw new CarNotFoundException("No " + brand + " brand of cars were found.");
    }

    Car[] carsByBrand = new Car[brandCount];
    var i = 0;

    for (Car car : allCars) {
      if (car.getBrand().equals(brand)) {
        carsByBrand[i++] = car;
      }
    }

    return carsByBrand;
  }

  public Car getCarById(String carId) {
    Car[] allCars = carDao.getAllCars();

    for (Car car : allCars) {
      if (car.getId().equals(UUID.fromString(carId))) {
        return car;
      }
    }
    // If we get here then the car was not found
    throw new CarNotFoundException("Car with Id of " + carId + " was not found");
  }
}
