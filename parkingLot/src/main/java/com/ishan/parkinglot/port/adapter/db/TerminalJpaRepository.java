package com.ishan.parkinglot.port.adapter.db;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminalJpaRepository extends JpaRepository<Terminal, String> {

  List<Terminal> findAllByParkingLotId(String parkingLotId);

}
