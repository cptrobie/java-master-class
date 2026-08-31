package com.cptrobie.car;

import java.util.*;

public class CarFileDataAccessService implements CarDao {
  private final String filePath;
  private static List<Car> CARS;

  public CarFileDataAccessService(String filePath) {
    this.filePath = filePath;
    CARS = CarCsvReader.readCarsCsvToList(filePath);
  }

  @Override
  public List<Car> getAllCars() {
    return CARS;
  }

  @Override
  public List<Car> getAllElectricCars() {
    List<Car> electricCars = new ArrayList<>();

    for (Car car : CARS) {
      if (car.isElectric()) {
        electricCars.add(car);
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
  public Optional<List<Car>> findCarsByBrand(Brand brand) {
    if (brand == null) {
      return Optional.empty();
    }

    List<Car> carsByBrand = new ArrayList<>();

    for (Car car : CARS) {
      if (car.getBrand().equals(brand)) {
        carsByBrand.add(car);
      }
    }
    return Optional.of(carsByBrand);
  }
}
