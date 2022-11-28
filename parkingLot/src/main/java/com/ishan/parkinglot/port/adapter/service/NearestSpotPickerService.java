package com.ishan.parkinglot.port.adapter.service;

import com.ishan.parkinglot.domain.VehicleType;
import com.ishan.parkinglot.domain.terminals.SpotPickerService;
import com.ishan.parkinglot.port.adapter.db.SpotRepository;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

@Service
public class NearestSpotPickerService implements SpotPickerService {

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  @Autowired
  private SpotRepository spotRepository;

  @Override
  public void init(String parkingLotId, String terminalId) {
    Boolean terminalMap =
        this.redisTemplate
            .opsForZSet()
            .getOperations()
            .hasKey(getTerminalMapKey(parkingLotId, terminalId));
    if (Objects.isNull(terminalMap) || !terminalMap) {
      Set<TypedTuple<Object>> spotDistancesFromTerminal = new HashSet<>();
      this.spotRepository
          .findAllByParkingLotId(parkingLotId)
          .forEach(
              spot -> {
                Map<String, String> configurationsMap = spot.getConfigurations();
                Double distance =
                    Double.valueOf(
                        configurationsMap.getOrDefault(
                            terminalId + "." + "distance", String.valueOf(Integer.MAX_VALUE)));
                TypedTuple spotDistanceFromTerminal = new DefaultTypedTuple(spot.getId(), distance);
                spotDistancesFromTerminal.add(spotDistanceFromTerminal);
              });
      this.redisTemplate
          .opsForZSet()
          .add(getTerminalMapKey(parkingLotId, terminalId), spotDistancesFromTerminal);
    }
  }

  @Override
  public String pickSpot(
      String parkingLotId, String terminalId, String vehicleNo, VehicleType vehicleType) {
    Set<Object> spot =
        this.redisTemplate.opsForZSet().range(getTerminalMapKey(parkingLotId, terminalId), 0, 0);
    return (String) spot.toArray()[0];
  }

  @Override
  public void onSpotTaken(String parkingLotId, String terminalId, String spotId) {
    this.redisTemplate.opsForZSet().remove(getTerminalMapKey(parkingLotId, terminalId), spotId);
  }

  @Override
  public void onSpotReleased(String parkingLotId, String terminalId, String spotId) {}

  private String getTerminalMapKey(String parkingLotId, String terminalId) {
    return parkingLotId + ":" + "terminal" + ":" + terminalId;
  }

}
