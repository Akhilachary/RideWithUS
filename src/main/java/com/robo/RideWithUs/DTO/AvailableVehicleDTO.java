package com.robo.RideWithUs.DTO;

import java.util.ArrayList;
import java.util.List;

import com.robo.RideWithUs.Entity.Customer;

public class AvailableVehicleDTO {

    private Customer customer;
    private double distance;
    private String sourceLocation;
    private String destinationLocation;

    // IMPORTANT — initialize it immediately!
    private List<VehicleDetail> availableVehicleDetails = new ArrayList<>();

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
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

    public List<VehicleDetail> getAvailableVehicleDetails() {
        return availableVehicleDetails;
    }
    public void setAvailableVehicleDetails(List<VehicleDetail> availableVehicleDetails) {
        this.availableVehicleDetails = availableVehicleDetails;
    }

    public AvailableVehicleDTO(Customer customer, double distance, String sourceLocation, String destinationLocation,
                               List<VehicleDetail> availableVehicleDetails) {
        this.customer = customer;
        this.distance = distance;
        this.sourceLocation = sourceLocation;
        this.destinationLocation = destinationLocation;
        this.availableVehicleDetails = availableVehicleDetails;
    }

    public AvailableVehicleDTO() {
        // still automatically initializes the list because of = new ArrayList<>();
    }
}
