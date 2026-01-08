package com.robo.RideWithUs.DTO;

public class UpdateDriverVehicleLocationDTO {

	private double longitude;
	private double latitude;
	
	
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
	
	public UpdateDriverVehicleLocationDTO(double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public UpdateDriverVehicleLocationDTO() {
		super();
	}
	
	
}
