package com.ishan.parkinglot.domain.terminals;

import com.ishan.parkinglot.domain.VehicleType;

public interface SpotPickerService {

  void init(String parkingLotId, String terminalId);

  String pickSpot(String parkingLotId, String terminalId, String vehicleNo, VehicleType vehicleType);

  void onSpotTaken(String parkingLotId, String terminalId, String spotId);

  void onSpotReleased(String parkingLotId, String terminalId, String spotId);

}
