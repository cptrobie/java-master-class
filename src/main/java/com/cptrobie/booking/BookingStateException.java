package com.cptrobie.booking;

public class BookingStateException extends RuntimeException {

  private String message;

  public BookingStateException(String message) {
    super(message);
  }
}
