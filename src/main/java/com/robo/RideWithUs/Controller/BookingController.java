package com.robo.RideWithUs.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.robo.RideWithUs.DTO.BookVehicelDTO;
import com.robo.RideWithUs.Service.BookingService;

@RestController
public class BookingController {
	
	@Autowired
	BookingService bookingService;

	public void BookVehicle(@RequestBody BookVehicelDTO bookVehicledto) {
		
		bookingService.BookVehicle(bookVehicledto);
	}
}
