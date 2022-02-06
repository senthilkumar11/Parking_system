package com.zoho.parking_system.service;

import com.zoho.parking_system.model.Slot;
import com.zoho.parking_system.request.model.ParkingRequest;
import com.zoho.parking_system.request.model.ParkingSystem;
import com.zoho.parking_system.request.model.UnParkRequest;
import com.zoho.parking_system.response.model.ParkingDetailResponse;

public interface ParkingService {
	boolean createParkingSystem(ParkingSystem parkingSystem);

	ParkingDetailResponse park(ParkingRequest parkingRequest);
	ParkingDetailResponse unPark(UnParkRequest unpark);
}
