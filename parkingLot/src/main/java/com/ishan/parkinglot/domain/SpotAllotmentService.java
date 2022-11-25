package com.ishan.parkinglot.domain;

public interface SpotAllotmentService {

  void init(String parkingLotId);

  ParkingTicket bookSpot(String parkingLotId, String entryTerminalId, String vehicleNo,
      VehicleType vehicleType, String spotId) throws BookingException;

}
