package com.ishan.parkinglot.domain.terminals;

import com.ishan.parkinglot.domain.VehicleType;

public interface SpotPickerService {

  void init(String parkingLotId, String terminalId);

  String pickSpot(String vehicleNo, VehicleType vehicleType);

  void onSpotTaken(String spotId);

  void onSpotReleased(String spotId);

}
