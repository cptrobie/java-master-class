package com.cptrobie.booking;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BookingCsvWriter {

  public static void writeBookingsCsvFromList(List<Booking> bookings, String csvFilePath) {

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {

      // Write CSV Header
      writer.write(
          "Booking.UUID, User.UUID, User.name, "
              + "Car.UUID, Car.regNumber, Car.rentalPricePerDay, Car.Brand, Car.isElectric, "
              + "Booking.startDate, Booking.endDate, Booking.price, Booking.Status");
      writer.newLine();

      for (Booking booking : bookings) {
        if (booking != null) {
          // writer.write(escapeSpecialCharacters(carBooking.serializeCarBooking()));
          writer.write(booking.serializeCarBooking());
          writer.newLine();
        }
      }
    } catch (IOException e) {
      System.err.println("An error occurred while writing the CSV file.");
      System.out.println(e.getMessage());
    }
  }
}
