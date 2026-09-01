package com.cptrobie.car;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarCsvReader {

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
