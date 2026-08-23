package com.cptrobie.booking;

public class BookingNotFoundException extends RuntimeException {

  private String message;

  public BookingNotFoundException(String message) {
    super(message);
  }
}
