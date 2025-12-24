package com.robo.RideWithUs.DTO;

import com.robo.RideWithUs.Entity.Bookings;
import com.robo.RideWithUs.Entity.Customer;
import com.robo.RideWithUs.Entity.Payment;
import com.robo.RideWithUs.Entity.Vehicle;

public class SuccessfullRideDTO {
	
	private Bookings bookings;
	private Customer customer;
	private Vehicle vehicle;
	private Payment payment;
	
	
	public Bookings getBookings() {
		return bookings;
	}
	public void setBookings(Bookings bookings) {
		this.bookings = bookings;
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
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public SuccessfullRideDTO(Bookings bookings, Customer customer, Vehicle vehicle, Payment payment) {
		super();
		this.bookings = bookings;
		this.customer = customer;
		this.vehicle = vehicle;
		this.payment = payment;
	}
	public SuccessfullRideDTO() {
		super();
	}
	
	

}
