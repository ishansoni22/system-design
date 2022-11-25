package com.ishan.parkinglot.port.adapter.db;

import com.ishan.parkinglot.domain.terminals.EntryTerminal;
import com.ishan.parkinglot.domain.terminals.EntryTerminalRepository;
import com.ishan.parkinglot.domain.terminals.SpotPickerService;
import com.ishan.parkinglot.port.adapter.service.NearestSpotPickerService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EntryTerminalRepositoryImpl implements EntryTerminalRepository {

  @Autowired
  private TerminalJpaRepository terminalJpaRepository;

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  @Autowired
  private SpotRepository spotRepository;

  @Override
  public Optional<EntryTerminal> getEntryTerminal(String terminalId) {
    return this.terminalJpaRepository.findById(terminalId)
        .map(terminal -> new EntryTerminal(
            terminal.getParkingLotId(),
            terminal.getId(),
            getSpotPickerService(terminal.getPickerStrategy())
        ));
  }

  @Override
  public List<EntryTerminal> getAllEntryTerminalsForParkingLot(String parkingLotId) {
    return this.terminalJpaRepository.findAllByParkingLotId(parkingLotId)
        .stream()
        .map(terminal -> new EntryTerminal(
            terminal.getParkingLotId(),
            terminal.getId(),
            getSpotPickerService(terminal.getPickerStrategy())
        )).collect(Collectors.toList());
  }

  private SpotPickerService getSpotPickerService(String pickerStrategy) {
    if ("NEAREST".equals(pickerStrategy)) {
      return new NearestSpotPickerService(this.redisTemplate, this.spotRepository);
    }
    return null;
  }

}
