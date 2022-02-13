package com.zoho.parking_system.request.model;

import java.util.Date;

import com.zoho.parking_system.model.ParkingType;
import com.zoho.parking_system.model.VehicleType;

public class ParkingRequest {
	private String vehicleRegistrationNumber;
	private String customerName;
	private String phNumber;
	private VehicleType vehicleType;
	private ParkingType parkingType;
	private double fee;
	
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
	public String getPhNumber() {
		return phNumber;
	}
	public void setPhNumber(String phNumber) {
		this.phNumber = phNumber;
	}
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	public ParkingType getParkingType() {
		return parkingType;
	}
	public void setParkingType(ParkingType parkingType) {
		this.parkingType = parkingType;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	
	
}
