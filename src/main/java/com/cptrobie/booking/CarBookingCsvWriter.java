package com.cptrobie.booking;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CarBookingCsvWriter {

  public static void writeBookingsCsvFromArray(CarBooking[] bookings, String csvFilePath) {

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {

      // Write CSV Header
      writer.write(
          "Booking.UUID, User.UUID, User.name, "
              + "Car.UUID, Car.regNumber, Car.rentalPricePerDay, Car.Brand, Car.isElectric, "
              + "Booking.startDate, Booking.endDate, Booking.price, Booking.Status");
      writer.newLine();

      for (CarBooking carBooking : bookings) {
        if (carBooking != null) {
          // writer.write(escapeSpecialCharacters(carBooking.serializeCarBooking()));
          writer.write(carBooking.serializeCarBooking());
          writer.newLine();
        }
      }
    } catch (IOException e) {
      System.err.println("An error occurred while writing the CSV file.");
      e.printStackTrace();
    }
  }
}
