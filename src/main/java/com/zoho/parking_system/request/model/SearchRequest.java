package com.zoho.parking_system.request.model;

import com.zoho.parking_system.model.ParkingType;
import com.zoho.parking_system.model.VehicleType;

public class SearchRequest {

	private String vehicleRegistrationNumber;
	private String customerName;
	private VehicleType vehicleType;
	private Integer floor;
	private Integer slot;
	private Integer pageNumber;
	private Integer pageSize;
	private Boolean availabilty;
	private ParkingType parkingType;
	
	public String getVehicleRegistrationNumber() {
		return vehicleRegistrationNumber;
	}
	public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
		this.vehicleRegistrationNumber = vehicleRegistrationNumber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	public Integer getFloor() {
		return floor;
	}
	public void setFloor(Integer floor) {
		this.floor = floor;
	}
	public Integer getSlot() {
		return slot;
	}
	public void setSlot(Integer slot) {
		this.slot = slot;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Boolean getAvailabilty() {
		return availabilty;
	}
	public void setAvailabilty(Boolean availabilty) {
		this.availabilty = availabilty;
	}
	public ParkingType getParkingType() {
		return parkingType;
	}
	public void setParkingType(ParkingType parkingType) {
		this.parkingType = parkingType;
	}
	
	
	
	
}
