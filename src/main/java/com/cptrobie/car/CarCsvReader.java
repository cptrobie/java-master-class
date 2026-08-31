package com.cptrobie.car;

import com.cptrobie.user.User;

import static com.cptrobie.util.CsvLineCounter.countLines;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarCsvReader {

  @Deprecated(since = "Phase 3", forRemoval = false)
  public static Car[] readCarsCsvToArray(String filePath) {
    int lineCount = countLines(filePath);
    Car[] array = new Car[lineCount];

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      int index = 0;

      // CSV has a header row
      br.readLine();

      while ((line = br.readLine()) != null && index < array.length) {
        String[] parts = line.split(",");
        UUID userId = UUID.fromString(parts[0].trim());
        String regNum = parts[1].trim();
        BigDecimal rentalPricePerDay = new BigDecimal(parts[2].trim());
        Brand brand = Brand.valueOf(parts[3].trim());
        boolean isElectric = Boolean.parseBoolean(parts[4].trim());

        array[index] = new Car(userId, regNum, rentalPricePerDay, brand, isElectric);
        index++;
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    return array;
  }

  public static List<Car> readCarsCsvToList(String filePath) {
    List<Car> cars = new ArrayList<Car>();

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
        Car car = new Car();
        car.setId(UUID.fromString(data[0].trim()));
        car.setRegNumber(data[1].trim());
        car.setRentalPricePerDay(new BigDecimal(data[2].trim()));
        car.setBrand(Brand.valueOf(data[3].trim()));
        car.setIsElectric(Boolean.parseBoolean(data[4].trim()));

        cars.add(car);
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return cars;
  }
}
