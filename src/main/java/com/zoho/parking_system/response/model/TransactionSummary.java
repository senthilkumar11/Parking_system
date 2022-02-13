package com.zoho.parking_system.response.model;

public class TransactionSummary {
	private long totalTransactionCompleted;
	private double totalAmountCollected;
	private long availableSlot;
	private long totalSlot;
	public long getTotalTransactionCompleted() {
		return totalTransactionCompleted;
	}
	public void setTotalTransactionCompleted(long totalTransactionCompleted) {
		this.totalTransactionCompleted = totalTransactionCompleted;
	}
	public double getTotalAmountCollected() {
		return totalAmountCollected;
	}
	public void setTotalAmountCollected(double totalAmountCollected) {
		this.totalAmountCollected = totalAmountCollected;
	}
	public long getAvailableSlot() {
		return availableSlot;
	}
	public void setAvailableSlot(long availableSlot) {
		this.availableSlot = availableSlot;
	}
	public long getTotalSlot() {
		return totalSlot;
	}
	public void setTotalSlot(long totalSlot) {
		this.totalSlot = totalSlot;
	}
	

}
