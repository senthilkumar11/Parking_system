package com.zoho.parking_system.service.impl;

import java.lang.StackWalker.Option;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.zoho.parking_system.model.DiscountCoupon;
import com.zoho.parking_system.model.Fee;
import com.zoho.parking_system.model.ParkingDetails;
import com.zoho.parking_system.model.ParkingLot;
import com.zoho.parking_system.model.ParkingType;
import com.zoho.parking_system.model.Slot;
import com.zoho.parking_system.model.VehicleType;
import com.zoho.parking_system.repo.DiscountCouponRepository;
import com.zoho.parking_system.repo.FeeRepository;
import com.zoho.parking_system.repo.ParkingDetailsRepository;
import com.zoho.parking_system.repo.ParkingLotRepository;
import com.zoho.parking_system.repo.SlotRepo;
import com.zoho.parking_system.request.model.CouponRequest;
import com.zoho.parking_system.request.model.ParkingRequest;
import com.zoho.parking_system.request.model.ParkingSystem;
import com.zoho.parking_system.request.model.SearchRequest;
import com.zoho.parking_system.response.model.CouponResponse;
import com.zoho.parking_system.response.model.HomeResponse;
import com.zoho.parking_system.response.model.SearchParkingResponse;
import com.zoho.parking_system.response.model.TransactionSummary;
import com.zoho.parking_system.service.ParkingService;

@Service
public class ParkingServiceImpl implements ParkingService {

	@Autowired
	ParkingLotRepository parkingLotRepository;

	@Autowired
	SlotRepo slotRepo;

	@Autowired
	ParkingDetailsRepository parkingDetailsRepository;

	@Autowired
	FeeRepository feeRepository;

	@Autowired
	DiscountCouponRepository couponRepo;

	@Override
	public boolean createParkingSystem(ParkingSystem parkingSystem) {
		List<ParkingLot> floors = new ArrayList<>();
		List<Slot> slots = new ArrayList<Slot>();
		for (int i = 0; i < parkingSystem.getFloor(); i++) {
			int parkingSpace = parkingSystem.getParkingSpace();

			int fourWheeler = (parkingSpace * 40) / 100;
			int heavyDuty = (parkingSpace * 20) / 100;
			int twoWheeler = parkingSpace - (fourWheeler + heavyDuty);

			int slotNumber = 1;
			for (int j = 0; j < fourWheeler; j++) {
				Slot newSlot = new Slot(i + 1, slotNumber, ParkingType.AVAILABLE, VehicleType.CAR);
				slots.add(newSlot);
				slotNumber++;
			}
			int heavyStartsFrom = slotNumber;
			for (int j = 0; j < heavyDuty; j++) {
				Slot newSlot = new Slot(i + 1, slotNumber, ParkingType.AVAILABLE, VehicleType.BUS);
				slots.add(newSlot);
				slotNumber++;
			}
			int twoWheelerStartsFrom = slotNumber;
			for (int j = 0; j < twoWheeler; j++) {
				Slot newSlot = new Slot(i + 1, slotNumber, ParkingType.AVAILABLE, VehicleType.BIKE);
				slots.add(newSlot);
				slotNumber++;
			}

			ParkingLot floor = new ParkingLot(i + 1, parkingSystem.getParkingSpace(), twoWheeler, fourWheeler,
					heavyDuty, twoWheeler, fourWheeler, heavyDuty, 1, heavyStartsFrom, twoWheelerStartsFrom);
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
	public ParkingDetails park(ParkingRequest parkingRequest) throws Exception {
		SearchParkingResponse parkingDetailResponse = new SearchParkingResponse();
		ParkingDetails existing=parkingDetailsRepository.findByVehichle(parkingRequest.getVehicleRegistrationNumber());
		if(existing!=null) {
			throw new Exception("Vehicle Already present");
		}
		VehicleType type = parkingRequest.getVehicleType();
		System.out.println(parkingRequest.getParkingType());
		System.out.println(type);
		Slot aSlot = slotRepo.findNextAvailableSlot(type.toString());
		ParkingDetails parking = new ParkingDetails();
		if (aSlot == null) {

			throw new Exception("Slot Full for "+type);
//			throw new Exception("ParkingFull");
		} else {
			aSlot.setAvailablity(parkingRequest.getParkingType());
			if (parkingRequest.getParkingType() == ParkingType.RESERVE) {
				parking.setFee(parkingRequest.getFee());
				parking.setFinalFee(parking.getFee());
				parking.setFeeCollected(true);
			}
			parking.setAvailabilty(true);
			parking.setCustomerName(parkingRequest.getCustomerName());
			parking.setEntranceTime(new Date());
			parking.setParkingType(parkingRequest.getParkingType());
			parking.setPhNumber(parkingRequest.getPhNumber());
			parking.setVehicleRegistrationNumber(parkingRequest.getVehicleRegistrationNumber());
			parking.setVehicleType(aSlot.getVehichleType());
			aSlot = slotRepo.save(aSlot);
			parking.setSlot(aSlot);
			parking = parkingDetailsRepository.save(parking);
		}

		return parking;
	}

	@Override
	public ParkingDetails unPark(int id) {
		Optional<ParkingDetails> res = parkingDetailsRepository.findById(id);
		if (!res.isPresent()) {
			return null;
		}
		ParkingDetails parkingDetails = res.get();
		Optional<Slot> resSolt = slotRepo.findById(parkingDetails.getSlot().getId());
		Slot slot = resSolt.get();
		parkingDetails.setExitTime(new Date());
		if (parkingDetails.getParkingType() != ParkingType.RESERVE) {
			long duration = parkingDetails.getExitTime().getTime() - parkingDetails.getEntranceTime().getTime();

			long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
			Optional<Fee> feeResponse = feeRepository.findById(parkingDetails.getVehicleType());
			if (feeResponse.isPresent()) {
				Fee fee = feeResponse.get();
				double cost = fee.getBasicFee();
				double remainingHourCost = 0;
				System.out.println(diffInMinutes);
				if ((diffInMinutes - 60) > 0) {
					remainingHourCost = Math.round(((diffInMinutes - 60) / 60.0f) * fee.getPerHourFee());
				}
				parkingDetails.setRemainingHourFee(remainingHourCost);
				parkingDetails.setBasicFee(cost);
				cost += remainingHourCost;
				parkingDetails.setFee(cost);
				parkingDetails.setFeeCollected(false);
				slot.setAvailablity(ParkingType.AVAILABLE);
			}
		}
		parkingDetails.setFinalFee(parkingDetails.getFee());

		parkingDetails.setAvailabilty(false);
		slot = slotRepo.save(slot);
		parkingDetails.setSlot(slot);
		parkingDetails = parkingDetailsRepository.save(parkingDetails);

		return parkingDetails;
	}

	@Override
	public Optional<ParkingDetails> getParkingDetails(int id) {
		// TODO Auto-generated method stub

		return parkingDetailsRepository.findById(id);
	}

	@Override
	public ParkingDetails collectFee(int id) throws Exception {
		Optional<ParkingDetails> res = parkingDetailsRepository.findById(id);
		if (!res.isPresent()) {
			return null;
		}
		ParkingDetails parkingDetails = res.get();
		Optional<Slot> resSolt = slotRepo.findById(parkingDetails.getSlot().getId());
		Slot slot = resSolt.get();
		slot.setAvailablity(ParkingType.AVAILABLE);
		parkingDetails.setAvailabilty(false);
		parkingDetails.setFeeCollected(true);
		DiscountCoupon coupon;
		if (parkingDetails.getCoupon() != null) {
			coupon = parkingDetails.getCoupon();
			if(!coupon.getUsed()) {
			coupon.setUsed(true);
			coupon.setUsedDate(new Date());
			couponRepo.save(coupon);
			}else {
				throw new Exception("Coupon already used please add new coupon");
			}
		}
		slot = slotRepo.save(slot);
		parkingDetails.setSlot(slot);
		parkingDetails = parkingDetailsRepository.save(parkingDetails);
		return parkingDetails;
	}

	@Transactional
	@Override
	public SearchParkingResponse searchVehicleDetails(SearchRequest searchRequest) {
		// TODO Auto-generated method stub
		System.out.println(searchRequest.getVehicleType());
		Specification<ParkingDetails> spec = new Specification<ParkingDetails>() {

			@Override
			public Predicate toPredicate(Root<ParkingDetails> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				List<Predicate> predicates = new ArrayList<>();
				if (searchRequest.getAvailabilty() != null)
					predicates.add(criteriaBuilder.equal(root.get("availabilty"), searchRequest.getAvailabilty()));
				if (searchRequest.getFloor() != null) {
					predicates.add(criteriaBuilder.equal(root.join("slot", JoinType.LEFT).get("floorId"),
							searchRequest.getFloor()));
				}
				if (searchRequest.getSlot() != null) {
					predicates.add(criteriaBuilder.equal(root.join("slot", JoinType.LEFT).get("slotNumber"),
							searchRequest.getSlot()));
				}
				if (searchRequest.getCustomerName() != null) {
					predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("customerName")),
							"%" + searchRequest.getCustomerName().toLowerCase() + "%"));
				}
				if (searchRequest.getVehicleRegistrationNumber() != null) {
					predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("vehicleRegistrationNumber")),
							"%" + searchRequest.getVehicleRegistrationNumber().toLowerCase() + "%"));
				}
				if (searchRequest.getVehicleType() != null) {
					predicates.add(criteriaBuilder.equal(root.get("vehicleType"), searchRequest.getVehicleType()));

				}
				if(searchRequest.getParkingType()!=null) {
					predicates.add(criteriaBuilder.equal(root.get("parkingType"), searchRequest.getParkingType()));

				}
				query.orderBy(criteriaBuilder.asc(root.join("slot", JoinType.LEFT).get("floorId")),
						criteriaBuilder.asc(root.join("slot", JoinType.LEFT).get("slotNumber")));
				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}
		};
		Pageable paging = PageRequest.of(searchRequest.getPageNumber() - 1, searchRequest.getPageSize());
		Page<ParkingDetails> parkingDetails = parkingDetailsRepository.findAll(spec, paging);
		SearchParkingResponse searchParkingResponse = new SearchParkingResponse();
		searchParkingResponse.setParkingDetails(parkingDetails.getContent());
		searchParkingResponse.setHasMore(!parkingDetails.isLast());
		searchParkingResponse.setPageSize(parkingDetails.getSize());
		searchParkingResponse.setPageTotal(parkingDetails.getTotalPages());
		searchParkingResponse.setTotal(parkingDetails.getTotalElements());
		searchParkingResponse.setPageNumber(parkingDetails.getNumber() + 1);
//		List<ParkingDetails> parkingDetails=parkingDetailsRepository.findAll(spec);
//		SearchParkingResponse searchParkingResponse=new SearchParkingResponse();
//		searchParkingResponse.setParkingDetails(parkingDetails);
//		searchParkingResponse.setHasMore(!parkingDetails.isLast());
//		searchParkingResponse.setPageSize(parkingDetails.getSize());
//		searchParkingResponse.setPageTotal(parkingDetails.getTotalPages());
//		searchParkingResponse.setTotal(parkingDetails.getTotalElements());
		return searchParkingResponse;
	}

	@Override
	public List<Fee> getAllFee() {
		// TODO Auto-generated method stub
		return feeRepository.findAll();
	}

	@Override
	public Fee saveFee(Fee fee) {
		// TODO Auto-generated method stub
		return feeRepository.save(fee);
	}

	@Override
	public Boolean createCoupon(CouponRequest couponReq) {
		// TODO Auto-generated method stub
		try {
			List<DiscountCoupon> coupons = new ArrayList<DiscountCoupon>();
			Date createdDate=new Date();
			for (int i = 0; i < couponReq.getCount(); i++) {
				String code = createRandomCode(8);
				coupons.add(
						new DiscountCoupon(couponReq.getBasicDiscount(), couponReq.getHourlyDiscount(), false, code,createdDate));
			}
			couponRepo.saveAll(coupons);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}

	public String createRandomCode(int codeLength) {
		char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new SecureRandom();
		for (int i = 0; i < codeLength; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		Optional<DiscountCoupon> discount = couponRepo.findByCoupon(output);
		if (discount.isEmpty())
			return output;
		else
			return createRandomCode(codeLength);
	}

	@Override
	public CouponResponse getCouponDetails(int page, int pageSize, String searchData) {
		Specification<DiscountCoupon> spec = new Specification<DiscountCoupon>() {

			@Override
			public Predicate toPredicate(Root<DiscountCoupon> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (searchData != null) {
					predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("coupon")),
							"%" + searchData.toLowerCase() + "%"));
				}
				query.orderBy(criteriaBuilder.asc(root.get("used")),criteriaBuilder.desc(root.get("createdDate")));
				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}
		};
		Pageable paging = PageRequest.of(page - 1, pageSize);
		Page<DiscountCoupon> couponDetails = couponRepo.findAll(spec, paging);
		CouponResponse couponResponse = new CouponResponse();
		couponResponse.setCouponDetails(couponDetails.getContent());
		couponResponse.setHasMore(!couponDetails.isLast());
		couponResponse.setPageSize(couponDetails.getSize());
		couponResponse.setPageTotal(couponDetails.getTotalPages());
		couponResponse.setTotal(couponDetails.getTotalElements());
		couponResponse.setPageNumber(couponDetails.getNumber() + 1);
		return couponResponse;
	}

	@Override
	public ParkingDetails applyDiscountCode(CouponRequest couponReq, int id) {
		// TODO Auto-generated method stub
		Optional<DiscountCoupon> couponRes = couponRepo.findByCoupon(couponReq.getCode());
		if (couponRes.isPresent()) {
			DiscountCoupon coupon = couponRes.get();
			if (!coupon.getUsed()) {
				Optional<ParkingDetails> parkingdetailsRes = parkingDetailsRepository.findById(id);
				if (parkingdetailsRes.isPresent()) {
					ParkingDetails parkingDetails = parkingdetailsRes.get();
					double basicDiscount = (parkingDetails.getBasicFee() * coupon.getBasicDiscount()) / 100.0;
					double remainingHourDiscount = (parkingDetails.getRemainingHourFee() * coupon.getHourlyDiscount())
							/ 100.0;
					parkingDetails.setDiscount(((Math.round((basicDiscount + remainingHourDiscount) * 100)) / 100.0));
					parkingDetails.setCoupon(coupon);
					parkingDetails.setFinalFee(parkingDetails.getFee() - parkingDetails.getDiscount());
					parkingDetailsRepository.save(parkingDetails);
					return parkingDetails;
				}
			}
			return null;
		}
		return null;

	}

	@Override
	public HomeResponse getSystemDetails() {
		try {
			HomeResponse homeResponse=new HomeResponse();
		homeResponse.setTotalFoor( parkingLotRepository.count());
		homeResponse.setSlotPerFloor(parkingLotRepository.findSlotCount());
		
		homeResponse.setFreeSlots(slotRepo.getAllFreeSlots());
		return homeResponse;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}

	@Override
	public TransactionSummary getTransaction(VehicleType type) {
		TransactionSummary transaction=new TransactionSummary();
		try {
			transaction.setTotalSlot(slotRepo.countTotalSlot(type.toString()));
			transaction.setAvailableSlot(slotRepo.countAvailableSlot(type.toString()));
			transaction.setTotalAmountCollected(parkingDetailsRepository.sumAllFee(type.toString()));
			transaction.setTotalTransactionCompleted(parkingDetailsRepository.countAllCompletedTransaction(type.toString()));
			return transaction;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
