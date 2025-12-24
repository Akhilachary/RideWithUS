package com.robo.RideWithUs.DTO;

public class CustomerRegisterDTO {

	private String name;
    private int age;
    private String gender;
    private long mobileNo;
    private String email;
    private double latitude;
    private double longitude;
    private String password;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

	public CustomerRegisterDTO(String name, int age, String gender, long mobileNo, String email, double latitude,
			double longitude, String password) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.mobileNo = mobileNo;
		this.email = email;
		this.latitude = latitude;
		this.longitude = longitude;
		this.password = password;
	}

	public CustomerRegisterDTO() {
		super();
	}
    
    
}
