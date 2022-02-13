package com.zoho.parking_system.model;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Fee {
	@Id
	@Enumerated(EnumType.STRING)
	private VehicleType vehicleType;
	private double basicFee;
	private double perHourFee;
	
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	public double getBasicFee() {
		return basicFee;
	}
	public void setBasicFee(double basicFee) {
		this.basicFee = basicFee;
	}
	public double getPerHourFee() {
		return perHourFee;
	}
	public void setPerHourFee(double perHourFee) {
		this.perHourFee = perHourFee;
	}
	public Fee(VehicleType vehicleType, double basicFee, double perHourFee) {
		super();
		this.vehicleType = vehicleType;
		this.basicFee = basicFee;
		this.perHourFee = perHourFee;
	}
	public Fee() {
		super();
	}
	
	
}
