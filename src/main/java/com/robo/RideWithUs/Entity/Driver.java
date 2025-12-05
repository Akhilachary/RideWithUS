package com.robo.RideWithUs.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Driver {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String licenseNumber;
	private String upiID;
	private String driverName;
	private String status="Active";
	private int age;
	private long mobileNumber;
	private String gender;
	private String mailID;
	
	
	 @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL)
	private Vehicle vehicle;
	
	
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
	public Driver( String licenseNumber, String upiID, String driverName, String status, int age,
			long mobileNumber, String gender, String mailID, Vehicle vehicle) {
		super();
		this.licenseNumber = licenseNumber;
		this.upiID = upiID;
		this.driverName = driverName;
		this.status = status;
		this.age = age;
		this.mobileNumber = mobileNumber;
		this.gender = gender;
		this.mailID = mailID;
		this.vehicle = vehicle;
	}
	public Driver() {
		super();
	}
	
	@Override
	public String toString() {
		return "Driver [id=" + id + ", licenseNumber=" + licenseNumber + ", upiID=" + upiID + ", driverName="
				+ driverName + ", status=" + status + ", age=" + age + ", mobileNumber=" + mobileNumber + ", gender="
				+ gender + ", mailID=" + mailID + ", vehicle=" + vehicle + "]";
	}
	
	
}
