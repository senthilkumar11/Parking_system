package com.zoho.parking_system.model;

public enum ParkingType {
	PARKING('P'), RESERVE('R'), UNAVAILABLE('U'), AVAILABLE('A'),ALLTYPE('S');

	private Character value;

	private ParkingType(Character value) {
		this.value = value;
	}

	public Character getValue() {
		return value;
	}

	
}
