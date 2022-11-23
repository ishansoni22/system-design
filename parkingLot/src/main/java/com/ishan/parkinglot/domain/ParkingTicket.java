package com.ishan.parkinglot.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    return today.getDayOfMonth() + today.getMonthValue() + today.getYear()
        + "-"
        + parkingLotId
        + "-" + UUID.randomUUID().toString().substring(0, 5);
  }

  @Override
  public String toString() {
    return
        '\n' +
            "==================================================" + '\n' +
            "Ticket Id=" + ticketId + '\n' +
            "Entry Time=" + entryTime + '\n' +
            "Vehicle Number=" + vehicleNo + '\n' +
            "Vehicle Type=" + vehicleType + '\n' +
            "==================================================" +
            '\n';
  }
}
