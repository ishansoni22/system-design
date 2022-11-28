package com.ishan.parkinglot.port.adapter.db;

import com.ishan.parkinglot.domain.terminals.EntryTerminal;
import com.ishan.parkinglot.domain.terminals.EntryTerminalRepository;
import com.ishan.parkinglot.domain.terminals.SpotPickerService;
import com.ishan.parkinglot.port.adapter.service.NearestSpotPickerService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EntryTerminalRepositoryImpl implements EntryTerminalRepository {

  @Autowired
  private TerminalJpaRepository terminalJpaRepository;

  @Autowired
  private NearestSpotPickerService nearestSpotPickerService;

  @Override
  public Optional<EntryTerminal> getEntryTerminal(String terminalId) {
    return this.terminalJpaRepository.findById(terminalId).map(this::getEntryTerminal);
  }

  @Override
  public List<EntryTerminal> getAllEntryTerminalsForParkingLot(String parkingLotId) {
    return this.terminalJpaRepository.findAllByParkingLotId(parkingLotId).stream()
        .map(this::getEntryTerminal)
        .collect(Collectors.toList());
  }

  private EntryTerminal getEntryTerminal(Terminal terminal) {
    SpotPickerService spotPickerService = null;
    if ("NEAREST".equals(terminal.getPickerStrategy())) {
      spotPickerService = this.nearestSpotPickerService;
    }
    return new EntryTerminal(terminal.getParkingLotId(), terminal.getId(), spotPickerService);
  }

}
