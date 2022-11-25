package com.ishan.parkinglot.port.adapter.service;

import com.ishan.parkinglot.domain.BookingException;
import com.ishan.parkinglot.domain.ParkingTicket;
import com.ishan.parkinglot.domain.SpotAllotmentService;
import com.ishan.parkinglot.domain.VehicleType;
import com.ishan.parkinglot.port.adapter.db.SpotRepository;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class DefaultSpotAllotmentService implements SpotAllotmentService {

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  @Autowired
  private SpotRepository spotRepository;

  @Override
  public void init(String parkingLotId) {
    Boolean parkingMap
        = this.redisTemplate.opsForValue().getOperations().hasKey(getParkingMapKey(parkingLotId));
    if (Objects.isNull(parkingMap) || !parkingMap) {
      // Get all parking spots for this parking lot and create a redis bitmap
      int spots = this.spotRepository
          .countByParkingLotId(parkingLotId);

      this.redisTemplate
          .opsForValue()
          .bitField(
              getParkingMapKey(parkingLotId),
              BitFieldSubCommands.create()
              .set(BitFieldSubCommands.BitFieldType.unsigned(spots)).valueAt(0).to((long) (Math.pow(2, spots) - 1))
          );
    }
  }

  /*
  Initially all bits/spots are initialised to 1 (Available)
  To book a spot, it's bit is set to 0
  If the returned value (old value) was 1, it means the spot was booked successfully
   */
  @Override
  public ParkingTicket bookSpot(String parkingLotId, String entryTerminalId,
      String vehicleNo, VehicleType vehicleType, String spotId) throws BookingException {

    ParkingTicket ticket = ParkingTicket.generate(
        parkingLotId,
        entryTerminalId,
        spotId,
        vehicleNo,
        vehicleType
    );

    boolean booked = this.redisTemplate
        .opsForValue()
        .setBit(getParkingMapKey(parkingLotId), Long.parseLong(spotId), false);

    if (! booked) {
      throw new BookingException("Spot already booked");
    }

    this.redisTemplate
        .opsForHash()
        .putAll(getParkingTicketKey(ticket.getTicketId()),
            Map.of(
                "ticketId", ticket.getTicketId(),
                "parkingLotId", ticket.getParkingLotId(),
                "entryTerminalId", ticket.getEntryTerminalId(),
                "spotId", ticket.getSpotId(),
                "entryTime", ticket.getEntryTime(),
                "vehicleNo", ticket.getVehicleNo(),
                "vehicleType", ticket.getVehicleType().name()
            ));

    return ticket;
  }

  private String getParkingMapKey(String parkingLotId) {
    return "parkinglot" + ":" + parkingLotId;
  }

  private String getParkingTicketKey(String ticketNo) {
    return "parkingticket" + ":" + ticketNo;
  }

}
