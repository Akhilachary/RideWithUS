package com.robo.RideWithUs.DTO;

import com.robo.RideWithUs.Entity.Vehicle;

public class BookVehicelDTO {

    // Customer & Driver info
    private long customerMobileNumber;
    private long driverMobileNumber;

    // Location info
    private double longitude;
    private double latitude;
    private String sourceLocation;
    private String destinationLocation;

    // Trip details
    private int fare;
    private int estimatedTime;
    private int distanceTravelled;

    // Vehicle info
    private int vehicleID;
    private Vehicle vehicle;

    // Constructors
    public BookVehicelDTO() {
        super();
    }

    public BookVehicelDTO(long customerMobileNumber, long driverMobileNumber,
                          double longitude, double latitude,
                          int vehicleID, String sourceLocation,
                          String destinationLocation, int fare,
                          int estimatedTime, int distanceTravelled,
                          Vehicle vehicle) {

        this.customerMobileNumber = customerMobileNumber;
        this.driverMobileNumber = driverMobileNumber;
        this.longitude = longitude;
        this.latitude = latitude;
        this.vehicleID = vehicleID;
        this.sourceLocation = sourceLocation;
        this.destinationLocation = destinationLocation;
        this.fare = fare;
        this.estimatedTime = estimatedTime;
        this.distanceTravelled = distanceTravelled;
        this.vehicle = vehicle;
    }

    // Getters & Setters
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

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public int getDistanceTravelled() {
        return distanceTravelled;
    }

    public void setDistanceTravelled(int distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
