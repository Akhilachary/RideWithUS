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
import com.robo.RideWithUs.Repository.UserRepository;
import com.robo.RideWithUs.Repository.VehicleRepository;

@Service
public class CustomerService {

	@Autowired
	private GetLocation getLocation;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private Distance_Duration_Service distanceService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// ---------------- REGISTER CUSTOMER ----------------
	public ResponseEntity<ResponseStructure<Customer>> registerCustomer(
			CustomerRegisterDTO dto) {

		Optional<Customer> existing =
				customerRepository.findByMobileNumber(dto.getMobileNo());

		if (existing.isPresent()) {
			throw new CustomerExistAlreadyException();
		}

		Customer customer = new Customer();
		customer.setCustomerName(dto.getName());
		customer.setCutomerAge(dto.getAge());
		customer.setCustomerGender(dto.getGender());
		customer.setMobileNumber(dto.getMobileNo());
		customer.setCutomerEmailID(dto.getEmail());
		customer.setCustomerCurrentLocation(
				getLocation.getLocation(dto.getLatitude(), dto.getLongitude()));

		User user = new User();
		user.setMobileNumber(dto.getMobileNo());
		user.setRole("Customer");
		user.setPassword(passwordEncoder.encode(dto.getPassword()));

		User savedUser = userRepository.save(user);
		customer.setUser(savedUser);

		Customer savedCustomer = customerRepository.save(customer);

		ResponseStructure<Customer> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Customer registered successfully");
		response.setData(savedCustomer);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// ---------------- AVAILABLE VEHICLES ----------------
	public ResponseEntity<ResponseStructure<AvailableVehicleDTO>>
	seeAllAvailableVehicles(long mobileNumber, String city) {

		Customer customer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(CustomerNotFoundWithThisMobileNumberException::new);

		if (customer.isActiveBookingFlag()) {
			throw new IllegalStateException(
					"You already have an active booking");
		}

		String customerLocation = customer.getCustomerCurrentLocation();

		DestinationLocationResponse dest =
				getLocation.getCoordinates1(city);
		DestinationLocationResponse source =
				getLocation.getCoordinates2(customerLocation);

		if (dest == null || source == null) {
			throw new LocationNotFoundException();
		}

		Distance_Duration_Response distanceResponse =
				distanceService.getDistanceAndDuration(
						source.getLatitude(), source.getLongitude(),
						dest.getLatitude(), dest.getLongitude());

		double distanceKm = distanceResponse.getDistanceInKm();

		AvailableVehicleDTO dto = new AvailableVehicleDTO();
		dto.setCustomer(customer);
		dto.setSourceLocation(customerLocation);
		dto.setDestinationLocation(city);
		dto.setDistance(distanceKm);

		List<Vehicle> vehicles =
				vehicleRepository.findByCityAndAvailabilityStatus(
						customerLocation, "AVAILABLE");

		for (Vehicle v : vehicles) {

			if (v.getDriver() == null ||
					!v.getDriver().getStatus().equalsIgnoreCase("ACTIVE"))
				continue;

			VehicleDetail detail = new VehicleDetail();

			double fare = v.getPricePerKM() * distanceKm;
			detail.setFare((int) Math.round(fare));

			if (v.getAverageSpeed() <= 0) {
				detail.setEstimatedTime(-1);
				detail.setEstimatedTimeString("N/A");
			} else {
				int minutes =
						(int) ((distanceKm / v.getAverageSpeed()) * 60);
				detail.setEstimatedTime(minutes);
				detail.setEstimatedTimeString(
						(minutes / 60) + " hours " + (minutes % 60) + " minutes");
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

	// ---------------- ACTIVE BOOKING ----------------
	public ResponseEntity<ResponseStructure<ActiveBookingDTO>>
	seeActiveBooking(long mobileNo) {

		Customer customer = customerRepository.findByMobileNumber(mobileNo)
				.orElseThrow(CustomerNotFoundWithThisMobileNumberException::new);

		if (!customer.isActiveBookingFlag()) {
			throw new NoActiveBookingFoundException();
		}

		Bookings booking =
				bookingRepository.findFirstByCustomerMobileNumberAndBookingStatus(
						mobileNo, "ONGOING");

		if (booking == null) {
			throw new NoActiveBookingFoundException();
		}

		ActiveBookingDTO dto = new ActiveBookingDTO();
		dto.setCustomerName(customer.getCustomerName());
		dto.setCustomerMobileNo(mobileNo);
		dto.setCurrentLocation(customer.getCustomerCurrentLocation());
		dto.setBookings(booking);

		ResponseStructure<ActiveBookingDTO> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Active booking found");
		response.setData(dto);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// ---------------- BOOKING HISTORY ----------------
	public ResponseEntity<ResponseStructure<BookingHistoryDTO>>
	seeCustomerBookingHistory(long mobileNo) {

		Customer customer = customerRepository.findByMobileNumber(mobileNo)
				.orElseThrow(CustomerNotFoundWithThisMobileNumberException::new);

		List<Bookings> bookings =
				bookingRepository.findByCustomerMobileNumberAndBookingStatus(
						mobileNo, "COMPLETED");

		BookingHistoryDTO history = new BookingHistoryDTO();
		double totalAmount = 0;

		for (Bookings b : bookings) {
			RideDetailDTO dto = new RideDetailDTO();
			dto.setSourceLocation(b.getSourceLocation());
			dto.setDestinationLocation(b.getDestinationLocation());
			dto.setDistance(b.getDistanceTravelled());
			dto.setFare(b.getFare());

			totalAmount += b.getFare();
			history.getRideDetailDTOs().add(dto);
		}

		history.setTotalAmount(totalAmount);

		ResponseStructure<BookingHistoryDTO> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Booking history fetched successfully");
		response.setData(history);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// ---------------- CANCEL BOOKING ----------------
	public ResponseEntity<ResponseStructure<Bookings>>
	cancelBookingByCustomer(int customerID, int bookingID) {

		Customer customer = customerRepository.findById(customerID)
				.orElseThrow(CustomerNotFoundException::new);

		Bookings booking = bookingRepository.findById(bookingID)
				.orElseThrow(BookingNotFoundException::new);

		if (booking.getBookingStatus().startsWith("CANCELLED")) {
			throw new RuntimeException("Booking already cancelled");
		}

		if (booking.getVehicle().getDriver() != null) {
			customer.setPenalty(customer.getPenalty() +
					booking.getFare() * 0.10);
		}

		booking.setBookingStatus("CANCELLED BY CUSTOMER");
		Vehicle vehicle = booking.getVehicle();
		vehicle.setAvailabilityStatus("AVAILABLE");

		bookingRepository.save(booking);
		customerRepository.save(customer);
		vehicleRepository.save(vehicle);

		ResponseStructure<Bookings> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.ACCEPTED.value());
		response.setMessage("Booking cancelled successfully");
		response.setData(booking);

		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
}
