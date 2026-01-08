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
	PasswordEncoder passwordEncoder;

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
		
		User user = new User();
		user.setMobileNumber(driverVehicleDTO.getDriverMobileNumber());
		user.setPassword(passwordEncoder.encode(driverVehicleDTO.getPassword()));
		user.setRole("DRIVER");
		
		driver.setUser(user);
		vehicle.setDriver(driver);  
		driver.setVehicle(vehicle);
		
		
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

	

	public ResponseEntity<ResponseStructure<Vehicle>> updateDriverVehicleLocation(
	        long driverMobile,
	        UpdateDriverVehicleLocationDTO updatelocation) {

	    // 1️ Get driver from JWT mobile
	    Driver driver = driverRepository.findByMobileNumber(driverMobile)
	            .orElseThrow(DriverNotFoundExceptionForthisNumber::new);

	    // 2️ Get vehicle linked to this driver
	    Vehicle vehicle = driver.getVehicle();
	    if (vehicle == null) {
	        throw new VehicleNotFoundException();
	    }

	    // 3️ Convert coordinates to city
	    String city = getLocation.getLocation(
	            updatelocation.getLatitude(),
	            updatelocation.getLongitude()
	    );

	    // 4️ Update vehicle location
	    vehicle.setCity(city);

	    // 5️ Save vehicle
	    Vehicle updatedVehicle = vehiclerepository.save(vehicle);

	    // 6️ Response
	    ResponseStructure<Vehicle> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.ACCEPTED.value());
	    response.setMessage("Vehicle location updated successfully");
	    response.setData(updatedVehicle);

	    return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}


	public ResponseEntity<ResponseStructure<Driver>> findbyDriver(long mobileNo) {
		
		Driver driver = driverRepository.findByMobileNumber(mobileNo).orElseThrow(()->new DriverNotFoundExceptionForthisNumber());
		  
		// Response
	    ResponseStructure<Driver> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.FOUND.value());
	    response.setMessage("Driver Found successfully");
	    response.setData(driver);

	    return new ResponseEntity<ResponseStructure<Driver>>(response, HttpStatus.FOUND);
	}

	public ResponseEntity<ResponseStructure<DriverDeletedDTO>> deleteDriver(long mobileNo) {
	    // 1. Find the driver
	    Driver driver = driverRepository.findByMobileNumber(mobileNo)
	            .orElseThrow(DriverNotFoundExceptionForthisNumber::new);

	    // 2. BUSY CHECK: Prevent deletion if a ride is ONGOING
	    boolean isBusy = bookingRepository.existsByVehicle_Driver_IdAndBookingStatus(
	            driver.getId(), "ONGOING");

	    if (isBusy) {
	        throw new RuntimeException("Cannot delete account: You have an ONGOING ride! Complete the trip first.");
	    }

	    // 3. Perform Deletion
	    // Because of CascadeType.ALL on User and Vehicle, they will be deleted automatically
	    driverRepository.delete(driver);

	    // 4. Prepare Response
	    DriverDeletedDTO deletedDTO = new DriverDeletedDTO();
	    deletedDTO.setDriverName(driver.getDriverName());
	    deletedDTO.setMobileNumber(mobileNo);
	    deletedDTO.setStatus("Account and linked Vehicle successfully deleted");

	    ResponseStructure<DriverDeletedDTO> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.OK.value());
	    response.setMessage("Driver record removed from system");
	    response.setData(deletedDTO);

	    return new ResponseEntity<>(response, HttpStatus.OK);
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

	
	public ResponseEntity<ResponseStructure<SuccessfullRideDTO>> successfullRide(
	        long driverMobile, int bookingId, String paytype, int otp) {
		
		

	    // 1️ Validate booking
	    Bookings bookings = bookingRepository.findById(bookingId)
	            .orElseThrow(BookingNotFoundException::new);
	    
	//  Verify the ride has actually started
	    if (!bookings.getBookingStatus().equalsIgnoreCase("ONGOING")) {
	        throw new RuntimeException("Ride cannot be completed because it has not started yet!");
	    }

	    // 2️ Validate driver ownership
	    Driver driver = bookings.getVehicle().getDriver();
	    if (driver == null || driver.getMobileNumber() != driverMobile) {
	        throw new RuntimeException("Unauthorized driver for this booking");
	    }

	    // 3️ Validate OTP
	    if (bookings.getOTP() != otp) {
	        throw new InvalidOTPException();
	    }

	    // 4️ Complete ride
	    bookings.setBookingStatus("COMPLETED");
	    bookings.setPaymentStatus("PAID");

	    Customer customer = bookings.getCustomer();
	    customer.setActiveBookingFlag(false);
	    customer.setPenalty(0.0);

	    Vehicle vehicle = bookings.getVehicle();
	    vehicle.setAvailabilityStatus("AVAILABLE");
	    vehicle.setCity(bookings.getDestinationLocation());

	    Payment payment = new Payment();
	    payment.setAmount(bookings.getFare() + customer.getPenalty());
	    payment.setBookings(bookings);
	    payment.setCustomer(customer);
	    payment.setPaymentType(paytype);
	    payment.setVehicle(vehicle);
	    
	    bookings.setPayment(payment);

	    bookingRepository.save(bookings);
	    customerRepository.save(customer);
	    vehiclerepository.save(vehicle);
	    paymentRepository.save(payment);

	    SuccessfullRideDTO dto = new SuccessfullRideDTO();
	    dto.setBookings(bookings);
	    dto.setCustomer(customer);
	    dto.setPayment(payment);
	    dto.setVehicle(vehicle);

	    ResponseStructure<SuccessfullRideDTO> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.ACCEPTED.value());
	    response.setMessage("Ride Completed Successfully");
	    response.setData(dto);

	    return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	
	public ResponseEntity<ResponseStructure<QRCodeDTO>> rideCompletedWithUPI(
	        long driverMobile, int bookingId) {

	    // 1️ Validate booking
	    Bookings bookings = bookingRepository.findById(bookingId)
	            .orElseThrow(BookingNotFoundException::new);
	    
	//  Verify the ride has actually started
	    if (!bookings.getBookingStatus().equalsIgnoreCase("ONGOING")) {
	        throw new RuntimeException("Ride cannot be completed because it has not started yet!");
	    }

	    // 2️ Validate driver ownership
	    Driver driver = bookings.getVehicle().getDriver();
	    if (driver == null || driver.getMobileNumber() != driverMobile) {
	        throw new RuntimeException("Unauthorized driver for this booking");
	    }

	    // 3️ Calculate payment
	    double amount = bookings.getFare() + bookings.getCustomer().getPenalty();
	    String upiID = driver.getUpiID();

	    // 4️ Generate QR
	    String qrUPI = "https://api.qrserver.com/v1/create-qr-code/"
	            + "?size=300x300&data=upi://pay?pa=" + upiID;

	    RestTemplate restTemplate = new RestTemplate();
	    byte[] qrCode = restTemplate.getForObject(qrUPI, byte[].class);

	    // 5️⃣ Prepare response DTO
	    QRCodeDTO dto = new QRCodeDTO();
	    dto.setFare(amount);
	    dto.setQrcode(qrCode);

	    ResponseStructure<QRCodeDTO> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.ACCEPTED.value());
	    response.setMessage("QR code generated successfully for payment");
	    response.setData(dto);

	    return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}



	@Transactional
	public ResponseEntity<ResponseStructure<Bookings>> cancelBookingByDriver(
	        long driverMobile, int bookingID) {

	    // 1️ Fetch booking
	    Bookings booking = bookingRepository.findById(bookingID)
	            .orElseThrow(BookingNotFoundException::new);

	    // 2️ Fetch driver using JWT mobile
	    Driver driver = driverRepository.findByMobileNumber(driverMobile)
	            .orElseThrow(DriverNotFoundException::new);

	    // 3️ Ownership check
	    if (booking.getVehicle() == null ||
	        booking.getVehicle().getDriver() == null ||
	        booking.getVehicle().getDriver().getId() != driver.getId()) {

	        throw new RuntimeException("Unauthorized driver for this booking");
	    }
	    
	    if (booking.getBookingStatus().equalsIgnoreCase("ONGOING")) {
	        throw new RuntimeException("Ride has already started! You cannot cancel it now. Please complete the trip.");
	    }
	    
	    // 4️ Prevent double cancellation
	    if (booking.getBookingStatus().startsWith("CANCELLED")) {
	        throw new RuntimeException("Booking already cancelled");
	    }

	    // 5️ Count today’s cancellations
	    LocalDate today = LocalDate.now();
	    int cancelledCount = bookingRepository
	            .countByVehicle_Driver_IdAndBookingStatusAndBookingDateBetween(
	                    driver.getId(),
	                    "CANCELLED BY DRIVER",
	                    today.atStartOfDay(),
	                    today.plusDays(1).atStartOfDay()
	            );

	    // 6️ Cancel booking
	    booking.setBookingStatus("CANCELLED BY DRIVER");
	    
	    Customer customer = booking.getCustomer();
	    if (customer != null) {
	        customer.setActiveBookingFlag(false);
	        customerRepository.save(customer);
	    }

	    // 7️ Block driver if exceeded limit
	    if (cancelledCount + 1 >= 3) {
	        driver.setStatus("BLOCKED");
	    }

	    // 8️ Make vehicle available
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




	public ResponseEntity<ResponseStructure<Driver>> changeActiveStatus(long driverMobile) {

	    Driver driver = driverRepository.findByMobileNumber(driverMobile)
	            .orElseThrow(DriverNotFoundException::new);

	    String status = driver.getStatus();
	    if (status == null) {
	        throw new IllegalStateException("Driver status is not set");
	    }
	    boolean isBusy = bookingRepository.existsByVehicle_Driver_IdAndBookingStatus(
	            driver.getId(), "ONGOING");

	    if (isBusy) {
	        throw new RuntimeException("Cannot go offline while a ride is ONGOING!");
	    }

	    if (status.equalsIgnoreCase("BLOCKED")) {
	        throw new DriverBlockedException();
	    }

	    if (status.equalsIgnoreCase("ACTIVE")) {
	        driver.setStatus("INACTIVE");
	    } else {
	        driver.setStatus("ACTIVE");
	    }

	    driverRepository.save(driver);

	    ResponseStructure<Driver> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.ACCEPTED.value());
	    response.setMessage("Driver status changed to " + driver.getStatus());
	    response.setData(driver);

	    return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}




	public ResponseEntity<ResponseStructure<Bookings>> startRide(
	        long driverMobile, int otp, int bookingID) {

	    // 1️ Fetch driver using JWT mobile
	    Driver driver = driverRepository.findByMobileNumber(driverMobile)
	            .orElseThrow(DriverNotFoundException::new);

	    // 2️ Fetch booking
	    Bookings booking = bookingRepository.findById(bookingID)
	            .orElseThrow(BookingNotFoundException::new);

	    // 3️ Ensure this booking belongs to this driver
	    if (booking.getVehicle() == null ||
	        booking.getVehicle().getDriver() == null ||
	        booking.getVehicle().getDriver().getId() != driver.getId()) {

	        throw new RuntimeException("Unauthorized driver for this booking");
	    }

	    // 4️ Validate OTP
	    if (booking.getOTP() != otp) {
	        throw new InvalidOTPException();
	    }

	    // 5️ Start ride
	    booking.setBookingStatus("ONGOING");
	    booking.setOTP(bookingService.generateOtp()); // for completion/cancel

	    bookingRepository.save(booking);

	    // 6️ Prepare response
	    ResponseStructure<Bookings> response = new ResponseStructure<>();
	    response.setData(booking);
	    response.setMessage("Your Ride has started!");
	    response.setStatusCode(HttpStatus.ACCEPTED.value());

	    // 7️ Send mail
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

	    return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	
	
	
	

}
