package com.cptrobie.car;

import java.util.*;

public class CarFileDataAccessService extends CarListDataAccessService implements CarDao {
  private final String filePath;


  public CarFileDataAccessService(String filePath) {
    this.filePath = filePath;
    CARS = CarCsvReader.readCarsCsvToList(filePath);
  }

  @Override
  public List<Car> getAllCars() {
    return super.getAllCars();
  }

  @Override
  public List<Car> getAllElectricCars() {
    return super.getAllElectricCars();
  }

  @Override
  public Optional<Car> findCarById(UUID carId) {
    return super.findCarById(carId);
  }

  @Override
  public Optional<List<Car>> findCarsByBrand(Brand brand) {
    return super.findCarsByBrand(brand);
  }
}
