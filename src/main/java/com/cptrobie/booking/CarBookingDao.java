package com.cptrobie.booking;

import java.util.Optional;
import java.util.UUID;

public interface CarBookingDao {

  CarBooking[] getBookings();

  Optional<CarBooking> findBookingById(UUID bookingId);

  void saveBooking(CarBooking carBooking);

  void deleteBooking(CarBooking carBookingToDelete);
}
