package com.robo.RideWithUs.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.robo.RideWithUs.Configirations.PasswordConfiguration;
import com.robo.RideWithUs.DAO.GetLocation;
import com.robo.RideWithUs.DTO.BookingHistoryDTO;
import com.robo.RideWithUs.DTO.DriverDeletedDTO;
import com.robo.RideWithUs.DTO.QRCodeDTO;
import com.robo.RideWithUs.DTO.RegisterDriverVehicleDTO;
import com.robo.RideWithUs.DTO.ResponseStructure;
import com.robo.RideWithUs.DTO.RideDetailDTO;
import com.robo.RideWithUs.DTO.SuccessfullRideDTO;
import com.robo.RideWithUs.DTO.UpdateDriverVehicleLocationDTO;
import com.robo.RideWithUs.Entity.Bookings;
import com.robo.RideWithUs.Entity.Customer;
import com.robo.RideWithUs.Entity.Driver;
import com.robo.RideWithUs.Entity.Payment;
import com.robo.RideWithUs.Entity.User;
import com.robo.RideWithUs.Entity.Vehicle;
import com.robo.RideWithUs.Exceptions.BookingNotFoundException;
import com.robo.RideWithUs.Exceptions.DriverAlreadyExistException;
import com.robo.RideWithUs.Exceptions.DriverBlockedException;
import com.robo.RideWithUs.Exceptions.DriverNotFoundException;
import com.robo.RideWithUs.Exceptions.DriverNotFoundExceptionForthisNumber;
import com.robo.RideWithUs.Exceptions.DriverNotFoundWithMobileNumberException;
import com.robo.RideWithUs.Exceptions.InvalidOTPException;
import com.robo.RideWithUs.Exceptions.VehicleNotFoundException;
import com.robo.RideWithUs.Repository.BookingRepository;
import com.robo.RideWithUs.Repository.CustomerRepository;
import com.robo.RideWithUs.Repository.DriverRepository;
import com.robo.RideWithUs.Repository.PaymentRepository;
import com.robo.RideWithUs.Repository.UserRepository;
import com.robo.RideWithUs.Repository.VehicleRepository;

import jakarta.transaction.Transactional;

@Service
public class DriverService {
	
	@Autowired
	DriverRepository driverRepository;
	
	@Autowired
	VehicleRepository vehiclerepository;
	
	@Autowired
	GetLocation getLocation;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	BookingService bookingService;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userrepository;


	public ResponseEntity<ResponseStructure<Driver>> registerDriver(RegisterDriverVehicleDTO driverVehicleDTO) {
		
		Optional<Driver> d = driverRepository.findByMobileNumber(driverVehicleDTO.getDriverMobileNumber());
		if(d.isPresent()) {
			throw new DriverAlreadyExistException();
		}

		Driver driver = new Driver();

		driver.setLicenseNumber(driverVehicleDTO.getLicenseNumber());
		driver.setUpiID(driverVehicleDTO.getUpiID());
		driver.setDriverName(driverVehicleDTO.getDriverName());
		driver.setAge(driverVehicleDTO.getDriverAge());
		driver.setMobileNumber(driverVehicleDTO.getDriverMobileNumber());
		driver.setGender(driverVehicleDTO.getGender());
		driver.setMailID(driverVehicleDTO.getMailID());

		Vehicle vehicle = new Vehicle();

		
		vehicle.setBrandName(driverVehicleDTO.getVehicleName());
		vehicle.setVehicleNumber(driverVehicleDTO.getVehicleNumber());
		vehicle.setType(driverVehicleDTO.getVehicletype());
		vehicle.setModal(driverVehicleDTO.getVehicleModel());
		vehicle.setCapacity(driverVehicleDTO.getCapacity());
		vehicle.setPricePerKM(driverVehicleDTO.getPriceperKilometer());
		vehicle.setAverageSpeed(driverVehicleDTO.getAverageSpeed());
		
		
		String city = getLocation.getLocation(driverVehicleDTO.getLatitude(), driverVehicleDTO.getLongitude());
		vehicle.setCity(city);
		
		vehicle.setDriver(driver);  
		driver.setVehicle(vehicle);
		
		User user = new User();
		user.setMobileNumber(driverVehicleDTO.getDriverMobileNumber());
		user.setRole("Driver");
		
		String encodedPassword = passwordEncoder.encode(driverVehicleDTO.getPassword());
		user.setPassword(encodedPassword);
		
//		System.err.println("Raw :"+ driverVehicleDTO.getPassword());
//		System.err.println(encodedPassword);
		
		User saveduser = userrepository.save(user);
		
		driver.setUser(saveduser);
		
		Driver saveddriver = driverRepository.save(driver);
		
		ResponseStructure<Driver> responseStructure = new ResponseStructure<Driver>();
		responseStructure.setStatusCode(HttpStatus.ACCEPTED.value());
		responseStructure.setMessage("Driver saved successfully");
		responseStructure.setData(saveddriver);
		
		String subject = "Welcome to RideWithUs – Driver Registration Successful";

        String message = """
                Hello %s,

                Congratulations! 🎉
                You have been successfully registered as a driver with RideWithUs.

                Driver Details:
                ------------------------
                Name        : %s
                Mobile No   : %d
                Vehicle     : %s %s
                Vehicle No  : %s
                City        : %s

                You can now start accepting ride requests from customers.

                Drive safe and earn more with RideWithUs 🚗💰

                Regards,
                RideWithUs Team
                """.formatted(
                driver.getDriverName(),
                driver.getDriverName(),
                driver.getMobileNumber(),
                vehicle.getBrandName(),
                vehicle.getModal(),
                vehicle.getVehicleNumber(),
                vehicle.getCity()
        );

        mailService.sendMail(driver.getMailID(), subject, message);
		
		return new ResponseEntity<ResponseStructure<Driver>>(responseStructure,HttpStatus.ACCEPTED);
		
		

	}

	

	public ResponseEntity<ResponseStructure<Vehicle>> UpdateDriverVehicleLocation(UpdateDriverVehicleLocationDTO updatelocation) {
	    
	    // 1. Find driver using mobile number
	    Driver driver = driverRepository.findByMobileNumber(updatelocation.getDriverMobileNumber())
	            .orElseThrow(() -> new DriverNotFoundExceptionForthisNumber());

	    // 2. Get the vehicle linked to driver
	    Vehicle vehicle = driver.getVehicle();
	    if (vehicle == null) {
	        throw new VehicleNotFoundException();
	    }

	    // 3. Convert coordinates into city
	    String city = getLocation.getLocation(updatelocation.getLatitude(), updatelocation.getLongitude());

	    // 4. Update only the city
	    vehicle.setCity(city);

	    // 5. Save (this will NOT give error now)
	    Vehicle updatedvehicle = vehiclerepository.save(vehicle);

	    // Response
	    ResponseStructure<Vehicle> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.ACCEPTED.value());
	    response.setMessage("Vehicle Location updated successfully");
	    response.setData(updatedvehicle);

	    return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	public ResponseEntity<ResponseStructure<Driver>> findbyDriverID(long mobileNo) {
		
		Driver driver = driverRepository.findByMobileNumber(mobileNo).orElseThrow(()->new DriverNotFoundExceptionForthisNumber());
		  
		// Response
	    ResponseStructure<Driver> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.FOUND.value());
	    response.setMessage("Driver Found successfully");
	    response.setData(driver);

	    return new ResponseEntity<ResponseStructure<Driver>>(response, HttpStatus.FOUND);
	}

	public ResponseEntity<ResponseStructure<DriverDeletedDTO>> deleteDriverbyID(long mobileNo) {
		
		Driver driver = driverRepository.findByMobileNumber(mobileNo).orElseThrow(()->new DriverNotFoundExceptionForthisNumber());
		
		driverRepository.delete(driver);
		
		DriverDeletedDTO deletedDTO = new DriverDeletedDTO();
		deletedDTO.setDriverName(driver.getDriverName());
		deletedDTO.setMobileNumber(mobileNo);
		deletedDTO.setStatus("Deleted");
		// Response
	    ResponseStructure<DriverDeletedDTO> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.MOVED_PERMANENTLY.value());
	    response.setMessage("Driver deleted successfully");
	    response.setData(deletedDTO);

	    return new ResponseEntity<ResponseStructure<DriverDeletedDTO>>(response, HttpStatus.MOVED_PERMANENTLY);
	}



	public ResponseEntity<ResponseStructure<BookingHistoryDTO>> seeDriverBookingHistory(long mobileNo) {
		
		Driver driver = driverRepository.findByMobileNumber(mobileNo).orElseThrow(()-> new DriverNotFoundWithMobileNumberException());
		
		List<Bookings> bookings = bookingRepository.findByVehicle_Driver_MobileNumberAndBookingStatus(mobileNo,"COMPLETED");
		
		double totalAmount = 0;
		
		BookingHistoryDTO bookingHistoryDTO = new BookingHistoryDTO();
		
		for (Bookings bookings2 : bookings) {
			
			RideDetailDTO dto = new RideDetailDTO();
			dto.setDestinationLocation(bookings2.getDestinationLocation());
			dto.setDistance(bookings2.getDistanceTravelled());
			dto.setFare(bookings2.getFare());
			dto.setSourceLocation(bookings2.getSourceLocation());
			
			bookingHistoryDTO.getRideDetailDTOs().add(dto);
			totalAmount += bookings2.getFare();
			
		}
		bookingHistoryDTO.setTotalAmount(totalAmount);
		
		ResponseStructure<BookingHistoryDTO> responseStructure = new ResponseStructure<BookingHistoryDTO>();
		responseStructure.setStatusCode(HttpStatus.FOUND.value());
		responseStructure.setMessage("Driver Booking History fetched Successfully.");
		responseStructure.setData(bookingHistoryDTO);
		
		return new ResponseEntity<ResponseStructure<BookingHistoryDTO>>(responseStructure,HttpStatus.FOUND);
	}

	
	public ResponseEntity<ResponseStructure<SuccessfullRideDTO>> successfullRide(int bookingId, String paytype) {
		
		Bookings bookings = bookingRepository.findById(bookingId).orElseThrow(()-> new BookingNotFoundException());
		
		bookings.setBookingStatus("COMPLETED");
		bookings.setPaymentStatus("PAID");
		
		Customer customer = bookings.getCustomer();
		customer.setActiveBookingFlag(false);
		
		Vehicle vehicle = bookings.getVehicle();
		vehicle.setAvailabilityStatus("AVAILABLE");
		
		//OR CALL THE UPDATE VEHILCEDRIVERLOCATION METHOD(LATI,LONGI,MOBILE)
		vehicle.setCity(bookings.getDestinationLocation());
		
		Payment payment = new Payment();
		payment.setAmount(bookings.getFare()+customer.getPenalty());
		payment.setBookings(bookings);
		payment.setCustomer(customer);
		payment.setPaymentType(paytype);
		payment.setVehicle(vehicle);
		
		bookingRepository.save(bookings);
		customerRepository.save(customer);
		vehiclerepository.save(vehicle);
		paymentRepository.save(payment);
		
		SuccessfullRideDTO dto = new SuccessfullRideDTO();
		dto.setBookings(bookings);
		dto.setCustomer(customer);
		dto.setPayment(payment);
		dto.setVehicle(vehicle);
		
		
		ResponseStructure<SuccessfullRideDTO> responseStructure = new ResponseStructure<SuccessfullRideDTO>();
		responseStructure.setStatusCode(HttpStatus.ACCEPTED.value());
		responseStructure.setMessage("Ride Completed Successfully /nTHANK YOU");
		responseStructure.setData(dto);
		
		return new ResponseEntity<ResponseStructure<SuccessfullRideDTO>>(responseStructure, HttpStatus.ACCEPTED);
	
	}
	
	public ResponseEntity<ResponseStructure<QRCodeDTO>> rideCompletedWithUPI(int bookingId) {
		
		Bookings bookings = bookingRepository.findById(bookingId).orElseThrow(()-> new BookingNotFoundException()); 
		
		Driver driver = bookings.getVehicle().getDriver();
		String upiID = driver.getUpiID();
		double amount = bookings.getFare()+bookings.getCustomer().getPenalty();
		
		
		String qrUPI = "https://api.qrserver.com/v1/create-qr-code/?size=300x300&data=upi://pay?pa="+upiID;
		
		RestTemplate restTemplate = new RestTemplate();
		
		byte[] qrCode = restTemplate.getForObject(qrUPI, byte[].class);
		
		QRCodeDTO dto = new QRCodeDTO();
		dto.setFare(amount);
		dto.setQrcode(qrCode);
		
		ResponseStructure<QRCodeDTO> responseStructure = new ResponseStructure<QRCodeDTO>();
		responseStructure.setStatusCode(HttpStatus.ACCEPTED.value());
		responseStructure.setMessage("QR code generated successfully for payment");
		responseStructure.setData(dto);
		
		return new ResponseEntity<ResponseStructure<QRCodeDTO>>(responseStructure,HttpStatus.ACCEPTED);
		
	}


	@Transactional
	public ResponseEntity<ResponseStructure<Bookings>> cancelBookingByDriver(int driverID, int bookingID) {
		
		Bookings booking = bookingRepository.findById(bookingID).orElseThrow(()-> new BookingNotFoundException()); 
		Driver driver = driverRepository.findById(driverID).orElseThrow(()-> new DriverNotFoundException());
		
		 //  Prevent double cancellation
	    if (booking.getBookingStatus().startsWith("CANCELLED")) {
	        throw new RuntimeException("Booking already cancelled");
	    }

	    //  Count today's driver cancellations
	    LocalDate today = LocalDate.now();
	    int cancelledCount = bookingRepository
	            .countByVehicle_Driver_IdAndBookingStatusAndBookingDateBetween(
	                    driverID,
	                    "CANCELLED BY DRIVER",
	                    today.atStartOfDay(),
	                    today.plusDays(1).atStartOfDay()
	            );

	    //  Cancel booking
	    booking.setBookingStatus("CANCELLED BY DRIVER");

	    //  Block driver if limit exceeded
	    if (cancelledCount + 1 >= 3) {
	        driver.setStatus("BLOCKED");
	    }

	    //  Make vehicle available
	    Vehicle vehicle = driver.getVehicle();
	    if (vehicle != null) {
	        vehicle.setAvailabilityStatus("AVAILABLE");
	        vehiclerepository.save(vehicle);
	    }

	    bookingRepository.save(booking);
	    driverRepository.save(driver);

	    ResponseStructure<Bookings> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.ACCEPTED.value());
	    response.setMessage("BOOKING CANCELLED BY DRIVER");
	    response.setData(booking);

	    return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}



	public ResponseEntity<ResponseStructure<Driver>> changeActiveStatus(int driverId) {
		
		Driver driver = driverRepository.findById(driverId).orElseThrow(()-> new DriverNotFoundException());
		String status = driver.getStatus();
		
		if (status == null) {
	        throw new IllegalStateException("Driver status is not set");
	    }

	    if (status.equalsIgnoreCase("BLOCKED")) {
	        throw new DriverBlockedException();
	    }

	    if (status.equalsIgnoreCase("AVAILABLE")) {
	        driver.setStatus("UNAVAILABLE");
	    } else {
	        driver.setStatus("AVAILABLE");
	    }

	    driverRepository.save(driver); 

	    ResponseStructure<Driver> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.ACCEPTED.value());
	    response.setMessage("Driver status changed to " + driver.getStatus());
	    response.setData(driver);

	    return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
		
	}



	public ResponseEntity<ResponseStructure<Bookings>> startRide(int otp, int driverID, int bookingID) {
		
		Driver driver = driverRepository.findById(driverID).orElseThrow(()-> new DriverNotFoundException());
		
		Bookings booking = bookingRepository.findById(bookingID).orElseThrow(()-> new BookingNotFoundException());
		
		if(booking.getOTP()!=otp) {
			throw new InvalidOTPException();
		}
		
		booking.setBookingStatus("ONGOING");
		booking.setOTP(bookingService.generateOtp());
		
		ResponseStructure<Bookings> responseStructure = new ResponseStructure<Bookings>();
		responseStructure.setData(booking);
		responseStructure.setMessage("Your Ride has started!.");
		responseStructure.setStatusCode(HttpStatus.ACCEPTED.value());
		
		String subject = "Ride Started – RideWithUs";

        String message = """
                Hello %s,

                🚗 Your ride has officially started!

                Ride Details:
                -------------------------
                Driver Name : %s
                Vehicle     : %s %s
                From        : %s
                To          : %s
                Status      : %s

                🔐 Ride Completion OTP: %d
                (Please share this OTP with the driver to complete the ride)

                Have a safe and pleasant journey 😊

                Regards,
                RideWithUs Team
                """.formatted(
                booking.getCustomer().getCustomerName(),
                driver.getDriverName(),
                booking.getVehicle().getBrandName(),
                booking.getVehicle().getModal(),
                booking.getSourceLocation(),
                booking.getDestinationLocation(),
                booking.getBookingStatus(),
                booking.getOTP()
        );

        mailService.sendMail(
                booking.getCustomer().getCutomerEmailID(),
                subject,
                message
        );
		
		return new ResponseEntity<>(responseStructure,HttpStatus.ACCEPTED);
	
	}
	
	
	
	

}
