package com.zoho.parking_system.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ParkingDetails {
	@Id
	@GeneratedValue
	private Integer id;
	@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
	private Slot slot;
	private String vehicleRegistrationNumber;
	private String customerName;
	private String phNumber;
	private Date entranceTime;
	private Date exitTime;
	private Boolean availabilty;
	@Enumerated(EnumType.STRING)
	private VehicleType vehicleType;
	private String parkingType;
	private Double fee;
	private boolean feeCollected;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Slot getSlot() {
		return slot;
	}
	public void setSlot(Slot slot) {
		this.slot = slot;
	}
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
	public Date getEntranceTime() {
		return entranceTime;
	}
	public void setEntranceTime(Date entranceTime) {
		this.entranceTime = entranceTime;
	}
	public Date getExitTime() {
		return exitTime;
	}
	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}
	public Boolean getAvailabilty() {
		return availabilty;
	}
	public void setAvailabilty(Boolean availabilty) {
		this.availabilty = availabilty;
	}
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getParkingType() {
		return parkingType;
	}
	public void setParkingType(String parkingType) {
		this.parkingType = parkingType;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public boolean isFeeCollected() {
		return feeCollected;
	}
	public void setFeeCollected(boolean feeCollected) {
		this.feeCollected = feeCollected;
	}
	
	
	
	
	
}
