package com.cptrobie.booking;

import com.cptrobie.car.Car;
import com.cptrobie.car.CarService;
import com.cptrobie.user.User;
import com.cptrobie.user.UserService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class CarBookingService {

  private final CarBookingDao carBookingDao;
  private final CarService carService;
  private final UserService userService;

  public CarBookingService(
      CarBookingDao carBookingDao, CarService carService, UserService userService) {
    this.carBookingDao = carBookingDao;
    this.carService = carService;
    this.userService = userService;
  }

  public UUID bookCar(String userId, String carId, Integer bookingLength) {
    // Check to see if any cars are available to book
    Car[] availableCars = carService.getAvailableCars(getAllBookings());
    if (availableCars.length == 0) {
      throw new BookingStateException("All cars are booked");
    }

    // Now check to see if car already booked
    var isAvailable = false;
    for (Car car : availableCars) {
      if (car.getId().equals(UUID.fromString(carId))) {
        isAvailable = true;
      }
    }
    if (!isAvailable) {
      throw new BookingAlreadyExistsException("Cars with Id " + carId + " is already booked");
    }

    // Car can now be booked
    var carBooking = createCarBooking(userId, carId, bookingLength);
    carBookingDao.saveBooking(carBooking);
    return carBooking.getId();
  }

  public void deleteCarBooking(String bookingId) {

    CarBooking[] allBookings = carBookingDao.getBookings();

    if (allBookings.length == 0) {
      throw new BookingNotFoundException("Booking not found");
    }

    // Determine if there is a booking match
    var bookingIdFound = false;
    for (CarBooking carBooking : allBookings) {
      if (carBooking != null && carBooking.getId().equals(UUID.fromString(bookingId))) {
        bookingIdFound = true;
      }
    }

    if (!bookingIdFound) {
      throw new BookingNotFoundException("Booking not found");
    }

    for (CarBooking carBooking : allBookings) {
      if (carBooking != null && carBooking.getId().equals(UUID.fromString(bookingId))) {
        carBookingDao.deleteBooking(carBooking);
      }
    }
  }

  public Optional<CarBooking> getBookingById(UUID bookingId) {
    return carBookingDao.findBookingById(bookingId);
  }

  public CarBooking[] getBookingsByUser(String userId) {

    CarBooking[] carBookings = carBookingDao.getBookings();

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

    CarBooking[] carBookings = carBookingDao.getBookings();
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

  private CarBooking createCarBooking(String userId, String carId, Integer bookingLength) {

    User user = userService.getUserById(UUID.fromString(userId)).get();
    Car car = carService.getCarById(UUID.fromString(carId)).get();

    return new CarBooking(
        user,
        car,
        LocalDate.now(),
        LocalDate.now().plusDays(bookingLength),
        car.getRentalPricePerDay().multiply(BigDecimal.valueOf(bookingLength)),
        BookingStatus.ACTIVE);
  }
}
