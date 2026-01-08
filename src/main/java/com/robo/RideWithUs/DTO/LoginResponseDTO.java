package com.robo.RideWithUs.DTO;

public class LoginResponseDTO {
	
	private String token;
	private String role;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public LoginResponseDTO(String token, String role) {
		super();
		this.token = token;
		this.role = role;
	}
	public LoginResponseDTO() {
		super();
	}
	
	

}
