package com.cptrobie.car;

import java.util.Optional;
import java.util.UUID;

public interface CarDao {

  Car[] getAllCars();

  Car[] getAllElectricCars();

  Optional<Car> findCarById(UUID carId);

  Optional<Car[]> findCarsByBrand(Brand brand);
}
