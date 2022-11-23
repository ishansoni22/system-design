package com.ishan.parkinglot.port.adapter.service;

import com.ishan.parkinglot.domain.VehicleType;
import com.ishan.parkinglot.domain.terminals.SpotPickerService;
import org.springframework.data.redis.core.RedisTemplate;

public class NearestSpotPickerService implements SpotPickerService {

  private RedisTemplate<String, Object> redisTemplate;

  public NearestSpotPickerService(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public void init(String parkingLotId, String terminalId) {

  }

  @Override
  public String pickSpot(String vehicleNo, VehicleType vehicleType) {
    return null;
  }

  @Override
  public void onSpotTaken(String spotId) {

  }

  @Override
  public void onSpotReleased(String spotId) {

  }

}
