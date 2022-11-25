package com.ishan.parkinglot.port.adapter.db;

import com.ishan.parkinglot.domain.VehicleType;
import com.vladmihalcea.hibernate.type.json.JsonType;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Data
@TypeDef(name = "json", typeClass = JsonType.class)
public class Spot {

  @Id
  private String id;

  private String parkingLotId;

  @Enumerated(EnumType.STRING)
  private VehicleType type;

  @Type(type = "json")
  @Column(columnDefinition = "json")
  private Map<String, String> configurations;
}
