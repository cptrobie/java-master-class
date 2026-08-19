package com.cptrobie.booking;

import com.cptrobie.car.Car;
import com.cptrobie.user.User;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class CarBooking {
  private final UUID id;
  private final User user;
  private final Car car;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final BigDecimal price;
  private final BookingStatus status;

  public CarBooking(
      User user,
      Car car,
      LocalDate startDate,
      LocalDate endDate,
      BigDecimal price,
      BookingStatus status) {
    this.user = user;
    this.car = car;
    this.startDate = startDate;
    this.endDate = endDate;
    this.price = price;
    this.status = status;
    this.id = UUID.randomUUID();
  }

  public UUID getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public Car getCar() {
    return car;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BookingStatus getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "CarBooking{"
        + " id="
        + id
        + ", user="
        + user
        + ", car="
        + car
        + ", startDate="
        + startDate
        + ", endDate="
        + endDate
        + ", price="
        + price
        + ", status="
        + status
        + " }";
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    CarBooking that = (CarBooking) o;
    return Objects.equals(id, that.id)
        && Objects.equals(user, that.user)
        && Objects.equals(car, that.car)
        && Objects.equals(startDate, that.startDate)
        && Objects.equals(endDate, that.endDate)
        && Objects.equals(price, that.price)
        && status == that.status;
  }
}
