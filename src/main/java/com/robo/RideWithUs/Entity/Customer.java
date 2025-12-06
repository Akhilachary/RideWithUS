package com.robo.RideWithUs.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String customerName;
	private int cutomerAge;
	private String customerGender;
	private long mobileNumber;
	private String cutomerEmailID;
	private String customerCurrentLocation;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Bookings> bookingslist;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getCutomerAge() {
		return cutomerAge;
	}
	public void setCutomerAge(int cutomerAge) {
		this.cutomerAge = cutomerAge;
	}
	public String getCustomerGender() {
		return customerGender;
	}
	public void setCustomerGender(String customerGender) {
		this.customerGender = customerGender;
	}
	public long MobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getCutomerEmailID() {
		return cutomerEmailID;
	}
	public void setCutomerEmailID(String cutomerEmailID) {
		this.cutomerEmailID = cutomerEmailID;
	}
	public String getCustomerCurrentLocation() {
		return customerCurrentLocation;
	}
	public void setCustomerCurrentLocation(String customerCurrentLocation) {
		this.customerCurrentLocation = customerCurrentLocation;
	}
	public List<Bookings> getBookingslist() {
		return bookingslist;
	}
	public void setBookingslist(List<Bookings> bookingslist) {
		this.bookingslist = bookingslist;
	}
	public Customer(int id, String customerName, int cutomerAge, String customerGender, long mobileNumber,
			String cutomerEmailID, String customerCurrentLocation, List<Bookings> bookingslist) {
		super();
		this.id = id;
		this.customerName = customerName;
		this.cutomerAge = cutomerAge;
		this.customerGender = customerGender;
		this.mobileNumber=mobileNumber;
		this.cutomerEmailID = cutomerEmailID;
		this.customerCurrentLocation = customerCurrentLocation;
		this.bookingslist = bookingslist;
	}
	public Customer() {
		super();
	}
	
	
	
	
}
