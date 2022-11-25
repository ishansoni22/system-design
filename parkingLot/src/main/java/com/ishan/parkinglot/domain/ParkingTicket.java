package com.ishan.parkinglot.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.Data;

@Data
public class ParkingTicket {

  private String ticketId;
  private String parkingLotId;
  private String entryTerminalId;
  private String spotId;
  private String entryTime;
  private String vehicleNo;
  private VehicleType vehicleType;

  public static ParkingTicket generate(
      String parkingLotId,
      String entryTerminalId,
      String spotId,
      String vehicleNo, VehicleType vehicleType) {
    ParkingTicket ticket = new ParkingTicket();

    String today = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
    String ticketId = today
        + ":"
        + parkingLotId
        + ":"
        + entryTerminalId
        + ":"
        + UUID.randomUUID().toString().substring(0, 5);

    ticket.setTicketId(ticketId);
    ticket.setParkingLotId(parkingLotId);
    ticket.setEntryTerminalId(entryTerminalId);
    ticket.setSpotId(spotId);
    ticket.setEntryTime(today);
    ticket.setVehicleNo(vehicleNo);
    ticket.setVehicleType(vehicleType);

    return ticket;
  }

  @Override
  public String toString() {
    return
        '\n' +
            "==================================================" + '\n' +
            "Ticket Id=" + this.ticketId + '\n' +
            "Parking Lot=" + this.parkingLotId + '\n' +
            "Entry Terminal=" + this.entryTerminalId + '\n' +
            "Spot Id=" + this.spotId + '\n' +
            "Entry Time=" + this.entryTime + '\n' +
            "Vehicle Number=" + this.vehicleNo + '\n' +
            "Vehicle Type=" + this.vehicleType + '\n' +
            "==================================================" +
            '\n';
  }
}
