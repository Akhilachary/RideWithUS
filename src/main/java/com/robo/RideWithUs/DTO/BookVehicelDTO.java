package com.robo.RideWithUs.DTO;

<<<<<<< HEAD
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
=======

public class BookVehicelDTO {

	private int vehicleID;
	private String sourceLocation;
	private String destinationLocation;
	private int fare;
	private int estiamtedTime;
	private int distanceTravelled;
	
	
	public int getVehicleID() {
		return vehicleID;
>>>>>>> main
	}
	public void setVehicleID(int vehicleID) {
		this.vehicleID = vehicleID;
	}
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
	public int getFare() {
		return fare;
	}
	public void setFare(int fare) {
		this.fare = fare;
	}
<<<<<<< HEAD
	
	public BookVehicelDTO(long customerMobileNumber, long driverMobileNumber, double longitude, double latitude,
			Vehicle vehicle) {
		super();
		this.customerMobileNumber = customerMobileNumber;
		this.driverMobileNumber = driverMobileNumber;
		this.longitude = longitude;
		this.latitude = latitude;
		this.vehicle = vehicle;
=======
	public int getEstiamtedTime() {
		return estiamtedTime;
	}
	public void setEstiamtedTime(int estiamtedTime) {
		this.estiamtedTime = estiamtedTime;
	}
	public int getDistanceTravelled() {
		return distanceTravelled;
	}
	public void setDistanceTravelled(int distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
	}
	
	public BookVehicelDTO(int vehicleID, String sourceLocation, String destinationLocation, int fare, int estiamtedTime, int distanceTravelled) {
		super();
		this.vehicleID = vehicleID;
		this.sourceLocation = sourceLocation;
		this.destinationLocation = destinationLocation;
		this.fare = fare;
		this.estiamtedTime = estiamtedTime;
		this.distanceTravelled = distanceTravelled;
>>>>>>> main
	}
	public BookVehicelDTO() {
		super();
	}

		
}
