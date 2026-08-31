package com.cptrobie.util;

public class CsvStringEscapeSpecChars {

  // Helper to handle commas or quotes inside strings (RFC 4180 rules)
  public static String escapeSpecialCharacters(String data) {
    if (data == null) {
      return "";
    }
    String escapedData = data.replaceAll("\"", "\"\"");
    if (escapedData.contains(",") || escapedData.contains("\"") || escapedData.contains("\n")) {
      escapedData = "\"" + escapedData + "\"";
    }
    return escapedData;
  }
}
