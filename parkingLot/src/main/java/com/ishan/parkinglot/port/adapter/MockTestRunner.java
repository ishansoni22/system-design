package com.ishan.parkinglot.port.adapter;

import com.ishan.parkinglot.domain.BookingException;
import com.ishan.parkinglot.domain.ParkingLot;
import com.ishan.parkinglot.domain.ParkingTicket;
import com.ishan.parkinglot.domain.VehicleType;
import com.ishan.parkinglot.domain.terminals.EntryTerminal;
import com.ishan.parkinglot.port.adapter.service.NearestSpotPickerService;
import com.ishan.parkinglot.port.adapter.service.DefaultSpotAllotmentService;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MockTestRunner implements CommandLineRunner {

  @Autowired
  private DefaultSpotAllotmentService defaultSpotAllotmentService;

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  @Override
  public void run(String... args) {
    String parkingLotId = "P001";

    EntryTerminal entryTerminal1
        = new EntryTerminal(
        parkingLotId,
        "ET001",
        new NearestSpotPickerService(redisTemplate)
    );

    EntryTerminal entryTerminal2
        = new EntryTerminal(
        parkingLotId,
        "ET002",
        new NearestSpotPickerService(redisTemplate)
    );

    ParkingLot parkingLot
        = new ParkingLot(
        parkingLotId,
        this.defaultSpotAllotmentService,
        Arrays.asList(entryTerminal1, entryTerminal2)
    );

    //First vehicle
    String spot1 = entryTerminal1.pickSpot("MH12 001", VehicleType.HANDICAP);
    try {
      ParkingTicket ticket = parkingLot.bookSpot("MH12 001", VehicleType.LMV, spot1);
      log.info("Booked ticket successfully {}", ticket);
    } catch (BookingException e) {
      log.error("Unable to book ticket", e);
    }

    //Second vehicle
    String spot2 = entryTerminal1.pickSpot("MH12 002", VehicleType.LMV);
    try {
      ParkingTicket ticket = parkingLot.bookSpot("MH12 002", VehicleType.LMV, spot2);
      log.info("Booked ticket successfully {}", ticket);
    } catch (BookingException e) {
      log.error("Unable to book ticket", e);
    }
  }

}