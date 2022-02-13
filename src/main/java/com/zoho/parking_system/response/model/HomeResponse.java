package com.zoho.parking_system.response.model;

public class HomeResponse {

	private long totalFoor;
	private long slotPerFloor;
	private long freeSlots;
	public long getTotalFoor() {
		return totalFoor;
	}
	public void setTotalFoor(long totalFoor) {
		this.totalFoor = totalFoor;
	}
	public long getSlotPerFloor() {
		return slotPerFloor;
	}
	public void setSlotPerFloor(long slotPerFloor) {
		this.slotPerFloor = slotPerFloor;
	}
	public long getFreeSlots() {
		return freeSlots;
	}
	public void setFreeSlots(long freeSlots) {
		this.freeSlots = freeSlots;
	}
	

	
}
