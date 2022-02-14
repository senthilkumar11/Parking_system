package com.zoho.parking_system.response.model;

import com.zoho.parking_system.model.VehicleType;

public class TransactionSummary {
	private Long totalTransactionCompleted;
	private Double totalAmountCollected;
	private Long availableSlot;
	private Long totalSlot;
	private VehicleType vehicleType;
	
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	public Long getTotalTransactionCompleted() {
		return totalTransactionCompleted;
	}
	public void setTotalTransactionCompleted(Long totalTransactionCompleted) {
		this.totalTransactionCompleted = totalTransactionCompleted;
	}
	public Double getTotalAmountCollected() {
		return totalAmountCollected;
	}
	public void setTotalAmountCollected(Double totalAmountCollected) {
		this.totalAmountCollected = totalAmountCollected;
	}
	public Long getAvailableSlot() {
		return availableSlot;
	}
	public void setAvailableSlot(Long availableSlot) {
		this.availableSlot = availableSlot;
	}
	public Long getTotalSlot() {
		return totalSlot;
	}
	public void setTotalSlot(Long totalSlot) {
		this.totalSlot = totalSlot;
	}
	

}
