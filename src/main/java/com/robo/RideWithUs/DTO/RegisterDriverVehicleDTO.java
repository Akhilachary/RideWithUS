package com.robo.RideWithUs.DTO;

public class RegisterDriverVehicleDTO {
	
	private String licenseNumber;
	private String upiID;
	private String driverName;
	private int driverAge;
	private long driverMobileNumber;
	private String gender;
	private String mailID;
	private String vehicleName;
	private String vehicleNumber;
	private String vehicleModel;
	private String vehicletype;
	private int capacity;
	private double longitude;
	private double latitude;
	private long priceperKilometer;
	private int averageSpeed;
	private String password;
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAverageSpeed() {
		return averageSpeed;
	}

	public void setAverageSpeed(int averageSpeed) {
		this.averageSpeed = averageSpeed;
	}

	public String getVehicletype() {
		return vehicletype;
	}

	public void setVehicletype(String vehicletype) {
		this.vehicletype = vehicletype;
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

	public int getDriverAge() {
		return driverAge;
	}

	public void setDriverAge(int driverAge) {
		this.driverAge = driverAge;
	}

	public long getDriverMobileNumber() {
		return driverMobileNumber;
	}

	public void setDriverMobileNumber(long driverMobileNumber) {
		this.driverMobileNumber = driverMobileNumber;
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

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public long getPriceperKilometer() {
		return priceperKilometer;
	}

	public void setPriceperKilometer(long priceperKilometer) {
		this.priceperKilometer = priceperKilometer;
	}

	

	
	public RegisterDriverVehicleDTO(String licenseNumber, String upiID, String driverName, int driverAge,
			long driverMobileNumber, String gender, String mailID, String vehicleName, String vehicleNumber,
			String vehicleModel, String vehicletype, int capacity, double longitude, double latitude,
			long priceperKilometer, int averageSpeed, String password) {
		super();
		this.licenseNumber = licenseNumber;
		this.upiID = upiID;
		this.driverName = driverName;
		this.driverAge = driverAge;
		this.driverMobileNumber = driverMobileNumber;
		this.gender = gender;
		this.mailID = mailID;
		this.vehicleName = vehicleName;
		this.vehicleNumber = vehicleNumber;
		this.vehicleModel = vehicleModel;
		this.vehicletype = vehicletype;
		this.capacity = capacity;
		this.longitude = longitude;
		this.latitude = latitude;
		this.priceperKilometer = priceperKilometer;
		this.averageSpeed = averageSpeed;
		this.password = password;
	}

	public RegisterDriverVehicleDTO() {
		super();
	}

}
