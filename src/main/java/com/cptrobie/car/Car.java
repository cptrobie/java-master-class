package com.cptrobie.car;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Car implements Serializable {

  private final UUID id;
  private final String regNumber;
  private final BigDecimal rentalPricePerDay;
  private final Brand brand;
  private final boolean isElectric;


  public Car(String regNumber, BigDecimal rentalPricePerDay, Brand brand, boolean isElectric) {
    this(UUID.randomUUID(), regNumber, rentalPricePerDay, brand, isElectric);
  }

  public Car(UUID id, String regNumber, BigDecimal rentalPricePerDay, Brand brand, boolean isElectric) {
    this.id = id;
    this.regNumber = regNumber;
    this.rentalPricePerDay = rentalPricePerDay;
    this.brand = brand;
    this.isElectric = isElectric;
  }

  public UUID getId() {
    return id;
  }

  public String getRegNumber() {
    return regNumber;
  }

  public BigDecimal getRentalPricePerDay() {
    return rentalPricePerDay;
  }

  public Brand getBrand() {
    return brand;
  }

  public boolean isElectric() {
    return isElectric;
  }

  public String serializeCar() {
    return id.toString()
        + ", "
        + regNumber
        + ", "
        + rentalPricePerDay.toString()
        + ", "
        + brand.toString()
        + ", "
        + isElectric;
  }

  @Override
  public String toString() {
    return "Car {"
        + "id="
        + id
        + ", regNumber='"
        + regNumber
        + '\''
        + ", rentalPricePerDay="
        + rentalPricePerDay
        + ", brand="
        + brand
        + ", isElectric="
        + isElectric
        + " }";
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Car car = (Car) o;
    return isElectric == car.isElectric
        && Objects.equals(id, car.id)
        && Objects.equals(regNumber, car.regNumber)
        && Objects.equals(rentalPricePerDay, car.rentalPricePerDay)
        && brand == car.brand;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
