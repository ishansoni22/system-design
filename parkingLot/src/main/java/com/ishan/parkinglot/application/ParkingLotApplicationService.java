package com.ishan.parkinglot.application;

import com.ishan.parkinglot.domain.BookingException;
import com.ishan.parkinglot.domain.ParkingLot;
import com.ishan.parkinglot.domain.ParkingLotRepository;
import com.ishan.parkinglot.domain.ParkingTicket;
import com.ishan.parkinglot.domain.terminals.EntryTerminal;
import com.ishan.parkinglot.domain.terminals.EntryTerminalRepository;
import com.ishan.parkinglot.domain.terminals.SpotSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ParkingLotApplicationService {

  @Autowired
  private EntryTerminalRepository entryTerminalRepository;

  @Autowired
  private ParkingLotRepository parkingLotRepository;

  @Autowired
  private SpotSyncService spotSyncService;

  public ParkingTicket bookSpot(SpotBookingCommand spotBookingCommand) throws BookingException {
    String parkingLotId = spotBookingCommand.getParkingLotId();
    String entryTerminalId = spotBookingCommand.getTerminalId();

    EntryTerminal entryTerminal =
        this.entryTerminalRepository.getEntryTerminal(entryTerminalId).orElseThrow();

    String pickedSpot =
        entryTerminal.pickSpot(
            spotBookingCommand.getVehicleNo(), spotBookingCommand.getVehicleType());

    log.info("Spot picked by terminal {} is {}", entryTerminal.getTerminalId(), pickedSpot);

    ParkingLot parkingLot = this.parkingLotRepository.getParkingLot(parkingLotId).orElseThrow();

    return parkingLot.bookSpot(
        this.spotSyncService,
        spotBookingCommand.getVehicleNo(),
        spotBookingCommand.getVehicleType(),
        entryTerminalId,
        pickedSpot
    );
  }

}