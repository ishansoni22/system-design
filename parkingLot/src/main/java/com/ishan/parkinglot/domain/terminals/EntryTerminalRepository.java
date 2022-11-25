package com.ishan.parkinglot.domain.terminals;

import java.util.List;
import java.util.Optional;

public interface EntryTerminalRepository {

  Optional<EntryTerminal> getEntryTerminal(String terminalId);

  List<EntryTerminal> getAllEntryTerminalsForParkingLot(String parkingLotId);

}
