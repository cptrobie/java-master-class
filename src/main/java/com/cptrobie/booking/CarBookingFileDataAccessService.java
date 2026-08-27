package com.cptrobie.booking;

import java.util.Optional;
import java.util.UUID;

public class CarBookingFileDataAccessService implements CarBookingDao {
  private final String filePath;
  private static CarBooking[] CAR_BOOKINGS;

  public CarBookingFileDataAccessService(String filePath) {
    this.filePath = filePath;
    CAR_BOOKINGS = CarBookingCsvReader.readBookingsCsvToArray(filePath);
  }

  @Override
  public void saveBooking(CarBooking newCarBooking) {
    // Since we are saving to a file that gets overwritten (if exists)
    // we need to save all the bookings not just the latest booking
    // NOTE: The file based CAR_BOOKINGS array is NOT set to a fixed length like the Array version

    // We first add it to the CAR_BOOKINGS' array
    CarBooking[] newCarBookings = new CarBooking[CAR_BOOKINGS.length + 1];
    var i = 0;
    for (CarBooking carBooking : CAR_BOOKINGS) {
      newCarBookings[i++] = carBooking;
    }
    newCarBookings[i] = newCarBooking;

    CAR_BOOKINGS = newCarBookings;

    // Then we save the updated array to the file
    CarBookingCsvWriter.writeBookingsCsvFromArray(CAR_BOOKINGS, filePath);
  }

  @Override
  public void deleteBooking(CarBooking carBookingToDelete) {
    // Since we are saving to a file that gets overwritten (if exists)
    // we need to save all the bookings
    // NOTE: The file based CAR_BOOKINGS array is NOT set to a fixed length like the Array version
    // Any time we update CAR_BOOKINGS we also need to update the file

    var i = 0;
    CarBooking[] newCarBookings = new CarBooking[CAR_BOOKINGS.length - 1];

    for (CarBooking carBooking : CAR_BOOKINGS) {
      if (carBooking != null && !carBooking.getId().equals(carBookingToDelete.getId())) {
        newCarBookings[i++] = carBooking;
      }
    }
    CAR_BOOKINGS = newCarBookings;
    CarBookingCsvWriter.writeBookingsCsvFromArray(CAR_BOOKINGS, filePath);
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
