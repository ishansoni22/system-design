package com.ishan.parkinglot.application;

import com.ishan.parkinglot.domain.VehicleType;
import java.util.Objects;
import lombok.Getter;

@Getter
public class SpotBookingCommand {

  private String parkingLotId;
  private String terminalId;
  private String vehicleNo;
  private VehicleType vehicleType;

  public SpotBookingCommand(String parkingLotId, String terminalId,
      String vehicleNo, VehicleType vehicleType) {
    this.parkingLotId = Objects.requireNonNull(parkingLotId);
    this.terminalId = Objects.requireNonNull(terminalId);
    this.vehicleNo = Objects.requireNonNull(vehicleNo);
    this.vehicleType = Objects.requireNonNull(vehicleType);
  }

}
