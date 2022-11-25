package com.ishan.parkinglot.domain;

import com.ishan.parkinglot.domain.terminals.SpotSyncService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParkingLot {

  private String id;
  private SpotAllotmentService spotAllotmentService;

  public ParkingLot(String id, SpotAllotmentService spotAllotmentService) {
    this.id = id;
    this.spotAllotmentService = spotAllotmentService;
    this.spotAllotmentService.init(this.id);
  }

  public ParkingTicket bookSpot(
      SpotSyncService spotSyncService,
      String vehicleNo, VehicleType vehicleType,
      String entryTerminalId, String spotId)
      throws BookingException {
    ParkingTicket ticket =  this.spotAllotmentService
        .bookSpot(this.id, entryTerminalId, vehicleNo, vehicleType, spotId);
    log.info("Spot booked {}", ticket);
    spotSyncService.publishSpotBooked(new SpotBooked(this.id, entryTerminalId, spotId));
    return ticket;
  }

}
