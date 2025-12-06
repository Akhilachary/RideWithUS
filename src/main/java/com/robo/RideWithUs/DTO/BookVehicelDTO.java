package com.robo.RideWithUs.DTO;

public class BookVehicelDTO {

	private long customerMobileNumber;
	private long driverMobileNumber;
	private double longitude;
	private double latitude;
	public long getCustomerMobileNumber() {
		return customerMobileNumber;
	}
	public void setCustomerMobileNumber(long customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
	}
	public long getDriverMobileNumber() {
		return driverMobileNumber;
	}
	public void setDriverMobileNumber(long driverMobileNumber) {
		this.driverMobileNumber = driverMobileNumber;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public BookVehicelDTO(long customerMobileNumber, long driverMobileNumber, double longitude, double latitude) {
		super();
		this.customerMobileNumber = customerMobileNumber;
		this.driverMobileNumber = driverMobileNumber;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public BookVehicelDTO() {
		super();
	}
	
	
}
