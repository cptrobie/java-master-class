package com.cptrobie.booking;

import java.util.Optional;
import java.util.UUID;

public interface BookingDao {

  Booking[] getBookings();

  Optional<Booking> findBookingById(UUID bookingId);

  void saveBooking(Booking booking);

  void deleteBooking(Booking bookingToDelete);
}
