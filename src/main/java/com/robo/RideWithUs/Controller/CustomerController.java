package com.robo.RideWithUs.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robo.RideWithUs.DTO.CustomerRegisterDTO;
import com.robo.RideWithUs.DTO.ResponseStructure;
import com.robo.RideWithUs.Entity.Customer;
import com.robo.RideWithUs.Entity.Vehicle;
import com.robo.RideWithUs.Service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerservice;
	
	@PostMapping("/registerCustomer")
	public ResponseEntity<ResponseStructure<Customer>> registerCustomer(@RequestBody CustomerRegisterDTO customerRegisterDTO) {
		
		return customerservice.registerCustomer(customerRegisterDTO);
	}
	
	@GetMapping("/findCustomer/{mobileNumber}")
	public ResponseEntity<ResponseStructure<Customer>> findCustomerByMobileNumber(@PathVariable long mobileNumber) {
		
		return customerservice.findCustomerByMobileNumber(mobileNumber);
		
	}
	
	@GetMapping("/seeallAvailablevehicle/{mobileNumber}")
	public ResponseEntity<ResponseStructure<List<Vehicle>>> sellAllAvailableVehicles(@PathVariable long mobileNumber) {
		
		return customerservice.sellAllAvailableVehicles(mobileNumber);
	}
	
	@DeleteMapping("deleteCustomer/{mobileNumber}")
	public ResponseEntity<ResponseStructure<Customer>> deleteCustomerByMobileNumber(@PathVariable long mobileNumber) {
		
		return customerservice.deleteCustomerByMobileNumber(mobileNumber);
	}
	
	
}
