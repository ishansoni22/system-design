package com.ishan.parkinglot.domain.terminals;

import com.ishan.parkinglot.domain.VehicleType;
import lombok.Data;

@Data
public class EntryTerminal {

  private String parkingLotId;
  private String terminalId;
  private SpotPickerService spotPickerService;

  public EntryTerminal(String parkingLotId, String terminalId,
      SpotPickerService spotPickerService) {
    this.parkingLotId = parkingLotId;
    this.terminalId = terminalId;
    this.spotPickerService = spotPickerService;
    this.spotPickerService.init(parkingLotId, terminalId);
  }

  public String pickSpot(String vehicleNo, VehicleType vehicleType) {
    return this.spotPickerService.pickSpot(this.parkingLotId, this.terminalId, vehicleNo, vehicleType);
  }

  public void onSpotTaken(String spotId) {
    this.spotPickerService.onSpotTaken(this.parkingLotId, this.terminalId, spotId);
  }

  public void onSpotReleased(String spotId) {
    this.spotPickerService.onSpotReleased(this.parkingLotId, this.terminalId, spotId);
  }

}
