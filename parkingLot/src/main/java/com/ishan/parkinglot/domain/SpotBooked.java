package com.ishan.parkinglot.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotBooked extends ParkingEvent {

  private String parkingLotId;
  private String entryTerminalId;
  private String spotId;

  public SpotBooked() {
    super("SpotBooked");
  }

  public SpotBooked(String parkingLotId, String entryTerminalId, String spotId) {
    this();
    this.parkingLotId = parkingLotId;
    this.entryTerminalId = entryTerminalId;
    this.spotId = spotId;
  }

}
