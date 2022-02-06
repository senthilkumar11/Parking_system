package com.zoho.parking_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zoho.parking_system.model.Slot;
import com.zoho.parking_system.request.model.ParkingRequest;
import com.zoho.parking_system.request.model.ParkingSystem;
import com.zoho.parking_system.request.model.UnParkRequest;
import com.zoho.parking_system.response.model.ParkingDetailResponse;
import com.zoho.parking_system.service.ParkingService;

@RestController("parking")
public class ParkingController {
	@Autowired
	ParkingService parkingService;
	
	@PostMapping("/system")
	public ResponseEntity<?> createParkingSystem(@RequestBody ParkingSystem parkingSystem){
		
		if(parkingService.createParkingSystem(parkingSystem)) {
			return new ResponseEntity<Boolean>(true,HttpStatus.CREATED);
		}else
		return new ResponseEntity<Boolean>(false,HttpStatus.BAD_REQUEST);
	}
	@PostMapping("/park")
	public ResponseEntity<?> parkVehicle(@RequestBody ParkingRequest parkingRequest){
		
		ParkingDetailResponse response=parkingService.park(parkingRequest);
		return new ResponseEntity<ParkingDetailResponse>(response,HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/unPark")
	public ResponseEntity<?> unpark(@RequestBody UnParkRequest unpark){
		
		ParkingDetailResponse response= parkingService.unPark(unpark);
		return new ResponseEntity<ParkingDetailResponse>(response,HttpStatus.ACCEPTED);
		
	}
}
