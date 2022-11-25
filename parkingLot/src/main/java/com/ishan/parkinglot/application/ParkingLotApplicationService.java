package com.ishan.parkinglot.application;

import com.ishan.parkinglot.domain.BookingException;
import com.ishan.parkinglot.domain.ParkingLot;
import com.ishan.parkinglot.domain.ParkingLotRepository;
import com.ishan.parkinglot.domain.ParkingTicket;
import com.ishan.parkinglot.domain.terminals.EntryTerminal;
import com.ishan.parkinglot.domain.terminals.EntryTerminalRepository;
import com.ishan.parkinglot.domain.terminals.SpotSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotApplicationService {

  /* @Autowired */
  private EntryTerminalRepository entryTerminalRepository;

  /* @Autowired */
  private ParkingLotRepository parkingLotRepository;

  @Autowired
  private SpotSyncService spotSyncService;

  public ParkingTicket bookSpot(SpotBookingCommand spotBookingCommand) throws BookingException {
    String parkingLotId = spotBookingCommand.getParkingLotId();
    String entryTerminalId = spotBookingCommand.getTerminalId();

    EntryTerminal entryTerminal =
        this.entryTerminalRepository.getEntryTerminal(parkingLotId).orElseThrow();

    String pickedSpot =
        entryTerminal.pickSpot(
            spotBookingCommand.getVehicleNo(), spotBookingCommand.getVehicleType());

    ParkingLot parkingLot = this.parkingLotRepository.getParkingLot(entryTerminalId).orElseThrow();

    return parkingLot.bookSpot(
        this.spotSyncService,
        spotBookingCommand.getVehicleNo(),
        spotBookingCommand.getVehicleType(),
        entryTerminalId,
        pickedSpot);
  }

}
