package com.example.sachinlld.ParkingLotLLD.services;

import com.example.sachinlld.ParkingLotLLD.entities.Vehicle;
import com.example.sachinlld.ParkingLotLLD.enums.VehicleType;

public interface ParkingSpot {
    boolean isEmpty();
    void park();
    void unPark();
    VehicleType getVehicleType();
}
