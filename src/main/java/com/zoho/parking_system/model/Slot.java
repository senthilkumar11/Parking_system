package com.zoho.parking_system.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Slot {
	@Id
	@GeneratedValue
	private Integer id;
	private Integer floorId;
	private Integer slotNumber;
	private Character availablity;
	@Enumerated(EnumType.STRING)
	private VehicleType vehichleType;
	
	
	public Slot() {
		super();
	}
	public Slot(Integer floorId, Integer spotNumber, Character availablity, VehicleType vehichleType) {
		super();
		this.floorId = floorId;
		this.slotNumber = spotNumber;
		this.availablity = availablity;
		this.vehichleType = vehichleType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getFloorId() {
		return floorId;
	}
	public void setFloorId(Integer floorId) {
		this.floorId = floorId;
	}
	public Integer getSlotNumber() {
		return slotNumber;
	}
	public void setSlotNumber(Integer slotNumber) {
		this.slotNumber = slotNumber;
	}
	public Character getAvailablity() {
		return availablity;
	}
	public void setAvailablity(Character availablity) {
		this.availablity = availablity;
	}
	public VehicleType getVehichleType() {
		return vehichleType;
	}
	public void setVehichleType(VehicleType vehichleType) {
		this.vehichleType = vehichleType;
	} 
	
	
	
	
}
