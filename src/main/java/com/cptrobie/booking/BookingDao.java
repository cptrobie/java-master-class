package com.cptrobie.booking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingDao {

  List<Booking> getBookings();

  Optional<Booking> findBookingById(UUID bookingId);

  void saveBooking(Booking booking);

  void deleteBooking(Booking bookingToDelete);
}
