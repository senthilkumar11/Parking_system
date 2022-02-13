package com.zoho.parking_system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class DiscountCoupon {

	@Id
	@GeneratedValue
	private Integer id;
	private Double basicDiscount;
	private Double hourlyDiscount;
	private Boolean used;
	@Column(unique = true)
	private String coupon;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getBasicDiscount() {
		return basicDiscount;
	}
	public void setBasicDiscount(Double basicDiscount) {
		this.basicDiscount = basicDiscount;
	}
	public Double getHourlyDiscount() {
		return hourlyDiscount;
	}
	public void setHourlyDiscount(Double hourlyDiscount) {
		this.hourlyDiscount = hourlyDiscount;
	}
	public Boolean getUsed() {
		return used;
	}
	public void setUsed(Boolean used) {
		this.used = used;
	}
	public String getCoupon() {
		return coupon;
	}
	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}
	public DiscountCoupon(Double basicDiscount, Double hourlyDiscount, Boolean used, String coupon) {
		super();
		this.basicDiscount = basicDiscount;
		this.hourlyDiscount = hourlyDiscount;
		this.used = used;
		this.coupon = coupon;
	}
	public DiscountCoupon() {
		super();
	}
		
	
}
