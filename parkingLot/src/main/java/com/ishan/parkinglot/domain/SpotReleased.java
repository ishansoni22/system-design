package com.ishan.parkinglot.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotReleased extends ParkingEvent {

  private String parkingLotId;
  private String entryTerminalId;
  private String spotId;

  public SpotReleased() {
    super("SpotBooked");
  }

  public SpotReleased(String parkingLotId, String entryTerminalId, String spotId) {
    this();
    this.parkingLotId = parkingLotId;
    this.entryTerminalId = entryTerminalId;
    this.spotId = spotId;
  }

}
