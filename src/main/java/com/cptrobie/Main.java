package com.cptrobie;

import com.cptrobie.booking.Booking;
import com.cptrobie.booking.BookingFileDataAccessService;
import com.cptrobie.booking.BookingService;
import com.cptrobie.car.Car;
import com.cptrobie.car.CarFileDataAccessService;
import com.cptrobie.car.CarService;
import com.cptrobie.user.UserFileDataAccessService;
import com.cptrobie.user.UserService;
import java.util.Scanner;
import java.util.UUID;

public class Main {

  static void main(String[] args) {

    var userService =
        new UserService(new UserFileDataAccessService("src/main/resources/data/users.csv"));
    // new UserService(new UserArrayDataAccessService());

    var carService =
        new CarService(new CarFileDataAccessService("src/main/resources/data/cars.csv"));
    // new CarService( new CarArrayDataAccessService() );

    var bookingService =
        new BookingService(
            new BookingFileDataAccessService("src/main/resources/data/bookings.csv"),
            // new CarBookingArrayDataAccessService(),
            carService,
            userService);

    var scanner = new Scanner(System.in);

    var continueApp = true;

    while (continueApp) {
      try {
        displayBookingSystemMenuOptions();
        var input = scanner.nextLine().trim();
        switch (Integer.parseInt(input)) {
          case 1 -> handleSaveBooking(bookingService, carService, userService, scanner);
          case 2 -> handleDeleteBooking(bookingService, scanner);
          case 3 -> handleUserBookedCars(bookingService, userService, scanner);
          case 4 -> handleViewBookings(bookingService);
          case 5 -> handleListAvailableCars(bookingService, carService, false);
          case 6 -> handleListAvailableCars(bookingService, carService, true);
          case 7 -> handleListUsers(userService);
          case 8 -> continueApp = false;
          default -> System.out.println("Invalid input, please try again");
        }
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
    scanner.close();
  }

  private static void displayBookingSystemMenuOptions() {
    System.out.println("The Car Booking System menu options are:");
    System.out.println(
        " 1 - Book Car\n"
            + " 2 - Delete Booking\n"
            + " 3 - View All User Booked Cars\n"
            + " 4 - View All Bookings\n"
            + " 5 - View Available Cars\n"
            + " 6 - View Available Electric Cars\n"
            + " 7 - View All Users\n"
            + " 8 - Exit");
    System.out.println("Please choose one of the above options: ");
  }

  private static void handleSaveBooking(
      BookingService bookingService,
      CarService carService,
      UserService userService,
      Scanner scanner) {
    // From Main Menu Option 1

    if (handleListAvailableCars(bookingService, carService, false) == 0) {
      return;
    }

    System.out.println("Enter the Car's Id.");
    String carId = scanner.nextLine().trim();

    if (carId.isBlank()) {
      System.out.println("Invalid car Id. \n");
      return;
    }

    handleListUsers(userService);

    System.out.println("Enter the User's Id.");
    var userId = scanner.nextLine().trim();

    if (userId.isBlank()) {
      System.out.println("Invalid User Id. \n");
      return;
    }

    System.out.println("\nHow many days will be needed for this booking?");
    var bookingDays = scanner.nextLine().trim();

    try {
      int bookingLength = Integer.parseInt(bookingDays);

      if (bookingLength <= 0) {
        System.out.println("Invalid booking length. \n");
        return;
      }

      UUID newBookingId = bookingService.bookCar(userId, carId, bookingLength);
      Booking newBooking = bookingService.getBookingById(newBookingId)
              .orElseThrow(() -> new IllegalStateException("Booking not found"));

      System.out.println("This is to confirm the following booking details:");
      System.out.println(newBooking);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private static void handleDeleteBooking(
      BookingService bookingService, Scanner scanner) {
    // From Main Menu Option 2
    try {
      if (bookingService.getAllBookings().length == 0) {
        System.out.println("There are no bookings to delete.\n");
        return;
      }
      handleViewBookings(bookingService);

      System.out.println("Enter the Booking Id to delete");
      var bookingId = scanner.nextLine().trim();

      if (bookingId.isBlank()) {
        System.out.println("Invalid Booking Id.\n");
        return;
      }

      bookingService.deleteCarBooking(bookingId);
      System.out.println("Booking " + bookingId + " has been deleted");
      System.out.println();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private static void handleUserBookedCars(
      BookingService bookingService, UserService userService, Scanner scanner) {
    // From Main Menu option 3
    handleListUsers(userService);

    System.out.println("Enter the User's Id.");
    var userId = scanner.nextLine().trim();

    if (userId.isBlank()) {
      System.out.println("Invalid User Id. \n");
      return;
    }
    var user = userService.getUserById(UUID.fromString(userId))
            .orElseThrow(() -> new IllegalStateException("User not found"));

    var userBookings = bookingService.getBookingsByUser(userId);

    if (userBookings.length == 0) {
      System.out.println("\nUser " + userId + " has no bookings. \n");
      return;
    }
    System.out.println("\nUser: " + user.getName() + " has booked the following cars:");
    for (var booking : userBookings) {
      System.out.println("   " + booking);
    }
    System.out.println();
  }

  private static void handleViewBookings(BookingService bookingService) {
    // From Main Menu option 4

    Booking[] bookings = bookingService.getAllBookings();

    if (bookings.length == 0) {
      System.out.println("There are no bookings to display \n");
      return;
    }
    for (Booking booking : bookings) {
      System.out.println(booking);
    }
    System.out.println();
  }

  private static int handleListAvailableCars(
      BookingService bookingService, CarService carService, boolean isElectric) {
    // List all available cars
    Car[] availableCars =
        isElectric
            ? carService.getAvailableElectricCars(bookingService.getAllBookings())
            : carService.getAvailableCars(bookingService.getAllBookings());

    if (availableCars.length > 0) {
      for (Car car : availableCars) {
        System.out.println(car.toString());
      }
    } else {
      if (isElectric) {
        System.out.println("No electric cars are available at this time");
      } else {
        System.out.println("No cars are available at this time");
      }
    }
    System.out.println();
    return availableCars.length;
  }

  private static void handleListUsers(UserService userService) {
    // From Main Menu option 7

    var users = userService.getAllUsers();
    for (var user : users) {
      System.out.println(user.toString());
    }
    System.out.println();
  }
}
