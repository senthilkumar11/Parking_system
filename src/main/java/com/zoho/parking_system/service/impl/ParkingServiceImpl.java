package com.zoho.parking_system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoho.parking_system.model.ParkingDetails;
import com.zoho.parking_system.model.ParkingLot;
import com.zoho.parking_system.model.Slot;
import com.zoho.parking_system.model.VehicleType;
import com.zoho.parking_system.repo.ParkingDetailsRepository;
import com.zoho.parking_system.repo.ParkingLotRepository;
import com.zoho.parking_system.repo.SlotRepo;
import com.zoho.parking_system.request.model.ParkingRequest;
import com.zoho.parking_system.request.model.ParkingSystem;
import com.zoho.parking_system.request.model.UnParkRequest;
import com.zoho.parking_system.response.model.ParkingDetailResponse;
import com.zoho.parking_system.service.ParkingService;

@Service
public class ParkingServiceImpl implements ParkingService {

	@Autowired
	ParkingLotRepository parkingLotRepository;
	
	@Autowired
	SlotRepo slotRepo;
	
	@Autowired
	ParkingDetailsRepository parkingDetailsRepository;

	@Override
	public boolean createParkingSystem(ParkingSystem parkingSystem) {
		List<ParkingLot> floors = new ArrayList<>();
		List<Slot> slots = new ArrayList<Slot>();
		for (int i = 0; i < parkingSystem.getFloor(); i++) {
			int parkingSpace = parkingSystem.getParkingSpace();

			int fourWheeler = (parkingSpace * 40) / 100;
			int heavyDuty = (parkingSpace * 20) / 100;
			int twoWheeler = parkingSpace - (fourWheeler + heavyDuty);

			int slotNumber=1;
			for (int j = 0; j < fourWheeler; j++) {
				Slot newSlot=new Slot(i+1, slotNumber, 'A', VehicleType.CAR);
				slots.add(newSlot);
				slotNumber++;
			}
			int heavyStartsFrom=slotNumber;
			for (int j = 0; j < heavyDuty; j++) {
				Slot newSlot=new Slot(i+1, slotNumber, 'A', VehicleType.BUS);
				slots.add(newSlot);
				slotNumber++;
			}
			int twoWheelerStartsFrom=slotNumber;
			for (int j = 0; j < twoWheeler; j++) {
				Slot newSlot=new Slot(i+1, slotNumber, 'A', VehicleType.BIKE);
				slots.add(newSlot);
				slotNumber++;
			}

			ParkingLot floor = new ParkingLot(i + 1, parkingSystem.getParkingSpace(), twoWheeler, fourWheeler,
					heavyDuty, twoWheeler, fourWheeler, heavyDuty,1,heavyStartsFrom,twoWheelerStartsFrom);
			floors.add(floor);
		}
		try {
			parkingLotRepository.saveAll(floors);
			slotRepo.saveAll(slots);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public ParkingDetailResponse park(ParkingRequest parkingRequest) {
		ParkingDetailResponse parkingDetailResponse=new ParkingDetailResponse();
		VehicleType type=parkingRequest.getVehicleType();
		System.out.println(type);
		Slot aSlot=slotRepo.findNextAvailableSlot(type.ordinal());
		ParkingDetailResponse response=new ParkingDetailResponse();
		if(aSlot==null)
		{
			System.out.println("Null");
//			throw new Exception("ParkingFull");
		}else {
			aSlot.setAvailablity('P');
			ParkingDetails parking=new ParkingDetails();
			parking.setAvailabilty(true);
			parking.setCustomerName(parkingRequest.getCustomerName());
			parking.setEntranceTime(new Date());
			parking.setParkingType("Parking");
			parking.setPhNumber(parkingRequest.getPhNumber());
			parking.setVehicleRegistrationNumber(parkingRequest.getVehicleRegistrationNumber());
			parking.setVehicleType(aSlot.getVehichleType());
			parking.setSlot(aSlot.getId());		
			aSlot=slotRepo.save(aSlot);
			parking=parkingDetailsRepository.save(parking);
			response.setSlot(aSlot);
			response.setParkingDetails(parking);
		}
		
		return response;
	}

	@Override
	public ParkingDetailResponse unPark(UnParkRequest unpark) {
		ParkingDetails parkingDetails=parkingDetailsRepository.findByVehichle(unpark.getVehicleNum());
		if(parkingDetails==null)
		{
			return null;
		}
		Optional<Slot> resSolt=slotRepo.findById(parkingDetails.getSlot());
		Slot slot= resSolt.get();
		slot.setAvailablity('A');
		parkingDetails.setExitTime(new Date());
		parkingDetails.setAvailabilty(false);
		parkingDetails.setParkingType("Unavailable");
		parkingDetails=parkingDetailsRepository.save(parkingDetails);
		slot=slotRepo.save(slot);
		ParkingDetailResponse response=new ParkingDetailResponse();
		response.setParkingDetails(parkingDetails);
		response.setSlot(slot);
		return response;
	}

}
