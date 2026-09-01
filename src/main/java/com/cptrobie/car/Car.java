package com.cptrobie.car;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Car implements Serializable {

  private UUID id;
  private String regNumber;
  private BigDecimal rentalPricePerDay;
  private Brand brand;
  private boolean isElectric;


  public Car() { }

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

  public void setId(UUID id) {
    this.id = id;
  }

  public String getRegNumber() {
    return regNumber;
  }

  public void setRegNumber(String regNumber) {
    this.regNumber = regNumber;
  }

  public BigDecimal getRentalPricePerDay() {
    return rentalPricePerDay;
  }

  public void setRentalPricePerDay(BigDecimal rentalPricePerDay) {
    this.rentalPricePerDay = rentalPricePerDay;
  }

  public Brand getBrand() {
    return brand;
  }

  public void setBrand(Brand brand) {
    this.brand = brand;
  }

  public boolean isElectric() {
    return isElectric;
  }

  public void setIsElectric(boolean isElectric) {
    this.isElectric = isElectric;
  }

  public String serializeCar() {
    return id.toString() + ", " + regNumber + ", "
        + rentalPricePerDay.toString() + ", "
        + brand.toString() + ", " + isElectric;
  }

  @Override
  public String toString() {
    return "Car {"
        + "id=" + id + ", regNumber='" + regNumber + '\''
        + ", rentalPricePerDay=" + rentalPricePerDay
        + ", brand=" + brand + ", isElectric=" + isElectric + " }";
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
