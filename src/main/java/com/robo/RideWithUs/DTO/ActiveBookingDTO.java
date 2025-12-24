package com.robo.RideWithUs.DTO;

import com.robo.RideWithUs.Entity.Bookings;

public class ActiveBookingDTO {

	private String customerName;
	private long customerMobileNo;
	private Bookings bookings;
	private String currentLocation;
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public long getCustomerMobileNo() {
		return customerMobileNo;
	}
	public void setCustomerMobileNo(long customerMobileNo) {
		this.customerMobileNo = customerMobileNo;
	}
	public Bookings getBookings() {
		return bookings;
	}
	public void setBookings(Bookings bookings) {
		this.bookings = bookings;
	}
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	public ActiveBookingDTO(String customerName, long customerMobileNo, Bookings bookings, String currentLocation) {
		super();
		this.customerName = customerName;
		this.customerMobileNo = customerMobileNo;
		this.bookings = bookings;
		this.currentLocation = currentLocation;
	}
	public ActiveBookingDTO() {
		super();
	}
	
	
}
