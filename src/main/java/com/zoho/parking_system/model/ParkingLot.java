package com.zoho.parking_system.model;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ParkingLot {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(unique = true)
	private Integer floorId;
	@Column
	private Integer parkingSpace;
	@Column
	private Integer twoWheeler;
	@Column
	private Integer fourWheeler;
	@Column
	private Integer heavyDuty; 
	@Column
	private Integer twoWheelerAvailable;
	@Column
	private Integer fourWheelerAvailable;
	@Column
	private Integer heavyDutyAvailable;
	@Column
	private Integer twoWheelerFrom;
	@Column
	private Integer fourWheelerFrom;
	@Column
	private Integer heavyDutyFrom;
	
	public ParkingLot() {
		super();
	}
	
	
	public ParkingLot(Integer floorId, Integer parkingSpace, Integer twoWheeler, Integer fourWheeler, Integer heavyDuty,
			Integer twoWheelerAvailable, Integer fourWheelerAvailable, Integer heavyDutyAvailable,
			 Integer fourWheelerFrom, Integer heavyDutyFrom,Integer twoWheelerFrom) {
		super();
		this.floorId = floorId;
		this.parkingSpace = parkingSpace;
		this.twoWheeler = twoWheeler;
		this.fourWheeler = fourWheeler;
		this.heavyDuty = heavyDuty;
		this.twoWheelerAvailable = twoWheelerAvailable;
		this.fourWheelerAvailable = fourWheelerAvailable;
		this.heavyDutyAvailable = heavyDutyAvailable;
		this.twoWheelerFrom = twoWheelerFrom;
		this.fourWheelerFrom = fourWheelerFrom;
		this.heavyDutyFrom = heavyDutyFrom;
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


	public Integer getParkingSpace() {
		return parkingSpace;
	}
	public void setParkingSpace(Integer parkingSpace) {
		this.parkingSpace = parkingSpace;
	}
	public Integer getTwoWheeler() {
		return twoWheeler;
	}
	public void setTwoWheeler(Integer twoWheeler) {
		this.twoWheeler = twoWheeler;
	}
	public Integer getFourWheeler() {
		return fourWheeler;
	}
	public void setFourWheeler(Integer fourWheeler) {
		this.fourWheeler = fourWheeler;
	}
	public Integer getHeavyDuty() {
		return heavyDuty;
	}
	public void setHeavyDuty(Integer heavyDuty) {
		this.heavyDuty = heavyDuty;
	}
	public Integer getParkingSpaceAvailable() {
		return twoWheelerAvailable+fourWheelerAvailable+heavyDutyAvailable;
	}
	
	public Integer getTwoWheelerAvailable() {
		return twoWheelerAvailable;
	}
	public void setTwoWheelerAvailable(Integer twoWheelerAvailable) {
		this.twoWheelerAvailable = twoWheelerAvailable;
	}
	public Integer getFourWheelerAvailable() {
		return fourWheelerAvailable;
	}
	public void setFourWheelerAvailable(Integer fourWheelerAvailable) {
		this.fourWheelerAvailable = fourWheelerAvailable;
	}
	public Integer getHeavyDutyAvailable() {
		return heavyDutyAvailable;
	}
	public void setHeavyDutyAvailable(Integer heavyDutyAvailable) {
		this.heavyDutyAvailable = heavyDutyAvailable;
	}

	public Integer getTwoWheelerFrom() {
		return twoWheelerFrom;
	}

	public void setTwoWheelerFrom(Integer twoWheelerFrom) {
		this.twoWheelerFrom = twoWheelerFrom;
	}

	public Integer getFourWheelerFrom() {
		return fourWheelerFrom;
	}

	public void setFourWheelerFrom(Integer fourWheelerFrom) {
		this.fourWheelerFrom = fourWheelerFrom;
	}

	public Integer getHeavyDutyFrom() {
		return heavyDutyFrom;
	}

	public void setHeavyDutyFrom(Integer heavyDutyFrom) {
		this.heavyDutyFrom = heavyDutyFrom;
	} 
	
}
