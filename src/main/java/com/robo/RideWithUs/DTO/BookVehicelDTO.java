package com.robo.RideWithUs.DTO;

import com.robo.RideWithUs.Entity.Vehicle;

public class BookVehicelDTO {

	private long customerMobileNumber;
	private long driverMobileNumber;
	private double longitude;
	private double latitude;
	private Vehicle vehicle;
	
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
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
	
	public BookVehicelDTO(long customerMobileNumber, long driverMobileNumber, double longitude, double latitude,
			Vehicle vehicle) {
		super();
		this.customerMobileNumber = customerMobileNumber;
		this.driverMobileNumber = driverMobileNumber;
		this.longitude = longitude;
		this.latitude = latitude;
		this.vehicle = vehicle;
	}
	public BookVehicelDTO() {
		super();
	}
	
	
}
