package com.ishan.parkinglot.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.Data;

@Data
public class ParkingTicket {

  private String ticketId;
  private String spotId;
  private LocalDateTime entryTime;
  private String vehicleNo;
  private VehicleType vehicleType;

  public static String generateTicketId(String parkingLotId) {
    LocalDate today = LocalDate.now();
    return today.format(DateTimeFormatter.ISO_DATE)
        + "-"
        + parkingLotId
        + "-"
        + UUID.randomUUID().toString().substring(0, 5);
  }

  @Override
  public String toString() {
    return
        '\n' +
            "==================================================" + '\n' +
            "Ticket Id=" + this.ticketId + '\n' +
            "Spot Id=" + this.spotId + '\n' +
            "Entry Time=" + this.entryTime + '\n' +
            "Vehicle Number=" + this.vehicleNo + '\n' +
            "Vehicle Type=" + this.vehicleType + '\n' +
            "==================================================" +
            '\n';
  }
}
