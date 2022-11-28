package com.ishan.parkinglot.domain;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ParkingChart {

  private String parkingId;

  private List<SpotStatus> spots;

  @Getter
  @Setter
  public static class SpotStatus {
    private String spotId;
    private boolean empty;
    private Map<String, Object> additionalProperties;
  }

}
