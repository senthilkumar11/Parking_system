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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.zoho.parking_system.response.model.ErrorResponse;
import com.zoho.parking_system.response.model.HomeResponse;
import com.zoho.parking_system.response.model.SearchParkingResponse;
import com.zoho.parking_system.response.model.TransactionSummary;
import com.zoho.parking_system.service.ParkingService;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/parking")
public class ParkingController {
	@Autowired
	ParkingService parkingService;

	@GetMapping("/home")
	public ResponseEntity<HomeResponse> getAllDetails() {

		HomeResponse homeResponse = parkingService.getSystemDetails();
		if (homeResponse != null)
			return new ResponseEntity<HomeResponse>(homeResponse, HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<>(homeResponse, HttpStatus.NOT_FOUND);
	}

	@GetMapping("/transaction/{type}")
	public ResponseEntity<TransactionSummary> getTransaction(@PathVariable int type) {

		TransactionSummary transactionSummary = parkingService.getTransaction(VehicleType.values()[type]);
		if (transactionSummary != null)
			return new ResponseEntity<TransactionSummary>(transactionSummary, HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/system")
	public ResponseEntity<?> createParkingSystem(@RequestBody ParkingSystem parkingSystem) {

		if (parkingService.createParkingSystem(parkingSystem)) {
			return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
		} else
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/park")
	public ResponseEntity<?> parkVehicle(@RequestBody ParkingRequest parkingRequest) {
		try {
			ParkingDetails response = parkingService.park(parkingRequest);
			return new ResponseEntity<ParkingDetails>(response, HttpStatus.ACCEPTED);
		} catch (Exception e) {

			System.out.println(e.getMessage());
			ErrorResponse error = new ErrorResponse();
			error.setMessage(e.getMessage());
			error.setCode(406);
			return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);

		}
	}

	@PutMapping("/unPark/{id}")
	public ResponseEntity<ParkingDetails> unpark(@PathVariable int id) {

		ParkingDetails response = parkingService.unPark(id);
		return new ResponseEntity<ParkingDetails>(response, HttpStatus.ACCEPTED);

	}

	@PutMapping("/collectfee/{id}")
	public ResponseEntity<?> collectFee(@PathVariable int id) {

		try {
			ParkingDetails response = parkingService.collectFee(id);
			return new ResponseEntity<ParkingDetails>(response, HttpStatus.ACCEPTED);
		} catch (Exception e) {

			System.out.println(e.getMessage());
			ErrorResponse error = new ErrorResponse();
			error.setMessage(e.getMessage());
			error.setCode(406);
			return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@PostMapping("/search")
	public ResponseEntity<SearchParkingResponse> getParkingVehicleDetails(@RequestBody SearchRequest searchRequest) {
		SearchParkingResponse parkingDetails = parkingService.searchVehicleDetails(searchRequest);
		return new ResponseEntity<SearchParkingResponse>(parkingDetails, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ParkingDetails> getParkingDetails(@PathVariable int id) {

		Optional<ParkingDetails> response = parkingService.getParkingDetails(id);
		if (response.isPresent()) {
			return new ResponseEntity<ParkingDetails>(response.get(), HttpStatus.OK);
		}
		return new ResponseEntity<ParkingDetails>(HttpStatus.NOT_FOUND);

	}

	@GetMapping("/fee")
	public ResponseEntity<List<Fee>> getAllFee() {
		List<Fee> allFee = parkingService.getAllFee();
		if (allFee != null)
			return new ResponseEntity<List<Fee>>(allFee, HttpStatus.OK);

		return new ResponseEntity<List<Fee>>(HttpStatus.NOT_FOUND);

	}

	@PostMapping("/fee")
	public ResponseEntity<Fee> saveFee(@RequestBody Fee fee) {
		Fee response = parkingService.saveFee(fee);
		if (response != null)
			return new ResponseEntity<Fee>(response, HttpStatus.OK);

		return new ResponseEntity<Fee>(HttpStatus.NOT_FOUND);

	}

	@PostMapping("/coupon")
	public ResponseEntity<Boolean> generateCoupon(@RequestBody CouponRequest couponReq) {
		Boolean res = parkingService.createCoupon(couponReq);
		if (res) {
			return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
		} else
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/coupon/{page}/{pageSize}")
	public ResponseEntity<CouponResponse> getCouponDetails(@PathVariable int page, @PathVariable int pageSize,
			@RequestParam("searchData") String searchData) {

		CouponResponse response = parkingService.getCouponDetails(page, pageSize, searchData);
		return new ResponseEntity<CouponResponse>(response, HttpStatus.OK);

	}

	@PutMapping("coupon/apply/{id}")
	public ResponseEntity<ParkingDetails> applyDiscountCode(@RequestBody CouponRequest couponReq,
			@PathVariable("id") int id) {
		ParkingDetails res = parkingService.applyDiscountCode(couponReq, id);
		if (res != null) {
			return new ResponseEntity<ParkingDetails>(res, HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
