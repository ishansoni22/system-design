package com.ishan.parkinglot.port.adapter;

import com.ishan.parkinglot.domain.BookingException;
import com.ishan.parkinglot.domain.ParkingLot;
import com.ishan.parkinglot.domain.ParkingTicket;
import com.ishan.parkinglot.domain.VehicleType;
import com.ishan.parkinglot.port.adapter.service.DefaultSpotAllotmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SetupMockParkingGarage implements CommandLineRunner {

  @Autowired
  private DefaultSpotAllotmentService defaultSpotAllotmentService;

  @Override
  public void run(String... args) {

    ParkingLot parkingLot = new ParkingLot("test-1", this.defaultSpotAllotmentService);
    String spot = "5";
    try {
      ParkingTicket ticket = parkingLot.bookSpot("", VehicleType.LMV, spot);
      log.info("Booked ticket successfully {}", ticket);
    } catch (BookingException e) {
      log.error("Unable to book ticket", e);
    }

    try {
      ParkingTicket ticket = parkingLot.bookSpot("", VehicleType.LMV, spot);
      log.info("Booked ticket successfully {}", ticket);
    } catch (BookingException e) {
      log.error("Unable to book ticket", e);
    }

  }

}