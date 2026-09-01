package com.cptrobie.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BookingListDataAccessService implements BookingDao {
  protected static List<Booking> CAR_BOOKINGS;

  public BookingListDataAccessService() {
    CAR_BOOKINGS = new ArrayList<Booking>();
  }


  @Override
  public void saveBooking(Booking booking) {

    if (booking == null) {
      throw new IllegalArgumentException("Booking cannot be null");
    }

    CAR_BOOKINGS.add(booking);
  }

  @Override
  public void deleteBooking(Booking bookingToDelete) {

    if (bookingToDelete == null) {
      throw new IllegalArgumentException("Booking arg cannot be null");
    }

    CAR_BOOKINGS.remove(bookingToDelete);
  }

  @Override
  public Optional<Booking> findBookingById(UUID bookingId) {

    if (bookingId == null) {
      return Optional.empty();
    }

    Booking bookingTarget = null;
    for (Booking booking : CAR_BOOKINGS) {
      if (booking != null && booking.getId().equals(bookingId)) {
        bookingTarget = booking;
        break;
      }
    }
    return Optional.ofNullable(bookingTarget);
  }

  @Override
  public List<Booking> getBookings() {
    return CAR_BOOKINGS;
  }
}
