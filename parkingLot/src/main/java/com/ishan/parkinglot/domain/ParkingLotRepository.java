package com.ishan.parkinglot.domain;

import java.util.Optional;

public interface ParkingLotRepository {

  Optional<ParkingLot> getParkingLot(String parkingLotId);

}
