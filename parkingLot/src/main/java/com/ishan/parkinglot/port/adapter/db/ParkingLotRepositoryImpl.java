package com.ishan.parkinglot.port.adapter.db;

import com.ishan.parkinglot.domain.ParkingLot;
import com.ishan.parkinglot.domain.ParkingLotRepository;
import com.ishan.parkinglot.port.adapter.service.DefaultSpotAllotmentService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ParkingLotRepositoryImpl implements ParkingLotRepository {

  @Autowired
  private ParkingLotJpaRepository parkingLotJpaRepository;

  @Autowired
  private DefaultSpotAllotmentService defaultSpotAllotmentService;

  @Override
  public Optional<ParkingLot> getParkingLot(String parkingLotId) {
    return this.parkingLotJpaRepository.findById(parkingLotId)
        .map(pl -> new ParkingLot(pl.getId(), this.defaultSpotAllotmentService));
  }

}
