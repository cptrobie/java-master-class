package com.cptrobie.user;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserCsvReader {

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
