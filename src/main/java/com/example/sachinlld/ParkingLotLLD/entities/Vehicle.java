package com.example.sachinlld.ParkingLotLLD.entities;

import com.example.sachinlld.ParkingLotLLD.enums.VehicleType;
import lombok.Data;

@Data
public class Vehicle {
    private String licensePlate;
    private VehicleType vehicleType;
}
