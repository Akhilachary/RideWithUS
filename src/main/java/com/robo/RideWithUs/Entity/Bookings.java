package com.robo.RideWithUs.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Bookings {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "driver_id")
	private Driver driver;

	private String sourceLocation;
	private String destinationLocation;
	private double distanceTravelled;
	private int fare;
	private String estimatedTimeRequired;
	private LocalDateTime bookingDate;

	@OneToOne
	@JoinColumn(name = "payment_id")
	private Payment payment;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
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

	public double getDistanceTravelled() {
		return distanceTravelled;
	}

	public void setDistanceTravelled(double distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
	}

	public int getFare() {
		return fare;
	}

	public void setFare(int fare) {
		this.fare = fare;
	}

	public String getEstimatedTimeRequired() {
		return estimatedTimeRequired;
	}

	public void setEstimatedTimeRequired(String estimatedTimeRequired) {
		this.estimatedTimeRequired = estimatedTimeRequired;
	}

	public LocalDateTime getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDateTime bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Bookings() {
		super();
	}

	public Bookings(int id, Customer customer, Driver driver, String sourceLocation, String destinationLocation,
			double distanceTravelled, int fare, String estimatedTimeRequired, LocalDateTime bookingDate,
			Payment payment) {
		super();
		this.id = id;
		this.customer = customer;
		this.driver = driver;
		this.sourceLocation = sourceLocation;
		this.destinationLocation = destinationLocation;
		this.distanceTravelled = distanceTravelled;
		this.fare = fare;
		this.estimatedTimeRequired = estimatedTimeRequired;
		this.bookingDate = bookingDate;
		this.payment = payment;
	}

}
