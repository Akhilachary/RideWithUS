package com.robo.RideWithUs.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.robo.RideWithUs.DAO.GetLocation;
import com.robo.RideWithUs.DTO.ActiveBookingDTO;
import com.robo.RideWithUs.DTO.AvailableVehicleDTO;
import com.robo.RideWithUs.DTO.BookingHistoryDTO;
import com.robo.RideWithUs.DTO.CustomerRegisterDTO;
import com.robo.RideWithUs.DTO.DestinationLocationResponse;
import com.robo.RideWithUs.DTO.Distance_Duration_Response;
import com.robo.RideWithUs.DTO.ResponseStructure;
import com.robo.RideWithUs.DTO.RideDetailDTO;
import com.robo.RideWithUs.DTO.VehicleDetail;
import com.robo.RideWithUs.Entity.Bookings;
import com.robo.RideWithUs.Entity.Customer;
import com.robo.RideWithUs.Entity.User;
import com.robo.RideWithUs.Entity.Vehicle;
import com.robo.RideWithUs.Exceptions.BookingNotFoundException;
import com.robo.RideWithUs.Exceptions.CustomerExistAlreadyException;
import com.robo.RideWithUs.Exceptions.CustomerNotFoundException;
import com.robo.RideWithUs.Exceptions.CustomerNotFoundWithThisMobileNumberException;
import com.robo.RideWithUs.Exceptions.LocationNotFoundException;
import com.robo.RideWithUs.Exceptions.NoActiveBookingFoundException;
import com.robo.RideWithUs.Repository.BookingRepository;
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
	BookingRepository bookingRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	Distance_Duration_Service distance_Duration_Service;

	public ResponseEntity<ResponseStructure<Customer>> registerCustomer(CustomerRegisterDTO customerRegisterDTO) {
		
		Optional<Customer> c = customerrepository.findByMobileNumber(customerRegisterDTO.getMobileNo());
		
		if(c.isPresent()) {
			throw new CustomerExistAlreadyException();
		}

		Customer customer = new Customer();
		customer.setCustomerName(customerRegisterDTO.getName());
		customer.setCutomerAge(customerRegisterDTO.getAge());
		customer.setCustomerGender(customerRegisterDTO.getGender());
		customer.setMobileNumber(customerRegisterDTO.getMobileNo());
		customer.setCutomerEmailID(customerRegisterDTO.getEmail());
		customer.setCustomerCurrentLocation(
				getLocation.getLocation(customerRegisterDTO.getLatitude(), customerRegisterDTO.getLongitude()));
		
		User user = new User();
		user.setMobileNumber(customerRegisterDTO.getMobileNo());
		user.setPassword(encoder.encode(customerRegisterDTO.getPassword()));
		user.setRole("CUSTOMER");
		
		customer.setUser(user);
		Customer savedcustomer = customerrepository.save(customer);

		ResponseStructure<Customer> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Customer registered successfully");
		response.setData(savedcustomer);

		return new ResponseEntity<ResponseStructure<Customer>>(response, HttpStatus.CREATED);

	}

	public ResponseEntity<ResponseStructure<AvailableVehicleDTO>> seeAllAvailableVehicles(
	        long mobileNumber, String city) {

	    Customer customer = customerrepository.findByMobileNumber(mobileNumber)
	            .orElseThrow(CustomerNotFoundWithThisMobileNumberException::new);
	    
	    boolean hasActiveBooking = bookingRepository.existsByCustomerMobileNumberAndBookingStatusIn(
	    	    mobileNumber, List.of("BOOKED", "ONGOING"));

	    	if (hasActiveBooking) {
	    	    customer.setActiveBookingFlag(true);
	    	    customerrepository.save(customer);
	    	    throw new IllegalStateException(
	    	        "You already have an active booking. Please complete or cancel it first."
	    	    );
	    	} 

	    String customerLocation = customer.getCustomerCurrentLocation();

	    DestinationLocationResponse destinationCoords =
	            getLocation.getCoordinates1(city);

	    DestinationLocationResponse customerCoords =
	            getLocation.getCoordinates2(customerLocation);

	    if (destinationCoords == null || customerCoords == null) {
	        throw new LocationNotFoundException();
	    }

	    Distance_Duration_Response distanceResponse =
	            distance_Duration_Service.getDistanceAndDuration(
	                    customerCoords.getLatitude(),
	                    customerCoords.getLongitude(),
	                    destinationCoords.getLatitude(),
	                    destinationCoords.getLongitude()
	            );

	    double distanceKm = distanceResponse.getDistanceInKm();

	    AvailableVehicleDTO dto = new AvailableVehicleDTO();
	    dto.setCustomer(customer);
	    dto.setSourceLocation(customerLocation);
	    dto.setDestinationLocation(city);
	    dto.setDistance(distanceKm);

	    //  Fetch vehicles near CUSTOMER current location
	    List<Vehicle> vehicles =
	            vehicleRepository.findByCityAndAvailabilityStatus(
	                    customerLocation, "AVAILABLE");
	    double penalty = customer.getPenalty();

	    for (Vehicle v : vehicles) {

	        // Driver must exist
	        if (v.getDriver() == null) continue;

	        // Driver must be AVAILABLE (not BLOCKED / UNAVAILABLE)
	        if (!v.getDriver().getStatus().equalsIgnoreCase("ACTIVE")) continue;

	        VehicleDetail detail = new VehicleDetail();

	        //  Fare calculation
	        double fare = v.getPricePerKM() * distanceKm;
	        detail.setFare((int) Math.round(fare)+(int)penalty);

	        //  Estimated time
	        if (v.getAverageSpeed() <= 0) {
	            detail.setEstimatedTime(-1);
	            detail.setEstimatedTimeString("N/A");
	        } else {
	            int totalMinutes =
	                    (int) Math.round((distanceKm / v.getAverageSpeed()) * 60);

	            detail.setEstimatedTime(totalMinutes);

	            int hr = totalMinutes / 60;
	            int min = totalMinutes % 60;
	            detail.setEstimatedTimeString(hr + " hours " + min + " minutes");
	        }

	        detail.setVehicle(v);
	        dto.getAvailableVehicleDetails().add(detail);
	    }

	    ResponseStructure<AvailableVehicleDTO> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.OK.value());
	    response.setMessage("Available vehicles fetched successfully");
	    response.setData(dto);

	    return new ResponseEntity<>(response, HttpStatus.OK);
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
		response.setStatusCode(HttpStatus.FOUND.value());
		response.setMessage("Customer fetched successfully");
		response.setData(customer);

		return new ResponseEntity<ResponseStructure<Customer>>(response, HttpStatus.FOUND);

	}

	public ResponseEntity<ResponseStructure<BookingHistoryDTO>> seeCustomerBookingHistory(long mobileNo) {

		customerrepository.findByMobileNumber(mobileNo).orElseThrow(()-> new CustomerNotFoundWithThisMobileNumberException());
		
		List<Bookings> bookings = bookingRepository.findByCustomerMobileNumberAndBookingStatus(mobileNo,"COMPLETED");
		
		double totalAmount = 0;
		
		BookingHistoryDTO bookingHistoryDTO = new BookingHistoryDTO();
		
		for(Bookings b : bookings) {
			
			RideDetailDTO dto = new RideDetailDTO();
			dto.setDestinationLocation(b.getDestinationLocation());
			dto.setDistance(b.getDistanceTravelled());
			dto.setFare(b.getFare());
			dto.setSourceLocation(b.getSourceLocation());
			
			totalAmount += b.getFare();
			bookingHistoryDTO.getRideDetailDTOs().add(dto);
		}
		
		bookingHistoryDTO.setTotalAmount(totalAmount);
		
		ResponseStructure<BookingHistoryDTO> responseStructure = new ResponseStructure<BookingHistoryDTO>();
		responseStructure.setStatusCode(HttpStatus.FOUND.value());
		responseStructure.setMessage("Booking History Fetched Successfully.");
		responseStructure.setData(bookingHistoryDTO);
		
		return new ResponseEntity<ResponseStructure<BookingHistoryDTO>>(responseStructure,HttpStatus.FOUND);
		
	}

	
	public ResponseEntity<ResponseStructure<ActiveBookingDTO>> seeActiveBooking(long mobileNo) {

	    Customer customer = customerrepository
	        .findByMobileNumber(mobileNo)
	        .orElseThrow(CustomerNotFoundWithThisMobileNumberException::new);

	 // If the flag is false, don't even look for a booking
	    if (!customer.isActiveBookingFlag()) {
	        throw new NoActiveBookingFoundException();
	    }

	    Bookings booking = bookingRepository.findTopByCustomerMobileNumberAndBookingStatusInOrderByIdDesc(
	    	    mobileNo, List.of("BOOKED", "ONGOING"));

	    if (booking == null) {
	        // Cleanup: If flag was true but no ongoing booking found, sync the flag
	        customer.setActiveBookingFlag(false);
	        customerrepository.save(customer);
	        throw new NoActiveBookingFoundException();
	    }

	    ActiveBookingDTO dto = new ActiveBookingDTO();
	    dto.setCustomerName(customer.getCustomerName());
	    dto.setCustomerMobileNo(mobileNo);
	    dto.setCurrentLocation(customer.getCustomerCurrentLocation());
	    dto.setBookings(booking);

	    ResponseStructure<ActiveBookingDTO> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.OK.value());
	    response.setMessage("Active Booking Found");
	    response.setData(dto);

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}


	public ResponseEntity<ResponseStructure<Bookings>> cancelBookingByCustomer(
	        long mobileNo, int bookingID) {

	    //  Customer fetched ONLY via JWT mobile number
	    Customer customer = customerrepository
	            .findByMobileNumber(mobileNo)
	            .orElseThrow(() -> new CustomerNotFoundException());

	    Bookings bookings = bookingRepository
	            .findById(bookingID)
	            .orElseThrow(() -> new BookingNotFoundException());

	    //  Ownership check (VERY IMPORTANT)
	    if (!bookings.getCustomer().getId().equals(customer.getId())) {
	        throw new RuntimeException("You are not allowed to cancel this booking");
	    }

	    // Prevent double cancellation
	    if (bookings.getBookingStatus().startsWith("CANCELLED")) {
	        throw new RuntimeException("Booking already cancelled");
	    }

	    // Apply penalty ONLY if driver assigned
	    if (bookings.getVehicle() != null &&
	        bookings.getVehicle().getDriver() != null) {

	        double penalty = bookings.getFare() * 0.10;
	        customer.setPenalty(customer.getPenalty() + penalty);
	    }

	    bookings.setBookingStatus("CANCELLED_BY_CUSTOMER");

	    Vehicle vehicle = bookings.getVehicle();
	    vehicle.setAvailabilityStatus("AVAILABLE");

	    customer.setActiveBookingFlag(false);

	    bookingRepository.save(bookings);
	    vehicleRepository.save(vehicle);
	    customerrepository.save(customer);

	    ResponseStructure<Bookings> responseStructure = new ResponseStructure<>();
	    responseStructure.setStatusCode(HttpStatus.ACCEPTED.value());
	    responseStructure.setMessage("SUCCESSFULLY CANCELLED BY CUSTOMER");
	    responseStructure.setData(bookings);

	    return new ResponseEntity<>(responseStructure, HttpStatus.ACCEPTED);
	}

	
}
