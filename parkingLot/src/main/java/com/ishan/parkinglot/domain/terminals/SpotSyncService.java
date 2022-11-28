package com.ishan.parkinglot.domain.terminals;

import com.ishan.parkinglot.domain.SpotBooked;
import com.ishan.parkinglot.domain.SpotReleased;

public interface SpotSyncService {

  void publishSpotBooked(SpotBooked spotBooked);

  void handleSpotBooked(SpotBooked spotBooked);

  void publishSpotReleased(SpotReleased spotReleased);

  void handleSpotReleased(SpotReleased spotReleased);

}
