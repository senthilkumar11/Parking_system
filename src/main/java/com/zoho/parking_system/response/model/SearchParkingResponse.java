package com.zoho.parking_system.response.model;

import java.util.List;

import com.zoho.parking_system.model.ParkingDetails;
import com.zoho.parking_system.model.Slot;

public class SearchParkingResponse {


	private List<ParkingDetails> parkingDetails;
	private Integer pageTotal;
	private Integer pageSize;
	private Long total;
	private Boolean hasMore;
	private Integer pageNumber;
	

	
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public List<ParkingDetails> getParkingDetails() {
		return parkingDetails;
	}
	public void setParkingDetails(List<ParkingDetails> parkingDetails) {
		this.parkingDetails = parkingDetails;
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
	
	
}
