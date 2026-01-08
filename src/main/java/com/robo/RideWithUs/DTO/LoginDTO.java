package com.robo.RideWithUs.DTO;

public class LoginDTO {
	
	private String mobile;
	private String password;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LoginDTO(String mobile, String password) {
		super();
		this.mobile = mobile;
		this.password = password;
	}
	public LoginDTO() {
		super();
	}
	
}
