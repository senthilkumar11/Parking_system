package com.zoho.parking_system.request.model;

public class CouponRequest {
	private double basicDiscount;
	private double hourlyDiscount;
	private Integer count;
	private String code;
	
	public double getBasicDiscount() {
		return basicDiscount;
	}
	public void setBasicDiscount(double basicDiscount) {
		this.basicDiscount = basicDiscount;
	}
	public double getHourlyDiscount() {
		return hourlyDiscount;
	}
	public void setHourlyDiscount(double hourlyDiscount) {
		this.hourlyDiscount = hourlyDiscount;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
