package com.robo.RideWithUs.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.robo.RideWithUs.DAO.GetLocation;
import com.robo.RideWithUs.DTO.AvailableVehicleDTO;
import com.robo.RideWithUs.DTO.BookVehicelDTO;
<<<<<<< HEAD
import com.robo.RideWithUs.DTO.DestinationLocationResponse;
import com.robo.RideWithUs.DTO.Distance_Duration_Response;
import com.robo.RideWithUs.DTO.VehicleDetail;
=======
import com.robo.RideWithUs.DTO.ResponseStructure;
>>>>>>> main
import com.robo.RideWithUs.Entity.Bookings;
import com.robo.RideWithUs.Entity.Customer;
import com.robo.RideWithUs.Entity.Driver;
import com.robo.RideWithUs.Entity.Vehicle;
import com.robo.RideWithUs.Exceptions.CustomerNotFoundWithMobileNumberException;
import com.robo.RideWithUs.Exceptions.DriverNotAvailableException;
import com.robo.RideWithUs.Exceptions.VehicleNotAvailableException;
import com.robo.RideWithUs.Exceptions.VehicleNotFoundException;
import com.robo.RideWithUs.Repository.BookingRepository;
import com.robo.RideWithUs.Repository.CustomerRepository;
<<<<<<< HEAD
import com.robo.RideWithUs.Repository.DriverRepository;
=======
>>>>>>> main
import com.robo.RideWithUs.Repository.VehicleRepository;

@Service
public class BookingService {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	VehicleRepository vehicleRepository;
	
	@Autowired
	GetLocation location;
	
	@Autowired
<<<<<<< HEAD
	Distance_Duration_Service distance_Duration_Service;
	
	@Autowired
	VehicleRepository vehicleRepository;
=======
	BookingRepository bookingRepository;
	
	@Autowired
	MailService mailService;
>>>>>>> main

	
	public int generateOtp() {
	    return (int) (Math.random() * 9000) + 1000; // 4-digit OTP
	}

	public ResponseEntity<ResponseStructure<Bookings>> bookVehicle(long mobileNo, BookVehicelDTO bookVehicledto) {
		
		Customer customer = customerRepository.findByMobileNumber(mobileNo).orElseThrow(()->new CustomerNotFoundWithMobileNumberException());
				
		Vehicle vehicle = vehicleRepository.findById(bookVehicledto.getVehicleID()).orElseThrow(()-> new VehicleNotFoundException());
		
		Driver driver = vehicle.getDriver();
		
		if(! driver.getStatus().equalsIgnoreCase("ACTIVE")) {
			throw new DriverNotAvailableException();
		}
		
		if (vehicle.getAvailabilityStatus().equals("BOOKED")) {
	        throw new VehicleNotAvailableException();
	    }
		
		Bookings bookings = new Bookings();
		
		bookings.setCustomer(customer);
		bookings.setVehicle(vehicle);
		bookings.setSourceLocation(bookVehicledto.getSourceLocation());
		bookings.setDestinationLocation(bookVehicledto.getDestinationLocation());
		bookings.setFare(bookVehicledto.getFare());
		bookings.setDistanceTravelled(bookVehicledto.getDistanceTravelled());
		bookings.setEstimatedTimeRequired(bookVehicledto.getEstiamtedTime());
		bookings.setBookingDate(LocalDateTime.now());
		bookings.setBookingStatus("PENDING");
		bookings.setOTP(generateOtp());	//how to generate random otp here
		bookings.setPaymentStatus("NOT PAID");
		customer.getBookingslist().add(bookings);
		customer.setActiveBookingFlag(true);
		vehicle.getDriver().getBookings().add(bookings);
		vehicle.setAvailabilityStatus("BOOKED");
		
		
		bookingRepository.save(bookings);
		customerRepository.save(customer);
		vehicleRepository.save(vehicle);
		
		ResponseStructure<Bookings> responseStructure = new ResponseStructure<Bookings>();
		responseStructure.setStatusCode(HttpStatus.ACCEPTED.value());
		responseStructure.setMessage("Booking has been done!.");
		responseStructure.setData(bookings);
		
		try {
            String subject = "RideWithUs - Booking Confirmed ";

            String message = """
                    Hello %s,

                    Your ride has been successfully booked!

                    Booking Details:
                    -----------------------
                    Source      : %s
                    Destination : %s
                    Vehicle     : %s
                    Driver      : %s
                    Fare        : ₹%.2f
                    Distance    : %.2f km
                    Status      : %s

                    Thank you for choosing RideWithUs.
                    Have a safe journey!

                    Regards,
                    RideWithUs Team
                    """.formatted(
                    customer.getCustomerName(),
                    bookings.getSourceLocation(),
                    bookings.getDestinationLocation(),
                    vehicle.getBrandName() + " " + vehicle.getModal(),
                    driver.getDriverName(),
                    bookings.getFare(),
                    bookings.getDistanceTravelled(),
                    bookings.getBookingStatus()
            );

            mailService.sendMail(customer.getCutomerEmailID(), subject, message);

        } catch (Exception e) {
            
            System.out.println("Mail sending failed: " + e.getMessage());
        }
		
<<<<<<< HEAD
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
	    
	    
	  

=======
		return new ResponseEntity<ResponseStructure<Bookings>>(responseStructure,HttpStatus.ACCEPTED);
		
>>>>>>> main
		
	}

}

