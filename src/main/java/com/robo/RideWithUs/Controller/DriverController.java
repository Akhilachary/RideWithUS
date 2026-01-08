package com.robo.RideWithUs.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.robo.RideWithUs.DTO.BookingHistoryDTO;
import com.robo.RideWithUs.DTO.DriverDeletedDTO;
import com.robo.RideWithUs.DTO.QRCodeDTO;
import com.robo.RideWithUs.DTO.ResponseStructure;
import com.robo.RideWithUs.DTO.SuccessfullRideDTO;
import com.robo.RideWithUs.DTO.UpdateDriverVehicleLocationDTO;
import com.robo.RideWithUs.Entity.Bookings;
import com.robo.RideWithUs.Entity.Driver;
import com.robo.RideWithUs.Entity.Vehicle;
import com.robo.RideWithUs.Service.DriverService;

@RestController
@RequestMapping("/driver")
public class DriverController {

	@Autowired
	DriverService driverService;
	
	
	 //  Extract driver mobile from JWT 
    private long getLoggedInDriverMobile() {
        return Long.parseLong(
            SecurityContextHolder.getContext()
                .getAuthentication()
                .getName()
        );
    }
    
    //SEND THE DATA (BARRIER TOKEN ) IN HEADER AS Authorization key and barrier token is value

    @PatchMapping("/update-vehicle-location")
    public ResponseEntity<ResponseStructure<Vehicle>> updateDriverVehicleLocation(
            @RequestBody UpdateDriverVehicleLocationDTO dto) {

        long mobile = getLoggedInDriverMobile();
        return driverService.updateDriverVehicleLocation(mobile, dto);
    }

    @GetMapping("/profile")
    public ResponseEntity<ResponseStructure<Driver>> findDriver() {

        long mobile = getLoggedInDriverMobile();
        return driverService.findbyDriver(mobile);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseStructure<DriverDeletedDTO>> deleteDriver() {

        long mobile = getLoggedInDriverMobile();
        return driverService.deleteDriver(mobile);
    }

    @GetMapping("/booking-history")
    public ResponseEntity<ResponseStructure<BookingHistoryDTO>> seeDriverBookingHistory() {

        long mobile = getLoggedInDriverMobile();
        return driverService.seeDriverBookingHistory(mobile);
    }

    @PutMapping("/complete-ride/cash")
    public ResponseEntity<ResponseStructure<SuccessfullRideDTO>> completeRideByCash(
            @RequestParam int bookingId,
            @RequestParam String payType,
            @RequestParam int otp) {

        long mobile = getLoggedInDriverMobile();
        return driverService.successfullRide(mobile, bookingId, payType, otp);
    }

    @PutMapping("/complete-ride/upi")
    public ResponseEntity<ResponseStructure<QRCodeDTO>> completeRideByUPI(
            @RequestParam int bookingId) {

        long mobile = getLoggedInDriverMobile();
        return driverService.rideCompletedWithUPI(mobile, bookingId);
    }

    @PostMapping("/confirm-upi-payment")
    public ResponseEntity<ResponseStructure<SuccessfullRideDTO>> confirmUPIPayment(
            @RequestParam int bookingId,
            @RequestParam String payType,
            @RequestParam int otp) {

        long mobile = getLoggedInDriverMobile();
        return driverService.successfullRide(mobile, bookingId, payType, otp);
    }

    @DeleteMapping("/cancel-booking/{bookingId}")
    public ResponseEntity<ResponseStructure<Bookings>> cancelBookingByDriver(
            @PathVariable int bookingId) {

        long mobile = getLoggedInDriverMobile();
        return driverService.cancelBookingByDriver(mobile, bookingId);
    }

    @PutMapping("/change-active-status")
    public ResponseEntity<ResponseStructure<Driver>> changeActiveStatus() {

        long mobile = getLoggedInDriverMobile();
        return driverService.changeActiveStatus(mobile);
    }

    @PostMapping("/start-ride")
    public ResponseEntity<ResponseStructure<Bookings>> startRide(
            @RequestParam int otp,
            @RequestParam int bookingId) {

        long mobile = getLoggedInDriverMobile();
        return driverService.startRide(mobile, otp, bookingId);
    }
}
