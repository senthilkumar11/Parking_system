package com.zoho.parking_system.response.model;

import com.zoho.parking_system.model.ParkingDetails;
import com.zoho.parking_system.model.Slot;

public class ParkingDetailResponse {

	private Slot slot;
	private ParkingDetails parkingDetails;
	
	
	public Slot getSlot() {
		return slot;
	}
	public void setSlot(Slot slot) {
		this.slot = slot;
	}
	public ParkingDetails getParkingDetails() {
		return parkingDetails;
	}
	public void setParkingDetails(ParkingDetails parkingDetails) {
		this.parkingDetails = parkingDetails;
	}
	
	
}
