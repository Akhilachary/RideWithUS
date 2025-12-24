package com.robo.RideWithUs.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Driver {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String licenseNumber;
	private String upiID;
	private String driverName;
	private String status="ACTIVE";
	private int age;
	private long mobileNumber;
	private String gender;
	private String mailID;
	private String role="DRIVER";
	
	
	 @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL)
	 @JsonManagedReference
	private Vehicle vehicle;
	 
	 @OneToMany
	private List<Bookings> bookings;
	 
	 @OneToOne
	 private User user;
	
	
	public User getUser() {
		return user;
	}
	 public void setUser(User user) {
		 this.user = user;
	 }
	public List<Bookings> getBookings() {
		return bookings;
	}
	 public void setBookings(List<Bookings> bookings) {
		 this.bookings = bookings;
	 }
	 public void setId(int id) {
		 this.id = id;
	 }
	public int getId() {
		return id;
	}
	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	public String getUpiID() {
		return upiID;
	}
	public void setUpiID(String upiID) {
		this.upiID = upiID;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public long getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMailID() {
		return mailID;
	}
	public void setMailID(String mailID) {
		this.mailID = mailID;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	public Driver(int id, String licenseNumber, String upiID, String driverName, String status, int age,
			long mobileNumber, String gender, String mailID, String role, Vehicle vehicle, List<Bookings> bookings,
			User user) {
		super();
		this.id = id;
		this.licenseNumber = licenseNumber;
		this.upiID = upiID;
		this.driverName = driverName;
		this.status = status;
		this.age = age;
		this.mobileNumber = mobileNumber;
		this.gender = gender;
		this.mailID = mailID;
		this.role = role;
		this.vehicle = vehicle;
		this.bookings = bookings;
		this.user = user;
	}
	public Driver() {
		super();
	}
	
	
	
	
}
