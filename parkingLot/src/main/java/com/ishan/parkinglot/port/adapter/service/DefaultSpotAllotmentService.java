package com.ishan.parkinglot.port.adapter.service;

import com.ishan.parkinglot.domain.BookingException;
import com.ishan.parkinglot.domain.ParkingTicket;
import com.ishan.parkinglot.domain.SpotAllotmentService;
import com.ishan.parkinglot.domain.VehicleType;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class DefaultSpotAllotmentService implements SpotAllotmentService {

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  @Override
  public void init(String parkingLotId) {
    Object parkingChart = this.redisTemplate.opsForValue().get(getParkingChartKey(parkingLotId));
    if (Objects.isNull(parkingChart)) {
      // Get all parking spots for this parking lot and create a redis bitmap
      int slots = 10; // todo

      this.redisTemplate
          .opsForValue()
          .bitField(
              getParkingChartKey(parkingLotId),
              BitFieldSubCommands.create()
              .set(BitFieldSubCommands.BitFieldType.unsigned(slots)).valueAt(0).to((long) (Math.pow(2, slots) - 1))
          );
    }
  }

  /*
  Initially all bits/spots are initialised to 1 (Available)
  To book a spot, it's bit is set to 0
  If the returned value (old value) was 1, it means the spot was booked successfully
   */
  @Override
  public ParkingTicket bookSpot(String parkingLotId, String vehicleNo,
      VehicleType vehicleType, String spotId) throws BookingException {

    ParkingTicket ticket = new ParkingTicket();
    String ticketId = UUID.randomUUID().toString();
    LocalDateTime entryTime = LocalDateTime.now();
    ticket.setTicketId(ticketId);
    ticket.setEntryTime(entryTime);
    ticket.setVehicleNo(vehicleNo);
    ticket.setVehicleType(vehicleType);

    boolean booked = this.redisTemplate
        .opsForValue()
        .setBit(getParkingChartKey(parkingLotId), Long.parseLong(spotId), false);

    if (! booked) {
      throw new BookingException();
    }

    this.redisTemplate
        .opsForHash()
        .putAll(getParkingTicketKey(ticketId),
            Map.of(
                "ticketNo", ticketId,
                "entryTime", entryTime, //todo - Format
                "vehicleNo", vehicleNo,
                "vehicleType", vehicleType.name()
            ));

    return ticket;
  }

  private String getParkingChartKey(String parkingLotId) {
    return "parkinglot" + ":" + parkingLotId;
  }

  private String getParkingTicketKey(String ticketNo) {
    return "parkingticket" + ":" + ticketNo;
  }

}
