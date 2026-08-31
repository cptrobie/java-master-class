package com.cptrobie.car;

import static com.cptrobie.util.CsvLineCounter.countLines;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

public class CarCsvReader {

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
}
