package com.cptrobie.car;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class CarArrayDataAccessService implements CarDao {
  private static final Car[] CARS;

  static {
    CARS =
        new Car[] {
          new Car(
              UUID.fromString("4a8ed515-6a39-47b8-bb9f-4ae9e0424f8d"),
              "Audi-1",
              BigDecimal.valueOf(90.00),
              Brand.AUDI,
              false),
          new Car(
              UUID.fromString("6f14d68f-17f5-444b-8ea0-3adf7f7382f7"),
              "Audi-2",
              BigDecimal.valueOf(80.00),
              Brand.AUDI,
              false),
          new Car(
              UUID.fromString("b9943d5c-2788-471e-a09f-47f3195c7008"),
              "Merc-1",
              BigDecimal.valueOf(100.00),
              Brand.MERCEDES,
              false),
          new Car(
              UUID.fromString("a8465e98-74b4-45a1-94ac-8c27ea108649"),
              "Merc-2",
              BigDecimal.valueOf(70.00),
              Brand.MERCEDES,
              true),
          new Car(
              UUID.fromString("3634853d-2f47-445f-be09-17c94cfa42f8"),
              "Tesla-1",
              BigDecimal.valueOf(50.00),
              Brand.TESLA,
              true),
          new Car(
              UUID.fromString("dd3f1192-b0ac-4214-be58-229a102ed949"),
              "Tesla-2",
              BigDecimal.valueOf(65.00),
              Brand.TESLA,
              true),
          new Car(
              UUID.fromString("b24afa33-941c-4cd6-81e1-a6a3eb30754f"),
              "Toy-1",
              BigDecimal.valueOf(35.00),
              Brand.TOYOTA,
              false),
          new Car(
              UUID.fromString("553a63f4-3725-4d3a-88e0-2837afaab5f0"),
              "Toy-2",
              BigDecimal.valueOf(55.00),
              Brand.TOYOTA,
              false)
        };
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
