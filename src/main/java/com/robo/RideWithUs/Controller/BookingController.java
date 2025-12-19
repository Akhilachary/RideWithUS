package com.robo.RideWithUs.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robo.RideWithUs.DTO.BookVehicelDTO;
import com.robo.RideWithUs.DTO.ResponseStructure;
import com.robo.RideWithUs.Entity.Bookings;
import com.robo.RideWithUs.Service.BookingService;
import com.robo.RideWithUs.Service.MailService;

@RequestMapping("/booking")
@RestController
public class BookingController {
	
	@Autowired
	BookingService bookingService;
	
	@Autowired
	MailService mailService;

	@PostMapping("/bookVehicle/{mobileNo}")
	public ResponseEntity<ResponseStructure<Bookings>> BookVehicle(@PathVariable long mobileNo, @RequestBody BookVehicelDTO bookVehicledto) {
		
		return bookingService.bookVehicle(mobileNo,bookVehicledto);
	}
	
	@PostMapping("/sendMail")
	public void sendMail() {
		mailService.sendMail("maheshwaramakhila03@gmail.com","BOOKING CONFIRMED","Here are the Booking Details"); 
	}
}

