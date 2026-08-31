package com.cptrobie.booking;

import static com.cptrobie.util.CsvLineCounter.countLines;

import com.cptrobie.car.Brand;
import com.cptrobie.car.Car;
import com.cptrobie.user.User;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class BookingCsvReader {

  public static Booking[] readBookingsCsvToArray(String filePath) {
    int lineCount = countLines(filePath);
    Booking[] array = new Booking[lineCount];

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      int index = 0;

      // CSV has a header row
      br.readLine();

      while ((line = br.readLine()) != null && index < array.length) {
        String[] parts = line.split(",");
        UUID bookingId = UUID.fromString(parts[0].trim());
        User user = new User(UUID.fromString(parts[1].trim()), parts[2].trim());
        Car car =
            new Car(
                UUID.fromString(parts[3].trim()),
                parts[4].trim(),
                new BigDecimal(parts[5].trim()),
                Brand.valueOf(parts[6].trim()),
                Boolean.parseBoolean(parts[7].trim()));
        LocalDate startDate = LocalDate.parse(parts[8].trim());
        LocalDate endDate = LocalDate.parse(parts[9].trim());
        BigDecimal price = new BigDecimal(parts[10].trim());
        BookingStatus status = BookingStatus.valueOf(parts[11].trim());

        array[index] = new Booking(bookingId, user, car, startDate, endDate, price, status);
        index++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return array;
  }
}
