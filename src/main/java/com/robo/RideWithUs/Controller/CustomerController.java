package com.robo.RideWithUs.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robo.RideWithUs.DTO.ActiveBookingDTO;
import com.robo.RideWithUs.DTO.AvailableVehicleDTO;
import com.robo.RideWithUs.DTO.BookingHistoryDTO;
import com.robo.RideWithUs.DTO.CustomerRegisterDTO;
import com.robo.RideWithUs.DTO.ResponseStructure;
import com.robo.RideWithUs.Entity.Bookings;
import com.robo.RideWithUs.Entity.Customer;
import com.robo.RideWithUs.Service.CustomerService;

@RestController
//@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerservice;
	
	@PostMapping("/auth/registerCustomer")
	public ResponseEntity<ResponseStructure<Customer>> registerCustomer(@RequestBody CustomerRegisterDTO customerRegisterDTO) {
		
		return customerservice.registerCustomer(customerRegisterDTO);
	}
	
	@GetMapping("/findCustomer/{mobileNumber}")
	public ResponseEntity<ResponseStructure<Customer>> findCustomerByMobileNumber(@PathVariable long mobileNumber) {
		
		return customerservice.findCustomerByMobileNumber(mobileNumber);
		
	}
	
	@GetMapping("/seeallAvailablevehicle/{mobileNumber}/{city}")
	public ResponseEntity<ResponseStructure<AvailableVehicleDTO>> sellAllAvailableVehicles(@PathVariable long mobileNumber, @PathVariable String city) {
		
		return customerservice.seeAllAvailableVehicles(mobileNumber, city);
	}
	
	@DeleteMapping("deleteCustomer/{mobileNumber}")
	public ResponseEntity<ResponseStructure<Customer>> deleteCustomerByMobileNumber(@PathVariable long mobileNumber) {
		
		return customerservice.deleteCustomerByMobileNumber(mobileNumber);
	}
	
	
	@GetMapping("/seeCustomerBookingHistory")
	public ResponseEntity<ResponseStructure<BookingHistoryDTO>> seeCustomerBookingHistory(@RequestHeader long mobileNo) {
		
		 return customerservice.seeCustomerBookingHistory(mobileNo);
	}
	
	@GetMapping("/findCustomerHaveActiveBookings/{mobileNo}")
	public ResponseEntity<ResponseStructure<ActiveBookingDTO>> findCustomerHaveActiveBookings(@PathVariable long mobileNo) {
		
		return customerservice.seeActiveBooking(mobileNo);
	}
	
	@PutMapping("/cancelBookingByCustomer")
	public ResponseEntity<ResponseStructure<Bookings>> cancelBookingByCustomer(@RequestHeader int customerID,@RequestHeader int bookingID) {
		return customerservice.cancelBookingByCustomer(customerID,bookingID);
	}
	
}
