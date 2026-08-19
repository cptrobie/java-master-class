package com.cptrobie.car;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public record Car(
    UUID id, String regNumber, BigDecimal rentalPricePerDay, Brand brand, boolean isElectric) {

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

  public boolean getIsElectric() {
    return isElectric;
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
}
