package com.zoho.parking_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.zoho.parking_system.model.Fee;
import com.zoho.parking_system.model.ParkingDetails;
import com.zoho.parking_system.model.Slot;
import com.zoho.parking_system.model.VehicleType;
import com.zoho.parking_system.request.model.CouponRequest;
import com.zoho.parking_system.request.model.ParkingRequest;
import com.zoho.parking_system.request.model.ParkingSystem;
import com.zoho.parking_system.request.model.SearchRequest;
import com.zoho.parking_system.request.model.UnParkRequest;
import com.zoho.parking_system.response.model.CouponResponse;
import com.zoho.parking_system.response.model.HomeResponse;
import com.zoho.parking_system.response.model.SearchParkingResponse;
import com.zoho.parking_system.response.model.TransactionSummary;

public interface ParkingService {
	boolean createParkingSystem(ParkingSystem parkingSystem);

	ParkingDetails park(ParkingRequest parkingRequest) throws Exception;
	ParkingDetails unPark(int id);

	Optional<ParkingDetails> getParkingDetails(int id);

	ParkingDetails collectFee(int id);

	SearchParkingResponse searchVehicleDetails(SearchRequest searchRequest);

	List<Fee> getAllFee();

	Fee saveFee(Fee fee);

	Boolean createCoupon(CouponRequest couponReq);

	CouponResponse getCouponDetails(int page, int pageSize, String searchData);

	ParkingDetails applyDiscountCode(CouponRequest couponReq, int id);

	HomeResponse getSystemDetails();

	TransactionSummary getTransaction(VehicleType type);
}
