package com.robo.RideWithUs.DTO;

public class UpdateDriverVehicleLocationDTO {

	private double longitude;
	private double latitude;
	private long driverMobileNumber;
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
	
	public UpdateDriverVehicleLocationDTO(double longitude, double latitude, long driverMobileNumber) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.driverMobileNumber = driverMobileNumber;
	}
	public UpdateDriverVehicleLocationDTO() {
		super();
	}
	
	
}
