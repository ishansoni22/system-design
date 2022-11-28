package com.ishan.parkinglot.port.adapter.db;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Terminal {

  enum TerminalType {
    ENTRY, EXIT;
  }

  @Id
  private String id;

  private String parkingLotId;

  @Enumerated(EnumType.STRING)
  private TerminalType type;

  private String pickerStrategy;

}
