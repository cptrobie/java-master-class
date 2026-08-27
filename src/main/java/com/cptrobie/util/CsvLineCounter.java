package com.cptrobie.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// This is probably overkill since both user & car csv files are so small.
// I could have just read and populated from inside respective *FileAccessDataService ctor.
// I did it this way because it is not a best practice to do it that way
// Not sure if this is any better :-| lol
public class CsvLineCounter {

  public static int countLines(String filePath) {
    var count = -1; // set to negative 1 to account for the header row
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      while (br.readLine() != null) {
        count++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return count;
  }
}
