package com.cptrobie.user;

import static com.cptrobie.util.CsvLineCounter.countLines;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

public class UserCsvReader {

  public static User[] readUsersCsvToArray(String filePath) {
    int lineCount = countLines(filePath);
    User[] array = new User[lineCount];

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      int index = 0;

      // CSV has a header row
      br.readLine();

      while ((line = br.readLine()) != null && index < array.length) {
        String[] parts = line.split(",");
        UUID userId = UUID.fromString(parts[0].trim());
        String name = parts[1].trim();

        array[index] = new User(userId, name);
        index++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return array;
  }
}
