package com.cptrobie.car;

public class CarNotFoundException extends RuntimeException {

  private String message;

  public CarNotFoundException(String message) {
    super(message);
  }
}
