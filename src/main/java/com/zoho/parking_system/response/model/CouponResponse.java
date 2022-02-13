package com.zoho.parking_system.response.model;

import java.util.List;

import com.zoho.parking_system.model.DiscountCoupon;

public class CouponResponse {
	private List<DiscountCoupon> couponDetails;
	private Integer pageTotal;
	private Integer pageSize;
	private Long total;
	private Boolean hasMore;
	private Integer pageNumber;
	public List<DiscountCoupon> getCouponDetails() {
		return couponDetails;
	}
	public void setCouponDetails(List<DiscountCoupon> couponDetails) {
		this.couponDetails = couponDetails;
	}
	public Integer getPageTotal() {
		return pageTotal;
	}
	public void setPageTotal(Integer pageTotal) {
		this.pageTotal = pageTotal;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Boolean getHasMore() {
		return hasMore;
	}
	public void setHasMore(Boolean hasMore) {
		this.hasMore = hasMore;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	
}
