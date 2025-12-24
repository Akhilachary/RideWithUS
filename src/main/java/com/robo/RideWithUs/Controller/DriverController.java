package com.robo.RideWithUs.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.robo.RideWithUs.DTO.BookingHistoryDTO;
import com.robo.RideWithUs.DTO.DriverDeletedDTO;
import com.robo.RideWithUs.DTO.QRCodeDTO;
import com.robo.RideWithUs.DTO.RegisterDriverVehicleDTO;
import com.robo.RideWithUs.DTO.ResponseStructure;
import com.robo.RideWithUs.DTO.SuccessfullRideDTO;
import com.robo.RideWithUs.DTO.UpdateDriverVehicleLocationDTO;
import com.robo.RideWithUs.Entity.Bookings;
import com.robo.RideWithUs.Entity.Driver;
import com.robo.RideWithUs.Entity.Vehicle;
import com.robo.RideWithUs.Service.DriverService;

@RestController
//@RequestMapping("/driver")
public class DriverController {

	@Autowired
	DriverService driverService;
	
	@PostMapping("/auth/registerDriver")
	public ResponseEntity<ResponseStructure<Driver>> registerDriver(@RequestBody RegisterDriverVehicleDTO driverVehicleDTO) {
		return driverService.registerDriver(driverVehicleDTO);
	}
	

	@PatchMapping("/updatedrivervehiclelocation")
	public ResponseEntity<ResponseStructure<Vehicle>> UpdateDriverVehicleLocation(@RequestBody UpdateDriverVehicleLocationDTO updatelocation) {
		
		return driverService.UpdateDriverVehicleLocation(updatelocation);
	}

	@GetMapping("/finddriverbyID/{mobileNo}")
	public ResponseEntity<ResponseStructure<Driver>> findbydriverID(@PathVariable long mobileNo) {
		
		return driverService.findbyDriverID(mobileNo);
	}
	
	@DeleteMapping("/deleteDriverbyID/{mobileNo}")
	public ResponseEntity<ResponseStructure<DriverDeletedDTO>> deleteDriverbyID(@PathVariable long mobileNo) {
		return driverService.deleteDriverbyID(mobileNo);

	}
	
	@GetMapping("/seeDriverBookingHistory")
	public ResponseEntity<ResponseStructure<BookingHistoryDTO>> seeDriverBookingHistory(@RequestParam long mobileNo) {
		return driverService.seeDriverBookingHistory(mobileNo);
	}
	
	@PutMapping("completeRideByCash/{bookingId}/{payType}")
	public ResponseEntity<ResponseStructure<SuccessfullRideDTO>> completeRide(@RequestParam int bookingId, @RequestParam String payType) {
		return driverService.successfullRide(bookingId,payType);
	}
	
	@PutMapping("completeRideByUPI/{bookingId}")
	public ResponseEntity<ResponseStructure<QRCodeDTO>> completeRideByUPI(@RequestParam int bookingId) {
		return driverService.rideCompletedWithUPI(bookingId);
	}
	
	@PostMapping("confirmUPIPayment/{bookingId}/{payType}")
	public ResponseEntity<ResponseStructure<SuccessfullRideDTO>> confirmUPIPayment(@RequestParam int bookingId, @RequestParam String payType) {
		return driverService.successfullRide(bookingId,payType);
	}
	
	
	@DeleteMapping("cancelBookingByDriver")
	public ResponseEntity<ResponseStructure<Bookings>> cancelBookingByDriver(@RequestHeader int driverID, @RequestHeader int bookingID) {
		return driverService.cancelBookingByDriver(driverID,bookingID);
	}
	
	@PutMapping("changeActiveStatus")
	public ResponseEntity<ResponseStructure<Driver>> changeActiveStatus(@RequestHeader int driverId) {
		return driverService.changeActiveStatus(driverId);
	}
	
	@PostMapping("/startRide/{otp}/{driverID}/{bookingID}")
	public ResponseEntity<ResponseStructure<Bookings>> startRide(@RequestParam int otp, @RequestParam int driverID, @RequestParam int bookingID) {
		return driverService.startRide(otp,driverID,bookingID);
	}
}
