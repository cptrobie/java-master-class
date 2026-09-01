package com.cptrobie.booking;

import com.cptrobie.car.Car;
import com.cptrobie.car.CarService;
import com.cptrobie.user.User;
import com.cptrobie.user.UserService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

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

  public UUID bookCar(String userId, String carId, Integer bookingDuration) {
    // Arg validation checks
    if (userId == null || carId == null || bookingDuration < 1) {
      throw new IllegalArgumentException("Invalid booking arguments");
    }

    // Check to see if any cars are available to book
    List<Car> availableCars = carService.getAvailableCars(getAllBookings());
    if (availableCars.isEmpty()) {
      throw new BookingStateException("All cars are booked");
    }

    // Now, check to see if car already booked
    var isAvailable = false;
    for (Car car : availableCars) {
      if (car.getId().equals(UUID.fromString(carId))) {
        isAvailable = true;
        break;
      }
    }
    if (!isAvailable) {
      throw new BookingAlreadyExistsException("Cars with Id " + carId + " is already booked");
    }

    // Car can now be booked
    var carBooking = createCarBooking(userId, carId, bookingDuration);
    bookingDao.saveBooking(carBooking);
    return carBooking.getId();
  }

  public void deleteCarBooking(String bookingId) {
    // Arg validation checks
    if (bookingId == null) {
      throw new IllegalArgumentException("Booking Id cannot be null");
    }
    var bookingToDelete = bookingDao.findBookingById(UUID.fromString(bookingId))
            .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

    bookingDao.deleteBooking(bookingToDelete);
  }

  public Optional<Booking> getBookingById(UUID bookingId) {
    return bookingDao.findBookingById(bookingId);
  }

  public List<Booking> getBookingsByUser(String userId) {

    if (userId == null) {
      throw new IllegalArgumentException("User ID cannot be null");
    }

    List<Booking> allBookings = bookingDao.getBookings();
    List<Booking> userBookings = new ArrayList<Booking>();

    for (Booking booking : allBookings) {
      if (booking != null && booking.getUser().getId().equals(UUID.fromString(userId))) {
        userBookings.add(booking);
      }
    }
    return userBookings;
  }

  public List<Booking> getAllBookings() {
    return bookingDao.getBookings();
  }

  private List<Booking> getAllElectricBookings() {
    List<Booking> allBookings = bookingDao.getBookings();
    List<Booking> allElectricBookings = new ArrayList<Booking>();

    for (Booking booking : allBookings) {
      if (booking.getCar().isElectric()) {
        allElectricBookings.add(booking);
      }
    }
    return allElectricBookings;
  }

  private Booking createCarBooking(String userId, String carId, Integer bookingDuration) {

    var user = userService.getUserById(UUID.fromString(userId))
            .orElseThrow(() -> new IllegalStateException("User not found"));
    var car = carService.getCarById(UUID.fromString(carId))
            .orElseThrow(() -> new IllegalStateException("Car not found"));

    return new Booking(
        user,
        car,
        LocalDate.now(),
        LocalDate.now().plusDays(bookingDuration),
        car.getRentalPricePerDay().multiply(BigDecimal.valueOf(bookingDuration)),
        BookingStatus.ACTIVE);
  }
}
