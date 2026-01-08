package com.robo.RideWithUs.DTO;

import java.util.ArrayList;
import java.util.List;

public class BookingHistoryDTO {

	// Initialize it here to prevent NullPointerException
    private List<RideDetailDTO> rideDetailDTOs = new ArrayList<>();
    private double totalAmount;
	
	public List<RideDetailDTO> getRideDetailDTOs() {
		return rideDetailDTOs;
	}
	public void setRideDetailDTOs(List<RideDetailDTO> rideDetailDTOs) {
		this.rideDetailDTOs = rideDetailDTOs;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BookingHistoryDTO(List<RideDetailDTO> rideDetailDTOs, double totalAmount) {
		super();
		this.rideDetailDTOs = rideDetailDTOs;
		this.totalAmount = totalAmount;
	}
	public BookingHistoryDTO() {
		super();
	}
	
	
}
