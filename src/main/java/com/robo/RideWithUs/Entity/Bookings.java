package com.robo.RideWithUs.Entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@JsonIgnore
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "vehicle_id")
	private Vehicle vehicle;
	
	private int OTP;
	private String sourceLocation;
	private String destinationLocation;
	private double distanceTravelled;
	private int fare;
	private int estimatedTimeRequired;
	private LocalDateTime bookingDate;
	private String paymentStatus;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private String bookingStatus;

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

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
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

	public int getEstimatedTimeRequired() {
		return estimatedTimeRequired;
	}

	public void setEstimatedTimeRequired(int estimatedTimeRequired) {
		this.estimatedTimeRequired = estimatedTimeRequired;
	}

	public LocalDateTime getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDateTime bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	

	public int getOTP() {
		return OTP;
	}

	public void setOTP(int oTP) {
		OTP = oTP;
	}

	public Bookings(int id, Customer customer, Vehicle vehicle, int oTP, String sourceLocation,
			String destinationLocation, double distanceTravelled, int fare, int estimatedTimeRequired,
			LocalDateTime bookingDate, String paymentStatus, String bookingStatus, Payment payment) {
		super();
		this.id = id;
		this.customer = customer;
		this.vehicle = vehicle;
		OTP = oTP;
		this.sourceLocation = sourceLocation;
		this.destinationLocation = destinationLocation;
		this.distanceTravelled = distanceTravelled;
		this.fare = fare;
		this.estimatedTimeRequired = estimatedTimeRequired;
		this.bookingDate = bookingDate;
		this.paymentStatus = paymentStatus;
		this.bookingStatus = bookingStatus;
		this.payment = payment;
	}

	

	public Bookings() {
		super();
	}

	

}
