package com.robo.RideWithUs.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.robo.RideWithUs.DAO.GetLocation;
import com.robo.RideWithUs.DTO.CustomerRegisterDTO;
import com.robo.RideWithUs.DTO.ResponseStructure;
import com.robo.RideWithUs.Entity.Customer;

import com.robo.RideWithUs.Entity.Vehicle;
import com.robo.RideWithUs.Exceptions.CustomerNotFoundWithThisMobileNumberException;
import com.robo.RideWithUs.Repository.CustomerRepository;
import com.robo.RideWithUs.Repository.VehicleRepository;

@Service
public class CustomerService {
	
	@Autowired
	GetLocation getLocation;
	@Autowired
	CustomerRepository customerrepository;
	
	@Autowired
	VehicleRepository  vehicleRepository;

	public ResponseEntity<ResponseStructure<Customer>> registerCustomer(CustomerRegisterDTO customerRegisterDTO) {
		
		Customer customer = new Customer();
		customer.setCustomerName(customerRegisterDTO.getName());
		customer.setCutomerAge(customerRegisterDTO.getAge());
		customer.setCustomerGender(customerRegisterDTO.getGender());
		customer.setMobileNumber(customerRegisterDTO.getMobileNo());
		customer.setCutomerEmailID(customerRegisterDTO.getEmail());
		customer.setCustomerCurrentLocation(getLocation.getLocation(customerRegisterDTO.getLatitude(), customerRegisterDTO.getLongitude()));
		
		Customer  savedcustomer = customerrepository.save(customer);
		
		// Response
	    ResponseStructure<Customer> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.CREATED.value());
	    response.setMessage("Customer registered successfully");
	    response.setData(savedcustomer);

	    return new ResponseEntity<ResponseStructure<Customer>>(response, HttpStatus.CREATED);
		
		
	}

	public ResponseEntity<ResponseStructure<List<Vehicle>>> sellAllAvailableVehicles(long mobileNumber) {
		
		Customer  customer = customerrepository.findByMobileNumber(mobileNumber).orElseThrow(()->new CustomerNotFoundWithThisMobileNumberException());
		
		List<Vehicle> vehiclelists = vehicleRepository.findAll();
		
		// Response
	    ResponseStructure<List<Vehicle>> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.FOUND.value());
	    response.setMessage("All available vehicles fetched successfully");
	    response.setData(vehiclelists);

	    return new ResponseEntity<ResponseStructure<List<Vehicle>>>(response, HttpStatus.FOUND);
		
		
	}

	public ResponseEntity<ResponseStructure<Customer>> findCustomerByMobileNumber(long mobileNumber) {
		
		Customer customer = customerrepository.findByMobileNumber(mobileNumber).orElseThrow(()->new CustomerNotFoundWithThisMobileNumberException());
		
		// Response
	    ResponseStructure<Customer> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.FOUND.value());
	    response.setMessage("Customer fetched successfully");
	    response.setData(customer);

	    return new ResponseEntity<ResponseStructure<Customer>>(response, HttpStatus.FOUND);
		
		
		
	}

	public ResponseEntity<ResponseStructure<Customer>> deleteCustomerByMobileNumber(long mobileNumber) {
		
		Customer customer = customerrepository.findByMobileNumber(mobileNumber).orElseThrow(()->new CustomerNotFoundWithThisMobileNumberException());
		
		customerrepository.delete(customer);
		
		// Response
	    ResponseStructure<Customer> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.FOUND.value());
	    response.setMessage("Customer fetched successfully");
	    response.setData(customer);

	    return new ResponseEntity<ResponseStructure<Customer>>(response, HttpStatus.FOUND);
		
		
		
	}

}
