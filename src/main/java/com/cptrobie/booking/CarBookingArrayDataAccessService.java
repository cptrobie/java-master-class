package com.cptrobie.booking;

import java.util.Optional;
import java.util.UUID;

public class CarBookingArrayDataAccessService implements CarBookingDao {
  // Was not given the array size as a requirement, so I arbitrarily chose 10
  private static final CarBooking[] CAR_BOOKINGS;

  static {
    CAR_BOOKINGS = new CarBooking[10];
  }

  @Override
  public void saveBooking(CarBooking carBooking) {
    // Since the booking array is larger than the total number of cars...
    for (int i = 0; i < CAR_BOOKINGS.length; i++) {
      if (CAR_BOOKINGS[i] == null) {
        CAR_BOOKINGS[i] = carBooking;
        break;
      }
    }
  }

  @Override
  public void deleteBooking(CarBooking carBookingToDelete) {
    // create a new array and copy over all bookings except the booking to delete

    var i = 0;
    CarBooking[] newCarBookings = new CarBooking[CAR_BOOKINGS.length - 1];

    for (CarBooking carBooking : CAR_BOOKINGS) {
      if (carBooking != null && !carBooking.getId().equals(carBookingToDelete.getId())) {
        newCarBookings[i++] = carBooking;
      }
    }
    // Resetting i
    for (i = 0; i < CAR_BOOKINGS.length; i++) {
      if (i < newCarBookings.length) {
        CAR_BOOKINGS[i] = newCarBookings[i];
      } else {
        CAR_BOOKINGS[i] = null;
      }
    }
  }

  @Override
  public Optional<CarBooking> findBookingById(UUID bookingId) {

    if (bookingId == null) {
      return Optional.empty();
    }

    for (CarBooking carBooking : CAR_BOOKINGS) {
      if (carBooking != null && carBooking.getId().equals(bookingId)) {
        return Optional.of(carBooking);
      }
    }

    throw new BookingNotFoundException("CarBooking with id of " + bookingId + " was not found");
  }

  @Override
  public CarBooking[] getBookings() {
    return CAR_BOOKINGS;
  }
}
