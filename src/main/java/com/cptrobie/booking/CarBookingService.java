package com.cptrobie.booking;

import com.cptrobie.car.Car;
import com.cptrobie.car.CarService;
import com.cptrobie.user.User;

import javax.management.InstanceNotFoundException;
import java.math.BigDecimal;
import java.rmi.UnexpectedException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.UUID;

public class CarBookingService {

  private final CarBookingDao carBookingDao = new CarBookingDao();
  private final CarService carService = new CarService();


  public UUID bookCar(User user, String carId, Integer bookingLength) throws UnexpectedException, InstanceNotFoundException {
    // Check to see if any cars are available to book
    Car[] availableCars = getAllAvailableCars();
    if (availableCars.length == 0) {
      throw new UnexpectedException("All cars are booked");
    }

    // Now check to see if car already booked
    var isAvailable = false;
    for (Car car : availableCars) {
      if(car.getId().equals(UUID.fromString(carId))) {
        isAvailable = true;
      }
    }
    if (!isAvailable) {
      throw new UnexpectedException("Cars with Id " + carId + " is already booked");
    }

    // Car can now be booked
    Car car = carService.getCarById(carId);
    var carBooking = createCarBooking(user,car, bookingLength);
    carBookingDao.bookCar(carBooking);
    return carBooking.getId();
  }

  public void deleteCarBooking(String bookingId) throws ClassNotFoundException {

    CarBooking[] allBookings = carBookingDao.getCarBookings();

    if (allBookings.length == 0) {
      throw new NoSuchElementException("There are no booking to delete");
    }

    // Determine if there is a booking match
    var bookingIdFound = false;
    for (CarBooking carBooking: allBookings) {
      if (carBooking != null && carBooking.getId().equals(UUID.fromString(bookingId))) {
        bookingIdFound = true;
      }
    }

    if (!bookingIdFound) {
      throw new ClassNotFoundException("BookingId not found");
    }

    for (CarBooking carBooking : allBookings) {
      if (carBooking != null && carBooking.getId().equals(UUID.fromString(bookingId))) {
        carBookingDao.deleteCarBooking(carBooking);
      }
    }
  }

  public CarBooking[] getBookingsByUser(String userId) {

    CarBooking[] carBookings = carBookingDao.getCarBookings();

    var userBookingCount = 0;

    for (CarBooking carBooking : carBookings) {
      if (carBooking != null && carBooking.getUser().getId().equals(UUID.fromString(userId))) {
        ++userBookingCount;
      }
    }

    if (userBookingCount == 0) {
      return new CarBooking[0];
    }

    var i = 0;
    CarBooking[] userBookings = new CarBooking[userBookingCount];

    for (CarBooking carBooking : carBookings) {
      if (carBooking != null && carBooking.getUser().getId().equals(UUID.fromString(userId))) {
        userBookings[i++] = carBooking;
      }
    }
    return userBookings;
  }

  public CarBooking[] getAllBookings() {

    CarBooking[] carBookings = carBookingDao.getCarBookings();
    // The DAO initially set the array size but is it empty or not?

    var bookingCount = 0;

    for (CarBooking carBooking : carBookings) {
      if (carBooking != null) {
        bookingCount++;
      }
    }

    if (bookingCount == 0) {
      return new CarBooking[0];
    }

    // Reset the booking array size & content
    var i = 0;
    CarBooking[] newBookings = new CarBooking[bookingCount];

    for (CarBooking carBooking : carBookings) {
      if (carBooking != null) {
        newBookings[i++] = carBooking;
      }
    }
    return newBookings;
  }

  public Car[] getAllAvailableCars() {

     return carService.getAvailableCars(getAllBookings());
  }

  public Car[] getAllAvailableElectricCars() {

    return carService.getAvailableElectricCars(getAllElectricBookings());
  }

  private CarBooking[] getAllElectricBookings() {
    CarBooking[] allBookings = getAllBookings();
    var electricCarBookingCount = 0;

    for (CarBooking carBooking : allBookings) {
      if (carBooking.getCar().isElectric()) {
        electricCarBookingCount++;
      }
    }

    if (electricCarBookingCount == 0) {
      return new CarBooking[0];
    }

    CarBooking[] allElectricBookings = new CarBooking[electricCarBookingCount];
    var i = 0;

    for (CarBooking carBooking : allBookings) {
      if (carBooking.getCar().isElectric()) {
        allElectricBookings[i++] = carBooking;
      }
    }
    return allElectricBookings;
  }

  private CarBooking createCarBooking(User user, Car car, Integer bookingLength) {

    return new CarBooking(
            user,
            car,
            LocalDate.now(),
            LocalDate.now().plusDays(bookingLength),
            car.getRentalPricePerDay().multiply(BigDecimal.valueOf(bookingLength)),
            BookingStatus.ACTIVE
    );
  }
}

