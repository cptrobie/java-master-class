package com.cptrobie.booking;

import java.util.Optional;
import java.util.UUID;

public class BookingArrayDataAccessService implements BookingDao {
  // Was not given the array size as a requirement, so I arbitrarily chose 10
  private static final Booking[] CAR_BOOKINGS;

  static {
    CAR_BOOKINGS = new Booking[10];
  }

  @Override
  public void saveBooking(Booking booking) {
    // Since the booking array is larger than the total number of cars...
    for (int i = 0; i < CAR_BOOKINGS.length; i++) {
      if (CAR_BOOKINGS[i] == null) {
        CAR_BOOKINGS[i] = booking;
        break;
      }
    }
  }

  @Override
  public void deleteBooking(Booking bookingToDelete) {
    // create a new array and copy over all bookings except the booking to delete

    var i = 0;
    Booking[] newBookings = new Booking[CAR_BOOKINGS.length - 1];

    for (Booking booking : CAR_BOOKINGS) {
      if (booking != null && !booking.getId().equals(bookingToDelete.getId())) {
        newBookings[i++] = booking;
      }
    }
    // Resetting i
    for (i = 0; i < CAR_BOOKINGS.length; i++) {
      if (i < newBookings.length) {
        CAR_BOOKINGS[i] = newBookings[i];
      } else {
        CAR_BOOKINGS[i] = null;
      }
    }
  }

  @Override
  public Optional<Booking> findBookingById(UUID bookingId) {

    if (bookingId == null) {
      return Optional.empty();
    }

    for (Booking booking : CAR_BOOKINGS) {
      if (booking != null && booking.getId().equals(bookingId)) {
        return Optional.of(booking);
      }
    }
    return Optional.empty();
  }

  @Override
  public Booking[] getBookings() {
    return CAR_BOOKINGS;
  }
}
