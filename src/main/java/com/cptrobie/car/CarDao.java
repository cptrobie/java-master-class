package com.cptrobie.car;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarDao {

  List<Car> getAllCars();

  List<Car> getAllElectricCars();

  Optional<Car> findCarById(UUID carId);

  Optional<List<Car>> findCarsByBrand(Brand brand);
}
