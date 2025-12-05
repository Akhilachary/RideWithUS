package com.robo.RideWithUs.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
public class Vehicle {

	@Id
	private int id;
	private String brandName;
	private String vehicleNumber;
	private String type;
	private String modal;
	private int capacity;
	private String city;
	private String availabilityStatus = "available";
	private double pricePerKM;
	
	@OneToOne
	@MapsId
	private Driver driver;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModal() {
		return modal;
	}

	public void setModal(String modal) {
		this.modal = modal;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAvailabilityStatus() {
		return availabilityStatus;
	}

	public void setAvailabilityStatus(String availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}

	public double getPricePerKM() {
		return pricePerKM;
	}

	public void setPricePerKM(double pricePerKM) {
		this.pricePerKM = pricePerKM;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public Vehicle(int id, String brandName, String vehicleNumber, String type, String modal, int capacity, String city,
			String availabilityStatus, double pricePerKM, Driver driver) {
		super();
		this.id = id;
		this.brandName = brandName;
		this.vehicleNumber = vehicleNumber;
		this.type = type;
		this.modal = modal;
		this.capacity = capacity;
		this.city = city;
		this.availabilityStatus = availabilityStatus;
		this.pricePerKM = pricePerKM;
		this.driver = driver;
	}

	public Vehicle() {
		super();
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", brandName=" + brandName + ", vehicleNumber=" + vehicleNumber + ", type=" + type
				+ ", modal=" + modal + ", capacity=" + capacity + ", city=" + city + ", availabilityStatus="
				+ availabilityStatus + ", pricePerKM=" + pricePerKM + ", driver=" + driver + "]";
	}
	
	
}
