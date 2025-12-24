package com.robo.RideWithUs.DTO;

public class RideDetailDTO {

	private String sourceLocation;
	private String destinationLocation;
	private double distance;
	private double fare;
	
	public String getSourceLocation() {
		return sourceLocation;
	}
	public void setSourceLocation(String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	public String getDestinationLocation() {
		return destinationLocation;
	}
	public void setDestinationLocation(String destinationLocation) {
		this.destinationLocation = destinationLocation;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getFare() {
		return fare;
	}
	public void setFare(double fare) {
		this.fare = fare;
	}
	
	public RideDetailDTO(String sourceLocation, String destinationLocation, double distance, double fare) {
		super();
		this.sourceLocation = sourceLocation;
		this.destinationLocation = destinationLocation;
		this.distance = distance;
		this.fare = fare;
	}
	
	public RideDetailDTO() {
		super();
	}
	
	
	
}
