package com.zoho.parking_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.zoho.parking_system.model.ParkingDetails;
import com.zoho.parking_system.model.Slot;
import com.zoho.parking_system.request.model.ParkingRequest;
import com.zoho.parking_system.request.model.ParkingSystem;
import com.zoho.parking_system.request.model.SearchRequest;
import com.zoho.parking_system.request.model.UnParkRequest;
import com.zoho.parking_system.response.model.SearchParkingResponse;

public interface ParkingService {
	boolean createParkingSystem(ParkingSystem parkingSystem);

	ParkingDetails park(ParkingRequest parkingRequest);
	ParkingDetails unPark(int id);

	Optional<ParkingDetails> getParkingDetails(int id);

	ParkingDetails collectFee(int id);

	SearchParkingResponse searchVehicleDetails(SearchRequest searchRequest);
}
