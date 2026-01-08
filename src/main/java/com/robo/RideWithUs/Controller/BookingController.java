package com.robo.RideWithUs.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PostMapping("/bookVehicle")
    public ResponseEntity<ResponseStructure<Bookings>> bookVehicle(@RequestBody BookVehicelDTO bookVehicledto) {
        // Get mobile number from JWT token
        String mobileNoString = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        long mobileNo = Long.parseLong(mobileNoString);

        return bookingService.bookVehicle(mobileNo, bookVehicledto);
    }
}
