package com.robo.RideWithUs.GlobalExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.robo.RideWithUs.DTO.ResponseStructure;
import com.robo.RideWithUs.Exceptions.APIwillNotGivingTheLocationException;


@RestControllerAdvice
public class GlobalExceptions {

	@ExceptionHandler(com.robo.RideWithUs.Exceptions.DriverNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> DriverNotFoundException(com.robo.RideWithUs.Exceptions.DriverNotFoundException ex) {
		
		 ResponseStructure<String> response = new ResponseStructure<>();
		    response.setStatusCode(HttpStatus.NOT_FOUND.value());
		    response.setMessage("Driver is Not Found");
		    response.setData("Driver is Not Found");

		    return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(com.robo.RideWithUs.Exceptions.DriverNotFoundExceptionForthisNumber.class)
	public ResponseEntity<ResponseStructure<String>> DriverNotFoundExceptionForthisNumber(com.robo.RideWithUs.Exceptions.DriverNotFoundExceptionForthisNumber ex) {
		
		ResponseStructure<String> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.NOT_FOUND.value());
	    response.setMessage("Driver is Not Found with this mobile Number");
	    response.setData("Driver is Not Found with this mobile number");

	    return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(com.robo.RideWithUs.Exceptions.VehicleNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> VehicleNotFoundException(com.robo.RideWithUs.Exceptions.VehicleNotFoundException ex) {
		
		ResponseStructure<String> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.NOT_FOUND.value());
	    response.setMessage("Vehice is Not Found");
	    response.setData("Vehicle is Not Found");

	    return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(com.robo.RideWithUs.Exceptions.CustomerNotFoundWithMobileNumberException.class)
	public ResponseEntity<ResponseStructure<String>> CustomerNotFoundWithMobileNumberException(com.robo.RideWithUs.Exceptions.CustomerNotFoundWithMobileNumberException ex) {
		
		ResponseStructure<String> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.NOT_FOUND.value());
	    response.setMessage("Customer is Not Found With this MobileNumber");
	    response.setData("Customer is Not Found With this MobileNumber");

	    return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.NOT_FOUND);
		
		
	}
	
	@ExceptionHandler(com.robo.RideWithUs.Exceptions.DriverNotFoundWithMobileNumberException.class)
	public ResponseEntity<ResponseStructure<String>> DriverNotFoundWithMobileNumberException(com.robo.RideWithUs.Exceptions.DriverNotFoundWithMobileNumberException ex) {
		

		ResponseStructure<String> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.NOT_FOUND.value());
	    response.setMessage("Driver is Not Found With this MobileNumber");
	    response.setData("Driver is Not Found With this MobileNumber");

	    return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(com.robo.RideWithUs.Exceptions.CustomerNotFoundWithThisMobileNumberException.class)
	public ResponseEntity<ResponseStructure<String>> CustomerNotFoundWithThisMobileNumberException(com.robo.RideWithUs.Exceptions.CustomerNotFoundWithThisMobileNumberException ex) {
		
		ResponseStructure<String> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.NOT_FOUND.value());
	    response.setMessage("Customer is Not Found With this MobileNumber");
	    response.setData("Customer is Not Found With this MobileNumber");

	    return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(APIwillNotGivingTheLocationException.class)
	public ResponseEntity<ResponseStructure<String>> APIwillNotGivingTheLocationException(APIwillNotGivingTheLocationException ex) {
		
		ResponseStructure<String> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.BAD_REQUEST.value());
	    response.setMessage("API will not giving the Location");
	    response.setData("API will not giving the Location");

	    return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.BAD_REQUEST);
		
	}
}
