package com.ishan.parkinglot.domain;

public class ParkingLot {

  private String id;
  private SpotAllotmentService spotAllotmentService;

  public ParkingLot(String id, SpotAllotmentService spotAllotmentService) {
    this.id = id;
    this.spotAllotmentService = spotAllotmentService;
    this.spotAllotmentService.init(this.id);
  }

  public ParkingTicket bookSpot(String vehicleNo, VehicleType vehicleType, String spotId)
      throws BookingException {
    return this.spotAllotmentService.bookSpot(this.id, vehicleNo, vehicleType, spotId);
  }

}
