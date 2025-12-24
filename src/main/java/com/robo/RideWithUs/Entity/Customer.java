package com.robo.RideWithUs.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String customerName;
	private int cutomerAge;
	private String customerGender;
	private long mobileNumber;
	private String cutomerEmailID;
	private String customerCurrentLocation;
	private boolean activeBookingFlag=false;
	private String role="CUSTOMER";
	private double penalty;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Bookings> bookingslist;
	
	@OneToOne
	private User user;

	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public double getPenalty() {
		return penalty;
	}
	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}
	public boolean isActiveBookingFlag() {
		return activeBookingFlag;
	}
	public void setActiveBookingFlag(boolean activeBookingFlag) {
		this.activeBookingFlag = activeBookingFlag;
	}
	public long getMobileNumber() {
		return mobileNumber;
	}
	public Integer getId() {
		return id;
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
	
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public Customer(Integer id, String customerName, int cutomerAge, String customerGender, long mobileNumber,
			String cutomerEmailID, String customerCurrentLocation, boolean activeBookingFlag, String role,
			double penalty, List<Bookings> bookingslist, User user) {
		super();
		this.id = id;
		this.customerName = customerName;
		this.cutomerAge = cutomerAge;
		this.customerGender = customerGender;
		this.mobileNumber = mobileNumber;
		this.cutomerEmailID = cutomerEmailID;
		this.customerCurrentLocation = customerCurrentLocation;
		this.activeBookingFlag = activeBookingFlag;
		this.role = role;
		this.penalty = penalty;
		this.bookingslist = bookingslist;
		this.user = user;
	}
	public Customer() {
		super();
	}
	
	
	
	
}
