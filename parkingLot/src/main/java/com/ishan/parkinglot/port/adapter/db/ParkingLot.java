package com.ishan.parkinglot.port.adapter.db;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ParkingLot {

  @Id
  private String id;

  private String name;

}
