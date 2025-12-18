package com.robo.RideWithUs.DTO;

public class DriverDeletedDTO {

	 private String driverName;
	 private long mobileNumber;
	 private String status;
	 public String getDriverName() {
		 return driverName;
	 }
	 public void setDriverName(String driverName) {
		 this.driverName = driverName;
	 }
	 public long getMobileNumber() {
		 return mobileNumber;
	 }
	 public void setMobileNumber(long mobileNumber) {
		 this.mobileNumber = mobileNumber;
	 }
	 public String getStatus() {
		 return status;
	 }
	 public void setStatus(String status) {
		 this.status = status;
	 }
	 public DriverDeletedDTO(String driverName, long mobileNumber, String status) {
		super();
		this.driverName = driverName;
		this.mobileNumber = mobileNumber;
		this.status = status;
	 }
	 public DriverDeletedDTO() {
		super();
	 }
	 
	 
}
