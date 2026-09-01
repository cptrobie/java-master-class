package com.cptrobie.booking;

import com.cptrobie.car.Brand;
import com.cptrobie.car.Car;
import com.cptrobie.user.User;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingCsvReader {

  public static List<Booking> readBookingsCsvToList(String filePath) {
    List<Booking> bookings = new ArrayList<Booking>();

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      boolean isHeader = true;

      while ((line = br.readLine()) != null) {
        if (isHeader) {
          isHeader = false; // Skip the header row
          continue;
        }

        String[] data = line.split(",");

        // Map array positions to object properties
        Booking booking = new Booking();
        booking.setId(UUID.fromString(data[0].trim()));
            User user = new User(UUID.fromString(data[1].trim()), data[2].trim());
        booking.setUser(user);
            Car car = new Car();
            car.setId(UUID.fromString(data[3].trim()));
            car.setRegNumber(data[4].trim());
            car.setRentalPricePerDay(new BigDecimal(data[5].trim()));
            car.setBrand(Brand.valueOf(data[6].trim()));
            car.setIsElectric(Boolean.parseBoolean(data[7].trim()));
        booking.setCar(car);
        booking.setStartDate(LocalDate.parse(data[8].trim()));
        booking.setEndDate(LocalDate.parse(data[9].trim()));
        booking.setPrice(new BigDecimal(data[10].trim()));
        booking.setStatus(BookingStatus.valueOf(data[11].trim()));

        bookings.add(booking);
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return bookings;
  }
}
