package com.ishan.parkinglot.port.adapter;

import com.ishan.parkinglot.domain.BookingException;
import com.ishan.parkinglot.domain.ParkingLot;
import com.ishan.parkinglot.domain.ParkingTicket;
import com.ishan.parkinglot.domain.SpotAllotmentService;
import com.ishan.parkinglot.domain.VehicleType;
import com.ishan.parkinglot.domain.terminals.EntryTerminal;
import com.ishan.parkinglot.domain.terminals.SpotSyncService;
import com.ishan.parkinglot.port.adapter.db.SpotRepository;
import com.ishan.parkinglot.port.adapter.service.NearestSpotPickerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MockTestRunner implements CommandLineRunner {

  @Autowired
  private SpotAllotmentService spotAllotmentService;

  @Autowired
  private SpotSyncService spotSyncService;

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  @Autowired
  private SpotRepository spotRepository;

  @Override
  public void run(String... args) {
    String parkingLotId = "P001";

    EntryTerminal entryTerminal1 =
        new EntryTerminal(parkingLotId, "ET001", new NearestSpotPickerService(
            this.redisTemplate, this.spotRepository));

    EntryTerminal entryTerminal2 =
        new EntryTerminal(parkingLotId, "ET002", new NearestSpotPickerService(
            this.redisTemplate, this.spotRepository));

    ParkingLot parkingLot = new ParkingLot(parkingLotId, this.spotAllotmentService);

    String spot1 = entryTerminal1.pickSpot("MH12 001", VehicleType.HANDICAP);
    log.info("Spot picked by terminal {} is {}", entryTerminal1.getTerminalId(), spot1);

    try {
      ParkingTicket ticket =
          parkingLot.bookSpot(
              this.spotSyncService, "MH12 001", VehicleType.LMV, entryTerminal1.getTerminalId(), spot1);
      log.info("Booked ticket successfully {}", ticket);
    } catch (BookingException e) {
      log.error("Unable to book ticket", e);
    }

    String spot2 = entryTerminal2.pickSpot("MH12 002", VehicleType.LMV);
    log.info("Spot picked by terminal {} is {}", entryTerminal2.getTerminalId(), spot2);

    try {
      ParkingTicket ticket =
          parkingLot.bookSpot(
              this.spotSyncService, "MH12 002", VehicleType.LMV, entryTerminal2.getTerminalId(), spot2);
      log.info("Booked ticket successfully {}", ticket);
    } catch (BookingException e) {
      log.error("Unable to book ticket", e);
    }

  }

}
