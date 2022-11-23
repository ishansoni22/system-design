package com.ishan.parkinglot.domain;

import com.ishan.parkinglot.domain.terminals.EntryTerminal;
import java.util.List;

public class ParkingLot {

  private String id;
  private SpotAllotmentService spotAllotmentService;
  private List<EntryTerminal> entryTerminals;

  public ParkingLot(String id, SpotAllotmentService spotAllotmentService,
      List<EntryTerminal> entryTerminals) {
    this.id = id;
    this.spotAllotmentService = spotAllotmentService;
    this.entryTerminals = entryTerminals;
    this.spotAllotmentService.init(this.id);
  }

  public ParkingTicket bookSpot(String vehicleNo, VehicleType vehicleType, String spotId)
      throws BookingException {
    ParkingTicket parkingTicket = this.spotAllotmentService
        .bookSpot(this.id, vehicleNo, vehicleType, spotId);
    this.entryTerminals.forEach(entryTerminal -> entryTerminal.onSpotTaken(spotId));
    return parkingTicket;
  }

}
