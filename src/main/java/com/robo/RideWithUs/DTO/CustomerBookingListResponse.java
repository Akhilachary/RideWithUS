package com.robo.RideWithUs.DTO;

import java.util.List;

import com.robo.RideWithUs.Entity.Bookings;
import com.robo.RideWithUs.Entity.Customer;

public class CustomerBookingListResponse {

	private Customer customer;
	private List<Bookings> bookingslist;
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<Bookings> getBookingslist() {
		return bookingslist;
	}
	public void setBookingslist(List<Bookings> bookingslist) {
		this.bookingslist = bookingslist;
	}
	public CustomerBookingListResponse(Customer customer, List<Bookings> bookingslist) {
		super();
		this.customer = customer;
		this.bookingslist = bookingslist;
	}
	public CustomerBookingListResponse() {
		super();
	}
	
	
}
