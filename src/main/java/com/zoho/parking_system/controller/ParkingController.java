package com.zoho.parking_system.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zoho.parking_system.model.ParkingDetails;
import com.zoho.parking_system.model.Slot;
import com.zoho.parking_system.request.model.ParkingRequest;
import com.zoho.parking_system.request.model.ParkingSystem;
import com.zoho.parking_system.request.model.SearchRequest;
import com.zoho.parking_system.request.model.UnParkRequest;
import com.zoho.parking_system.response.model.SearchParkingResponse;
import com.zoho.parking_system.service.ParkingService;

@RestController
@CrossOrigin(value="*")
@RequestMapping("/parking")
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
		
		ParkingDetails response=parkingService.park(parkingRequest);
		return new ResponseEntity<ParkingDetails>(response,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/unPark/{id}")
	public ResponseEntity<?> unpark(@PathVariable int id){
		
		
		ParkingDetails response= parkingService.unPark(id);
		return new ResponseEntity<ParkingDetails>(response,HttpStatus.ACCEPTED);
		
	}
	@PutMapping("/collectfee/{id}")
	public ResponseEntity<?> collectFee(@PathVariable int id){
		
		
		ParkingDetails response= parkingService.collectFee(id);
		return new ResponseEntity<ParkingDetails>(response,HttpStatus.ACCEPTED);
		
	}
	@PostMapping("/search")
	public ResponseEntity<?> getParkingVehicleDetails(@RequestBody SearchRequest searchRequest){
		SearchParkingResponse parkingDetails= parkingService.searchVehicleDetails(searchRequest);
		return new ResponseEntity<SearchParkingResponse>(parkingDetails,HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getParkingDetails(@PathVariable int id){
		
		Optional<ParkingDetails> response=parkingService.getParkingDetails(id);
		if(response.isPresent()) {
			return new ResponseEntity<ParkingDetails>(response.get(),HttpStatus.OK);
		}
		return new ResponseEntity<ParkingDetails>(HttpStatus.NOT_FOUND);
		
	}
}
