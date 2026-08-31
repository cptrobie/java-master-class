package com.cptrobie.user;

import static com.cptrobie.util.CsvLineCounter.countLines;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserCsvReader {

  @Deprecated(since = "Phase 3", forRemoval = false)
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
      System.out.println(e.getMessage());
    }
    return array;
  }

  public static List<User> readUsersCsvToList(String filePath) {
    List<User> users = new ArrayList<User>();

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
        User user = new User();
        user.setId(UUID.fromString(data[0].trim()));
        user.setName(data[1].trim());

        users.add(user);
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return users;
  }
}
