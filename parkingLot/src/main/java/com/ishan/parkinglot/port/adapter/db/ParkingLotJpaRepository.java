package com.ishan.parkinglot.port.adapter.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotJpaRepository extends JpaRepository<ParkingLot, String> {

}
