package com.cptrobie.booking;

import java.util.Optional;
import java.util.UUID;

public class BookingFileDataAccessService implements BookingDao {
  private final String filePath;
  private static Booking[] CAR_BOOKINGS;

  public BookingFileDataAccessService(String filePath) {
    this.filePath = filePath;
    CAR_BOOKINGS = BookingCsvReader.readBookingsCsvToArray(filePath);
  }

  @Override
  public void saveBooking(Booking newBooking) {
    // Since we are saving to a file that gets overwritten (if exists)
    // we need to save all the bookings not just the latest booking
    // NOTE: The file based CAR_BOOKINGS array is NOT set to a fixed length like the Array version

    // We first add it to the CAR_BOOKINGS' array
    Booking[] newBookings = new Booking[CAR_BOOKINGS.length + 1];
    var i = 0;
    for (Booking booking : CAR_BOOKINGS) {
      newBookings[i++] = booking;
    }
    newBookings[i] = newBooking;

    CAR_BOOKINGS = newBookings;

    // Then we save the updated array to the file
    BookingCsvWriter.writeBookingsCsvFromArray(CAR_BOOKINGS, filePath);
  }

  @Override
  public void deleteBooking(Booking bookingToDelete) {
    // Since we are saving to a file that gets overwritten (if exists)
    // we need to save all the bookings
    // NOTE: The file based CAR_BOOKINGS array is NOT set to a fixed length like the Array version
    // Any time we update CAR_BOOKINGS we also need to update the file

    var i = 0;
    Booking[] newBookings = new Booking[CAR_BOOKINGS.length - 1];

    for (Booking booking : CAR_BOOKINGS) {
      if (booking != null && !booking.getId().equals(bookingToDelete.getId())) {
        newBookings[i++] = booking;
      }
    }
    CAR_BOOKINGS = newBookings;
    BookingCsvWriter.writeBookingsCsvFromArray(CAR_BOOKINGS, filePath);
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
