package com.robo.RideWithUs.DTO;

public class QRCodeDTO {
	
	private double fare;
	private byte[] qrcode;
	
	public double getFare() {
		return fare;
	}
	public void setFare(double fare) {
		this.fare = fare;
	}
	public byte[] getQrcode() {
		return qrcode;
	}
	public void setQrcode(byte[] qrcode) {
		this.qrcode = qrcode;
	}
	public QRCodeDTO(double fare, byte[] qrcode) {
		super();
		this.fare = fare;
		this.qrcode = qrcode;
	}
	public QRCodeDTO() {
		super();
	}
	

}
