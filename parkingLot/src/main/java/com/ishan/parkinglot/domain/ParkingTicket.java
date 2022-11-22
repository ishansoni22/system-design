package com.ishan.parkinglot.domain;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ParkingTicket {

  private String ticketId;
  private LocalDateTime entryTime;
  private String vehicleNo;
  private VehicleType vehicleType;

}
