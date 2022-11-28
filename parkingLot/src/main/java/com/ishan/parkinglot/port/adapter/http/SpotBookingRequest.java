package com.ishan.parkinglot.port.adapter.http;

import com.ishan.parkinglot.domain.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpotBookingRequest {

  private String vehicleNo;
  private VehicleType vehicleType;

}
