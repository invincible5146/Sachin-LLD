package com.example.sachinlld.ParkingLotLLD.services;

import com.example.sachinlld.ParkingLotLLD.enums.VehicleType;
import org.springframework.stereotype.Service;

@Service
public class TwoWheelerParkingSpot implements ParkingSpot {
    private boolean isEmpty = true;
    @Override
    public boolean isEmpty() {
        return isEmpty;
    }

    @Override
    public void park() {
        isEmpty = false;
    }

    @Override
    public void unPark() {
        isEmpty = true;
    }

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.TWO_WHEELER;
    }
}
