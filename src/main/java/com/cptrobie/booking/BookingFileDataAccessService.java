package com.cptrobie.booking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BookingFileDataAccessService extends BookingListDataAccessService implements BookingDao {
  private final String filePath;

  public BookingFileDataAccessService(String filePath) {
    this.filePath = filePath;
    CAR_BOOKINGS = BookingCsvReader.readBookingsCsvToList(filePath);
  }

  @Override
  public void saveBooking(Booking newBooking) {
    // We need to save all the bookings to a file
    // if it already exists, then overwrite otherwise create and populate

    super.saveBooking(newBooking);

    // Then we save CAR_BOOKINGS to the file
    BookingCsvWriter.writeBookingsCsvFromList(CAR_BOOKINGS, filePath);
  }

  @Override
  public void deleteBooking(Booking bookingToDelete) {
    // We need to save all the bookings to a file
    // if it already exists, then overwrite otherwise create and populate

    super.deleteBooking(bookingToDelete);

    // Then we save CAR_BOOKINGS to the file
    BookingCsvWriter.writeBookingsCsvFromList(CAR_BOOKINGS, filePath);
  }

  @Override
  public Optional<Booking> findBookingById(UUID bookingId) {
    return super.findBookingById(bookingId);
  }

  @Override
  public List<Booking> getBookings() {
    return super.getBookings();
  }
}
