package com.ishan.parkinglot.port.adapter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishan.parkinglot.domain.ParkingEvent;
import com.ishan.parkinglot.domain.SpotBooked;
import com.ishan.parkinglot.domain.SpotReleased;
import com.ishan.parkinglot.domain.terminals.EntryTerminalRepository;
import com.ishan.parkinglot.domain.terminals.SpotSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DefaultSpotSyncService implements SpotSyncService {

  public static final String SYNC_CHANNEL = "parking-events";

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  @Autowired
  private EntryTerminalRepository entryTerminalRepository;

  @Override
  public void publishSpotBooked(SpotBooked spotBooked) {
    try {
      this.stringRedisTemplate.convertAndSend(
          SYNC_CHANNEL, this.objectMapper.writeValueAsString(spotBooked));
    } catch (Exception e) {
      log.error("Unable to send spot allocated event", e);
    }
  }

  @Override
  public void handleSpotBooked(SpotBooked spotBooked) {
    this.entryTerminalRepository
        .getAllEntryTerminalsForParkingLot(spotBooked.getParkingLotId())
        .forEach(entryTerminal -> entryTerminal.onSpotTaken(spotBooked.getSpotId()));
  }

  @Override
  public void publishSpotReleased(SpotReleased spotReleased) {}

  @Override
  public void handleSpotReleased(SpotReleased spotReleased) {}

  public void handleMessage(String message) {
    log.info("Incoming parking event message {} ", message);
    try {
      ParkingEvent parkingEvent = this.objectMapper.readValue(message, ParkingEvent.class);
      if ("SpotBooked".equals(parkingEvent.getType())) {
        this.handleSpotBooked(this.objectMapper.readValue(message, SpotBooked.class));
      } else if ("SpotReleased".equals(parkingEvent.getType())) {
        this.handleSpotReleased(this.objectMapper.readValue(message, SpotReleased.class));
      } else {
        throw new IllegalStateException("Unknown parking event type" + parkingEvent.getType());
      }
    } catch (Exception e) {
      log.error("Unable to receive spot allocated event", e);
    }
  }

}
