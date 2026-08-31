package com.cptrobie.car;

import java.util.Optional;
import java.util.UUID;

public class CarFileDataAccessService implements CarDao {
  private final String filePath;
  private static Car[] CARS;

  public CarFileDataAccessService(String filePath) {
    this.filePath = filePath;
    CARS = CarCsvReader.readCarsCsvToArray(filePath);
  }

  @Override
  public Car[] getAllCars() {
    return CARS;
  }

  @Override
  public Car[] getAllElectricCars() {

    var electricCarCount = 0;

    for (Car car : CARS) {
      if (car.isElectric()) {
        electricCarCount++;
      }
    }

    if (electricCarCount == 0) {
      return new Car[0];
    }

    Car[] electricCars = new Car[electricCarCount];
    var i = 0;

    for (Car car : CARS) {
      if (car.isElectric()) {
        electricCars[i++] = car;
      }
    }
    return electricCars;
  }

  @Override
  public Optional<Car> findCarById(UUID carId) {

    if (carId == null) {
      return Optional.empty();
    }

    for (Car car : CARS) {
      if (car != null && car.getId().equals(carId)) {
        return Optional.of(car);
      }
    }
    return Optional.empty();
  }

  @Override
  public Optional<Car[]> findCarsByBrand(Brand brand) {

    var brandCount = 0;
    for (Car car : CARS) {
      if (car.getBrand().equals(brand)) {
        brandCount++;
      }
    }

    if (brandCount == 0) {
      return Optional.empty();
    }

    Car[] carsByBrand = new Car[brandCount];
    var i = 0;

    for (Car car : CARS) {
      if (car.getBrand().equals(brand)) {
        carsByBrand[i++] = car;
      }
    }
    return Optional.of(carsByBrand);
  }
}
