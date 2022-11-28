package com.ishan.parkinglot.port.adapter.db;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, String> {

  List<Spot> findAllByParkingLotId(String parkingLotId);

  int countByParkingLotId(String parkingLotId);

}
