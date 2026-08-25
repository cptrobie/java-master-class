package com.cptrobie.booking;

public class CarBookingDao {
  // Was not given the array size as a requirement, so I arbitrarily chose 10
  private static final CarBooking[] carBookings;

  static {
    carBookings = new CarBooking[10];
  }

  public CarBooking[] getCarBookings() {
    return carBookings;
  }

  public void bookCar(CarBooking carBooking) {
    // Since the booking array is larger than the total number of cars...
    for (int i = 0; i < carBookings.length; i++) {
      if (carBookings[i] == null) {
        carBookings[i] = carBooking;
        break;
      }
    }
  }

  public void deleteCarBooking(CarBooking carBookingToDelete) {
    // create a new array and copy over all bookings except the booking to delete

    var i = 0;
    CarBooking[] newCarBookings = new CarBooking[carBookings.length - 1];

    for (CarBooking carBooking : carBookings) {
      if (carBooking != null && !carBooking.getId().equals(carBookingToDelete.getId())) {
        newCarBookings[i++] = carBooking;
      }
    }
    // Resetting i
    for (i = 0; i < carBookings.length; i++) {
      if (i < newCarBookings.length) {
        carBookings[i] = newCarBookings[i];
      } else {
        carBookings[i] = null;
      }
    }
  }
}
