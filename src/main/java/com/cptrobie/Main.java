package com.cptrobie;

import com.cptrobie.booking.CarBooking;
import com.cptrobie.booking.CarBookingFileDataAccessService;
import com.cptrobie.booking.CarBookingService;
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

    var carBookingService =
        new CarBookingService(
            new CarBookingFileDataAccessService("src/main/resources/data/bookings.csv"),
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
          case 1 -> handleSaveBooking(carBookingService, carService, userService, scanner);
          case 2 -> handleDeleteBooking(carBookingService, userService, scanner);
          case 3 -> handleUserBookedCars(carBookingService, userService, scanner);
          case 4 -> handleViewBookings(carBookingService);
          case 5 -> handleListAvailableCars(carBookingService, carService, false);
          case 6 -> handleListAvailableCars(carBookingService, carService, true);
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
      CarBookingService carBookingService,
      CarService carService,
      UserService userService,
      Scanner scanner) {
    // From Main Menu Option 1

    if (handleListAvailableCars(carBookingService, carService, false) == 0) {
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

      CarBooking newCarBooking =
          carBookingService
              .getBookingById(carBookingService.bookCar(userId, carId, bookingLength))
              .get();
      System.out.println("This is to confirm the following booking details:");
      System.out.println(newCarBooking);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private static void handleDeleteBooking(
      CarBookingService carBookingService, UserService userService, Scanner scanner) {
    // From Main Menu Option 2
    try {
      if (carBookingService.getAllBookings().length == 0) {
        System.out.println("There are no bookings to delete.\n");
        return;
      }
      handleViewBookings(carBookingService);

      System.out.println("Enter the Booking Id to delete");
      var bookingId = scanner.nextLine().trim();

      if (bookingId.isBlank()) {
        System.out.println("Invalid Booking Id.\n");
        return;
      }

      carBookingService.deleteCarBooking(bookingId);
      System.out.println("Booking " + bookingId + " has been deleted");
      System.out.println();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private static void handleUserBookedCars(
      CarBookingService carBookingService, UserService userService, Scanner scanner) {
    // From Main Menu option 3
    handleListUsers(userService);

    System.out.println("Enter the User's Id.");
    var userId = scanner.nextLine().trim();

    if (userId.isBlank()) {
      System.out.println("Invalid User Id. \n");
      return;
    }
    var user = userService.getUserById(UUID.fromString(userId)).get();

    var userBookings = carBookingService.getBookingsByUser(userId);

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

  private static void handleViewBookings(CarBookingService carBookingService) {
    // From Main Menu option 4

    CarBooking[] bookings = carBookingService.getAllBookings();

    if (bookings.length == 0) {
      System.out.println("There are no bookings to display \n");
      return;
    }
    for (CarBooking booking : bookings) {
      System.out.println(booking);
    }
    System.out.println();
  }

  private static int handleListAvailableCars(
      CarBookingService carBookingService, CarService carService, boolean isElectric) {
    // List all available cars
    Car[] availableCars =
        isElectric
            ? carService.getAvailableElectricCars(carBookingService.getAllBookings())
            : carService.getAvailableCars(carBookingService.getAllBookings());

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
