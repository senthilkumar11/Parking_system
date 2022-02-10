package com.zoho.parking_system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
import com.zoho.parking_system.request.model.SearchRequest;
import com.zoho.parking_system.response.model.SearchParkingResponse;
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
	public ParkingDetails park(ParkingRequest parkingRequest) {
		SearchParkingResponse parkingDetailResponse=new SearchParkingResponse();
		VehicleType type=parkingRequest.getVehicleType();
		System.out.println(type);
		Slot aSlot=slotRepo.findNextAvailableSlot(type.toString());
		ParkingDetails parking=new ParkingDetails();
		if(aSlot==null)
		{
			
			System.out.println("Null");
			return null;
//			throw new Exception("ParkingFull");
		}else {
			aSlot.setAvailablity('P');
			parking.setAvailabilty(true);
			parking.setCustomerName(parkingRequest.getCustomerName());
			parking.setEntranceTime(new Date());
			parking.setParkingType("Parking");
			parking.setPhNumber(parkingRequest.getPhNumber());
			parking.setVehicleRegistrationNumber(parkingRequest.getVehicleRegistrationNumber());
			parking.setVehicleType(aSlot.getVehichleType());
			aSlot=slotRepo.save(aSlot);
			parking.setSlot(aSlot);		
			parking=parkingDetailsRepository.save(parking);
		}
		
		return parking;
	}

	@Override
	public ParkingDetails unPark(int id) {
		Optional<ParkingDetails> res=parkingDetailsRepository.findById(id);
		if(!res.isPresent())
		{
			return null;
		}
		ParkingDetails parkingDetails=res.get();
		Optional<Slot> resSolt=slotRepo.findById(parkingDetails.getSlot().getId());
		Slot slot= resSolt.get();
		parkingDetails.setExitTime(new Date());
		long duration= parkingDetails.getExitTime().getTime()-parkingDetails.getEntranceTime().getTime() ;
		
		long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
		double cost=40;
		System.out.println(diffInMinutes);
		if((diffInMinutes-60)>0) {
		cost+=Math.round(((diffInMinutes-60)/60.0f)*20);
		}
		parkingDetails.setFee(cost);
		
		parkingDetails.setParkingType("Collect Fee");
		slot=slotRepo.save(slot);
		parkingDetails.setSlot(slot);
		parkingDetails=parkingDetailsRepository.save(parkingDetails);

		
		return parkingDetails;
	}

	@Override
	public Optional<ParkingDetails> getParkingDetails(int id) {
		// TODO Auto-generated method stub
		
		return parkingDetailsRepository.findById(id);
	}

	@Override
	public ParkingDetails collectFee(int id) {
		Optional<ParkingDetails> res=parkingDetailsRepository.findById(id);
		if(!res.isPresent())
		{
			return null;
		}
		ParkingDetails parkingDetails=res.get();
		Optional<Slot> resSolt=slotRepo.findById(parkingDetails.getSlot().getId());
		Slot slot= resSolt.get();
		slot.setAvailablity('A');
		parkingDetails.setAvailabilty(false);
		parkingDetails.setParkingType("Unavailable");
		parkingDetails.setFeeCollected(true);
		slot=slotRepo.save(slot);
		parkingDetails.setSlot(slot);
		parkingDetails=parkingDetailsRepository.save(parkingDetails);
		return parkingDetails;
	}

	@Transactional
	@Override
	public SearchParkingResponse searchVehicleDetails(SearchRequest searchRequest) {
		// TODO Auto-generated method stub
		System.out.println(searchRequest.getVehicleType());
		Specification<ParkingDetails> spec=new Specification<ParkingDetails>() {
			
			@Override
			public Predicate toPredicate(Root<ParkingDetails> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				 List<Predicate> predicates = new ArrayList<>();
				 if(searchRequest.getAvailabilty()!=null)
				 predicates.add(criteriaBuilder.equal(root.get("availabilty"), searchRequest.getAvailabilty()));
				 if(searchRequest.getFloor()!=null) {
					 predicates.add(criteriaBuilder.equal(root.join("slot", JoinType.LEFT).get("floorId"),searchRequest.getFloor()));
				 }
				 if(searchRequest.getSlot()!=null) {
					 predicates.add(criteriaBuilder.equal(root.join	("slot", JoinType.LEFT).get("slotNumber"),searchRequest.getSlot()));
				 }
				 if(searchRequest.getCustomerName()!=null) {
					 predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("customerName")),"%"+searchRequest.getCustomerName().toLowerCase()+"%"));
				 }
				 if(searchRequest.getVehicleRegistrationNumber()!=null) {
					 predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("vehicleRegistrationNumber")),"%"+searchRequest.getVehicleRegistrationNumber().toLowerCase()+"%"));
				 }
				 if(searchRequest.getVehicleType()!=null) {
					 predicates.add(criteriaBuilder.equal(root.get("vehicleType"),searchRequest.getVehicleType()));
						
				 }
				 query.orderBy(criteriaBuilder.asc(root.join("slot", JoinType.LEFT).get("floorId")),criteriaBuilder.asc(root.join("slot", JoinType.LEFT).get("slotNumber")));
				 return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}
		};
		Pageable paging = PageRequest.of(searchRequest.getPageNumber() - 1, searchRequest.getPageSize());
		Page<ParkingDetails> parkingDetails=parkingDetailsRepository.findAll(spec,paging);
		SearchParkingResponse searchParkingResponse=new SearchParkingResponse();
		searchParkingResponse.setParkingDetails(parkingDetails.getContent());
		searchParkingResponse.setHasMore(!parkingDetails.isLast());
		searchParkingResponse.setPageSize(parkingDetails.getSize());
		searchParkingResponse.setPageTotal(parkingDetails.getTotalPages());
		searchParkingResponse.setTotal(parkingDetails.getTotalElements());
		searchParkingResponse.setPageNumber(parkingDetails.getNumber()+1);
//		List<ParkingDetails> parkingDetails=parkingDetailsRepository.findAll(spec);
//		SearchParkingResponse searchParkingResponse=new SearchParkingResponse();
//		searchParkingResponse.setParkingDetails(parkingDetails);
//		searchParkingResponse.setHasMore(!parkingDetails.isLast());
//		searchParkingResponse.setPageSize(parkingDetails.getSize());
//		searchParkingResponse.setPageTotal(parkingDetails.getTotalPages());
//		searchParkingResponse.setTotal(parkingDetails.getTotalElements());
		return searchParkingResponse;
	}

}
