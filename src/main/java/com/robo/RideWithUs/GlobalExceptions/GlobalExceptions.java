package com.robo.RideWithUs.GlobalExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.robo.RideWithUs.DTO.ResponseStructure;
import com.robo.RideWithUs.Exceptions.APIwillNotGivingTheLocationException;
import com.robo.RideWithUs.Exceptions.CustomerExistAlreadyException;
import com.robo.RideWithUs.Exceptions.CustomerNotFoundException;
import com.robo.RideWithUs.Exceptions.DriverAlreadyExistException;
import com.robo.RideWithUs.Exceptions.DriverBlockedException;
import com.robo.RideWithUs.Exceptions.DriverNotAvailableException;
import com.robo.RideWithUs.Exceptions.IncorrectLocationException;
import com.robo.RideWithUs.Exceptions.InvalidOTPException;
import com.robo.RideWithUs.Exceptions.LocationNotFoundException;
import com.robo.RideWithUs.Exceptions.NoActiveBookingFoundException;

@RestControllerAdvice
public class GlobalExceptions {

	@ExceptionHandler(com.robo.RideWithUs.Exceptions.DriverNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> DriverNotFoundException(
			com.robo.RideWithUs.Exceptions.DriverNotFoundException ex) {

		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("Driver is Not Found");
		response.setData("Driver is Not Found");

		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(com.robo.RideWithUs.Exceptions.DriverNotFoundExceptionForthisNumber.class)
	public ResponseEntity<ResponseStructure<String>> DriverNotFoundExceptionForthisNumber(
			com.robo.RideWithUs.Exceptions.DriverNotFoundExceptionForthisNumber ex) {

		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("Driver is Not Found with this mobile Number");
		response.setData("Driver is Not Found with this mobile number");

		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(com.robo.RideWithUs.Exceptions.VehicleNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> VehicleNotFoundException(
			com.robo.RideWithUs.Exceptions.VehicleNotFoundException ex) {

		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("Vehice is Not Found");
		response.setData("Vehicle is Not Found");

		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(com.robo.RideWithUs.Exceptions.CustomerNotFoundWithMobileNumberException.class)
	public ResponseEntity<ResponseStructure<String>> CustomerNotFoundWithMobileNumberException(
			com.robo.RideWithUs.Exceptions.CustomerNotFoundWithMobileNumberException ex) {

		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("Customer is Not Found With this MobileNumber");
		response.setData("Customer is Not Found With this MobileNumber");

		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(com.robo.RideWithUs.Exceptions.DriverNotFoundWithMobileNumberException.class)
	public ResponseEntity<ResponseStructure<String>> DriverNotFoundWithMobileNumberException(
			com.robo.RideWithUs.Exceptions.DriverNotFoundWithMobileNumberException ex) {

		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("Driver is Not Found With this MobileNumber");
		response.setData("Driver is Not Found With this MobileNumber");

		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(com.robo.RideWithUs.Exceptions.CustomerNotFoundWithThisMobileNumberException.class)
	public ResponseEntity<ResponseStructure<String>> CustomerNotFoundWithThisMobileNumberException(
			com.robo.RideWithUs.Exceptions.CustomerNotFoundWithThisMobileNumberException ex) {

		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("Customer is Not Found With this MobileNumber");
		response.setData("Customer is Not Found With this MobileNumber");

		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(APIwillNotGivingTheLocationException.class)
	public ResponseEntity<ResponseStructure<String>> APIwillNotGivingTheLocationException(
			APIwillNotGivingTheLocationException ex) {

		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage("API will not giving the Location");
		response.setData("API will not giving the Location");

		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(com.robo.RideWithUs.Exceptions.DistanceDurationAPIFailedException.class)
	public ResponseEntity<ResponseStructure<String>> DistanceDurationAPIFailedException(
			com.robo.RideWithUs.Exceptions.DistanceDurationAPIFailedException ex) {

		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage("API will not giving the Duration&Distance");
		response.setData("API will not giving the Duration&Distance");

		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(com.robo.RideWithUs.Exceptions.LocationNotFoundForCityNameException.class)
	public ResponseEntity<ResponseStructure<String>> LocationNotFoundForCityNameException(
			com.robo.RideWithUs.Exceptions.LocationNotFoundForCityNameException ex) {

		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value());
		response.setMessage("Location Not Found For CityName");
		response.setData("Location Not Found For CityName");

		return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
	}

	@ExceptionHandler(com.robo.RideWithUs.Exceptions.LocationIQErrorForCityNameException.class)
	public ResponseEntity<ResponseStructure<String>> LocationIQErrorForCityNameException(
			com.robo.RideWithUs.Exceptions.LocationIQErrorForCityNameException ex) {

		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value());
		response.setMessage("LocationIQ Error Not Found For CityName");
		response.setData("LocationIQ Error Not Found For CityName");

		return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
	}

	@ExceptionHandler(IncorrectLocationException.class)
	public ResponseEntity<ResponseStructure<String>> incorrectLocationException() {
		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage("Location Entered is incorrect.");
		response.setData("PROVIDE VALID LOCATION");

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoActiveBookingFoundException.class)
	public ResponseEntity<ResponseStructure<String>> noActiveBookingFoundException() {
		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage("NoActiveBookingFoundException");
		response.setData(null);

		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(com.robo.RideWithUs.Exceptions.VehicleNotAvailableException.class)
	public ResponseEntity<ResponseStructure<String>> VehicleNotAvailableException() {
		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage("Sorry! Vehilce Already Booked");
		response.setData(null);

		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DriverNotAvailableException.class)
	public ResponseEntity<ResponseStructure<String>> driverNotAvailableException(DriverNotAvailableException ex) {

		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage("Sorry! Driver is not available at the moment");
		response.setData(null);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> CustomerNotFoundException() {

		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("Customer is Not Found With this Id");
		response.setData(null);

		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(DriverAlreadyExistException.class)
	public ResponseEntity<ResponseStructure<String>> DriverAlreadyExistException() {
		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage("Driver is Already exists with this mobileNumber");
		response.setData(null);

		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CustomerExistAlreadyException.class)
	public ResponseEntity<ResponseStructure<String>> CustomerExistAlreadyException() {
		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage("Customer is Already exists with this mobileNumber");
		response.setData(null);

		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DriverBlockedException.class)
	public ResponseEntity<ResponseStructure<String>> DriverBlockedException() {
		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage("DRIVER ALREADY BLOCKED");
		response.setData(null);

		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ResponseStructure<String>> handleIllegalStateException(IllegalStateException ex) {

		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage(ex.getMessage());
		response.setData("INVALID APPLICATION STATE");

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LocationNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleLocationNotFoundException(LocationNotFoundException ex) {

		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage(ex.getMessage() != null ? ex.getMessage()
				: "Location not found. Please provide a valid city/location.");
		response.setData("INVALID LOCATION");

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidOTPException.class)
	 public ResponseEntity<ResponseStructure<String>> invalidOTPException() {
		 ResponseStructure<String> response = new ResponseStructure<>();
		    response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		    response.setMessage("Invalid OTP. Please enter the correct OTP.");
		    response.setData(null);

		    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	 }
}
