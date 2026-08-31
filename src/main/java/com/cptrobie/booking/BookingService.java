package com.cptrobie.booking;

import com.cptrobie.car.Car;
import com.cptrobie.car.CarService;
import com.cptrobie.user.User;
import com.cptrobie.user.UserService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class BookingService {

  private final BookingDao bookingDao;
  private final CarService carService;
  private final UserService userService;

  public BookingService(
      BookingDao bookingDao, CarService carService, UserService userService) {
    this.bookingDao = bookingDao;
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
    bookingDao.saveBooking(carBooking);
    return carBooking.getId();
  }

  public void deleteCarBooking(String bookingId) {

    Booking[] allBookings = bookingDao.getBookings();

    if (allBookings.length == 0) {
      throw new BookingNotFoundException("Booking not found");
    }

    // Determine if there is a booking match
    var bookingIdFound = false;
    for (Booking booking : allBookings) {
      if (booking != null && booking.getId().equals(UUID.fromString(bookingId))) {
        bookingIdFound = true;
      }
    }

    if (!bookingIdFound) {
      throw new BookingNotFoundException("Booking not found");
    }

    for (Booking booking : allBookings) {
      if (booking != null && booking.getId().equals(UUID.fromString(bookingId))) {
        bookingDao.deleteBooking(booking);
      }
    }
  }

  public Optional<Booking> getBookingById(UUID bookingId) {
    return bookingDao.findBookingById(bookingId);
  }

  public Booking[] getBookingsByUser(String userId) {

    Booking[] bookings = bookingDao.getBookings();

    var userBookingCount = 0;

    for (Booking booking : bookings) {
      if (booking != null && booking.getUser().getId().equals(UUID.fromString(userId))) {
        ++userBookingCount;
      }
    }

    if (userBookingCount == 0) {
      return new Booking[0];
    }

    var i = 0;
    Booking[] userBookings = new Booking[userBookingCount];

    for (Booking booking : bookings) {
      if (booking != null && booking.getUser().getId().equals(UUID.fromString(userId))) {
        userBookings[i++] = booking;
      }
    }
    return userBookings;
  }

  public Booking[] getAllBookings() {

    Booking[] bookings = bookingDao.getBookings();
    // The DAO initially set the array size but is it empty or not?

    var bookingCount = 0;

    for (Booking booking : bookings) {
      if (booking != null) {
        bookingCount++;
      }
    }

    if (bookingCount == 0) {
      return new Booking[0];
    }

    // Reset the booking array size & content
    var i = 0;
    Booking[] newBookings = new Booking[bookingCount];

    for (Booking booking : bookings) {
      if (booking != null) {
        newBookings[i++] = booking;
      }
    }
    return newBookings;
  }

  private Booking[] getAllElectricBookings() {
    Booking[] allBookings = getAllBookings();
    var electricCarBookingCount = 0;

    for (Booking booking : allBookings) {
      if (booking.getCar().isElectric()) {
        electricCarBookingCount++;
      }
    }

    if (electricCarBookingCount == 0) {
      return new Booking[0];
    }

    Booking[] allElectricBookings = new Booking[electricCarBookingCount];
    var i = 0;

    for (Booking booking : allBookings) {
      if (booking.getCar().isElectric()) {
        allElectricBookings[i++] = booking;
      }
    }
    return allElectricBookings;
  }

  private Booking createCarBooking(String userId, String carId, Integer bookingLength) {

    User user = userService.getUserById(UUID.fromString(userId))
            .orElseThrow(() -> new IllegalStateException("User not found"));
    Car car = carService.getCarById(UUID.fromString(carId))
            .orElseThrow(() -> new IllegalStateException("Car not found"));

    return new Booking(
        user,
        car,
        LocalDate.now(),
        LocalDate.now().plusDays(bookingLength),
        car.getRentalPricePerDay().multiply(BigDecimal.valueOf(bookingLength)),
        BookingStatus.ACTIVE);
  }
}
