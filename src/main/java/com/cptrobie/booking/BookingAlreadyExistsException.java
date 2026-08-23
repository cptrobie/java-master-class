package com.cptrobie.booking;

public class BookingAlreadyExistsException extends RuntimeException {

  private String message;

  public BookingAlreadyExistsException(String message) {
    super(message);
  }
}
