package com.robo.RideWithUs.DTO;

public class LocationDto {

	private long lattitude;
	private long longitude;
	public long getLattitude() {
		return lattitude;
	}
	public void setLattitude(long lattitude) {
		this.lattitude = lattitude;
	}
	public long getLongitude() {
		return longitude;
	}
	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}
	@Override
	public String toString() {
		return "LocationDto [lattitude=" + lattitude + ", longitude=" + longitude + "]";
	}
	public LocationDto(long lattitude, long longitude) {
		super();
		this.lattitude = lattitude;
		this.longitude = longitude;
	}
	public LocationDto() {
		super();
	}
	
	
	
}
