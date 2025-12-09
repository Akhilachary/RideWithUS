package com.robo.RideWithUs.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robo.RideWithUs.DAO.GetLocation;
import com.robo.RideWithUs.DTO.AvailableVehicleDTO;
import com.robo.RideWithUs.DTO.BookVehicelDTO;
import com.robo.RideWithUs.DTO.DestinationLocationResponse;
import com.robo.RideWithUs.DTO.Distance_Duration_Response;
import com.robo.RideWithUs.DTO.VehicleDetail;
import com.robo.RideWithUs.Entity.Bookings;
import com.robo.RideWithUs.Entity.Customer;
import com.robo.RideWithUs.Entity.Driver;
import com.robo.RideWithUs.Entity.Vehicle;
import com.robo.RideWithUs.Exceptions.CustomerNotFoundWithMobileNumberException;
import com.robo.RideWithUs.Exceptions.DriverNotFoundWithMobileNumberException;
import com.robo.RideWithUs.Repository.CustomerRepository;
import com.robo.RideWithUs.Repository.DriverRepository;
import com.robo.RideWithUs.Repository.VehicleRepository;

@Service
public class BookingService {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	DriverRepository driverRepository;
	
	@Autowired
	GetLocation location;
	
	@Autowired
	Distance_Duration_Service distance_Duration_Service;
	
	@Autowired
	VehicleRepository vehicleRepository;

	public void BookVehicle(BookVehicelDTO bookVehicledto) {
		
		Customer customer = customerRepository.findByMobileNumber(bookVehicledto.getCustomerMobileNumber()).orElseThrow(()->new CustomerNotFoundWithMobileNumberException());
		
		Driver driver = driverRepository.findByMobileNumber(bookVehicledto.getDriverMobileNumber()).orElseThrow(()->new DriverNotFoundWithMobileNumberException());
		
		
		Bookings bookings = new Bookings();
		bookings.setCustomer(customer);
		bookings.setDriver(driver);
		bookings.setBookingDate(LocalDateTime.now());
		
		String customerLocation = customer.getCustomerCurrentLocation();
		
		bookings.setSourceLocation(customerLocation);
		
		String city = location.getLocation(bookVehicledto.getLatitude(), bookVehicledto.getLongitude());
		
		bookings.setDestinationLocation(city);
		
		DestinationLocationResponse customerLocationCoords = location.getCoordinates2(customerLocation);
		DestinationLocationResponse destinationLocation = location.getCoordinates1(city);

	    // Calculate distance + time
	    Distance_Duration_Response distanceResponse =
	            distance_Duration_Service.getDistanceAndDuration(
	                    customerLocationCoords.getLatitude(),
	                    customerLocationCoords.getLongitude(),
	                    destinationLocation.getLatitude(),
	                    destinationLocation.getLongitude()
	            );

	    double distanceKm = distanceResponse.getDistanceInKm();
	    
	    
	  

		
	}

}
