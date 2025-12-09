package com.robo.RideWithUs.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.robo.RideWithUs.DAO.GetLocation;
import com.robo.RideWithUs.DTO.AvailableVehicleDTO;
import com.robo.RideWithUs.DTO.CustomerRegisterDTO;
import com.robo.RideWithUs.DTO.DestinationLocationResponse;
import com.robo.RideWithUs.DTO.Distance_Duration_Response;
import com.robo.RideWithUs.DTO.ResponseStructure;
import com.robo.RideWithUs.DTO.VehicleDetail;
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
	VehicleRepository vehicleRepository;
	
	@Autowired
	Distance_Duration_Service distance_Duration_Service;

	public ResponseEntity<ResponseStructure<Customer>> registerCustomer(CustomerRegisterDTO customerRegisterDTO) {

		Customer customer = new Customer();
		customer.setCustomerName(customerRegisterDTO.getName());
		customer.setCutomerAge(customerRegisterDTO.getAge());
		customer.setCustomerGender(customerRegisterDTO.getGender());
		customer.setMobileNumber(customerRegisterDTO.getMobileNo());
		customer.setCutomerEmailID(customerRegisterDTO.getEmail());
		customer.setCustomerCurrentLocation(
				getLocation.getLocation(customerRegisterDTO.getLatitude(), customerRegisterDTO.getLongitude()));

		Customer savedcustomer = customerrepository.save(customer);

		// Response
		ResponseStructure<Customer> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Customer registered successfully");
		response.setData(savedcustomer);

		return new ResponseEntity<ResponseStructure<Customer>>(response, HttpStatus.CREATED);

	}

	public ResponseEntity<ResponseStructure<AvailableVehicleDTO>> sellAllAvailableVehicles(
	        long mobileNumber, String city) {

	    Customer customer = customerrepository.findByMobileNumber(mobileNumber)
	            .orElseThrow(()->new CustomerNotFoundWithThisMobileNumberException());

	    String customerLocation = customer.getCustomerCurrentLocation();

	    DestinationLocationResponse destinationLocation = getLocation.getCoordinates1(city);
	    DestinationLocationResponse customerLocationCoords = getLocation.getCoordinates2(customerLocation);

	    // Calculate distance + time
	    Distance_Duration_Response distanceResponse =
	            distance_Duration_Service.getDistanceAndDuration(
	                    customerLocationCoords.getLatitude(),
	                    customerLocationCoords.getLongitude(),
	                    destinationLocation.getLatitude(),
	                    destinationLocation.getLongitude()
	            );

	    double distanceKm = distanceResponse.getDistanceInKm();

	    // Prepare DTO
	    AvailableVehicleDTO availableVehicleDTO = new AvailableVehicleDTO();
	    availableVehicleDTO.setCustomer(customer);
	    availableVehicleDTO.setSourceLocation(customerLocation);
	    availableVehicleDTO.setDestinationLocation(city);
	    availableVehicleDTO.setDistance((int) distanceKm);

	    // Fetch available vehicles
	    List<Vehicle> availableVehicles =
	            vehicleRepository.findByCityAndAvailabilityStatus(city, "available");

	    // Build vehicle details list
	    for (Vehicle v : availableVehicles) {

	        VehicleDetail detail = new VehicleDetail();

	        // fare calculation
	        double fare = v.getPricePerKM() * distanceKm;
	        detail.setFare((int) fare);
	        
	        if (v.getAverageSpeed() <= 0) {
	            detail.setEstimatedTime(-1);
	            detail.setEstimatedTimeString("N/A");
	            detail.setFare((int) (v.getPricePerKM() * distanceKm));
	            detail.setVehicle(v);
	            availableVehicleDTO.getAvailableVehicleDetails().add(detail);
	            continue;
	        }


	     // estimated time
	        if (v.getAverageSpeed() <= 0) {

	            detail.setEstimatedTime(-1);
	            detail.setEstimatedTimeString("N/A");

	        } else {

	            double hours = distanceKm / v.getAverageSpeed(); // time in hours (decimal)
	            int totalMinutes = (int) Math.round(hours * 60); // convert to minutes

	            detail.setEstimatedTime(totalMinutes); // raw minutes (if you need it)

	            int hr = totalMinutes / 60;
	            int min = totalMinutes % 60;

	            detail.setEstimatedTimeString(hr + " hours " + min + " minutes");
	        }


	        detail.setVehicle(v);

	        availableVehicleDTO.getAvailableVehicleDetails().add(detail);
	    }

	    // Response
	    ResponseStructure<AvailableVehicleDTO> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.FOUND.value());
	    response.setMessage("All available vehicles fetched successfully");
	    response.setData(availableVehicleDTO);

	    return new ResponseEntity<>(response, HttpStatus.FOUND);
	}


	public ResponseEntity<ResponseStructure<Customer>> findCustomerByMobileNumber(long mobileNumber) {

		Customer customer = customerrepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new CustomerNotFoundWithThisMobileNumberException());

		// Response
		ResponseStructure<Customer> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.FOUND.value());
		response.setMessage("Customer fetched successfully");
		response.setData(customer);

		return new ResponseEntity<ResponseStructure<Customer>>(response, HttpStatus.FOUND);

	}

	public ResponseEntity<ResponseStructure<Customer>> deleteCustomerByMobileNumber(long mobileNumber) {

		Customer customer = customerrepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new CustomerNotFoundWithThisMobileNumberException());

		customerrepository.delete(customer);

		// Response
		ResponseStructure<Customer> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.GONE.value());
		response.setMessage("Customer deleted successfully");
		response.setData(customer);

		return new ResponseEntity<ResponseStructure<Customer>>(response, HttpStatus.GONE);

	}

}
