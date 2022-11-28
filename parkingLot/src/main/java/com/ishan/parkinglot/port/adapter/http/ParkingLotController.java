package com.ishan.parkinglot.port.adapter.http;

import com.ishan.parkinglot.application.ParkingLotApplicationService;
import com.ishan.parkinglot.application.SpotBookingCommand;
import com.ishan.parkinglot.domain.BookingException;
import com.ishan.parkinglot.domain.ParkingChart;
import com.ishan.parkinglot.domain.ParkingTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/parking-lot")
public class ParkingLotController {

  @Autowired
  private ParkingLotApplicationService parkingLotApplicationService;

  @PostMapping(
      value = "/{parkingLotId}/terminals/{terminalId}/book",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ParkingTicket> bookSpot(
      @PathVariable("parkingLotId") String parkingLotId,
      @PathVariable("terminalId") String terminalId,
      @RequestBody SpotBookingRequest spotBookingRequest)
      throws BookingException {
    return ResponseEntity.ok(
        this.parkingLotApplicationService.bookSpot(
            new SpotBookingCommand(
                parkingLotId,
                terminalId,
                spotBookingRequest.getVehicleNo(),
                spotBookingRequest.getVehicleType())));
  }

  @GetMapping(value = "/{parkingLotId}/chart", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ParkingChart> getCurrentParkingChart(
      @PathVariable("parkingLotId") String parkingLotId) {
    return ResponseEntity.ok(
        this.parkingLotApplicationService.getCurrentParkingChart(parkingLotId));
  }

}
