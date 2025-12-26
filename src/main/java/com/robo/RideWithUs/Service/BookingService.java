package com.robo.RideWithUs.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.robo.RideWithUs.DTO.BookVehicelDTO;
import com.robo.RideWithUs.DTO.ResponseStructure;
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
import com.robo.RideWithUs.Repository.VehicleRepository;

@Service
public class BookingService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	VehicleRepository vehicleRepository;

	@Autowired
	BookingRepository bookingRepository;

	@Autowired
	MailService mailService;

	private int generateOtp() {
		return (int) (Math.random() * 9000) + 1000;
	}

	public ResponseEntity<ResponseStructure<Bookings>> bookVehicle(
			long mobileNo, BookVehicelDTO dto) {

		Customer customer = customerRepository
				.findByMobileNumber(mobileNo)
				.orElseThrow(CustomerNotFoundWithMobileNumberException::new);

		Vehicle vehicle = vehicleRepository
				.findById(dto.getVehicleID())
				.orElseThrow(VehicleNotFoundException::new);

		Driver driver = vehicle.getDriver();

		if (driver == null || !driver.getStatus().equalsIgnoreCase("ACTIVE")) {
			throw new DriverNotAvailableException();
		}

		if (vehicle.getAvailabilityStatus().equalsIgnoreCase("BOOKED")) {
			throw new VehicleNotAvailableException();
		}

		Bookings booking = new Bookings();
		booking.setCustomer(customer);
		booking.setVehicle(vehicle);
		booking.setSourceLocation(dto.getSourceLocation());
		booking.setDestinationLocation(dto.getDestinationLocation());
		booking.setFare(dto.getFare());
		booking.setDistanceTravelled(dto.getDistanceTravelled());
		booking.setEstimatedTimeRequired(dto.getEstimatedTime());
		booking.setBookingDate(LocalDateTime.now());
		booking.setBookingStatus("PENDING");
		booking.setOTP(generateOtp());
		booking.setPaymentStatus("NOT PAID");

		customer.getBookingslist().add(booking);
		customer.setActiveBookingFlag(true);

		driver.getBookings().add(booking);
		vehicle.setAvailabilityStatus("BOOKED");

		bookingRepository.save(booking);
		customerRepository.save(customer);
		vehicleRepository.save(vehicle);

		ResponseStructure<Bookings> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.ACCEPTED.value());
		response.setMessage("Booking has been done successfully");
		response.setData(booking);

		try {
			String subject = "RideWithUs - Booking Confirmed";
			String message = """
					Hello %s,

					Your ride has been booked successfully.

					Source      : %s
					Destination : %s
					Vehicle     : %s
					Driver      : %s
					Fare        : ₹%d

					OTP : %d

					RideWithUs Team
					""".formatted(
					customer.getCustomerName(),
					booking.getSourceLocation(),
					booking.getDestinationLocation(),
					vehicle.getBrandName() + " " + vehicle.getModal(),
					driver.getDriverName(),
					booking.getFare(),
					booking.getOTP()
			);

			mailService.sendMail(customer.getCutomerEmailID(), subject, message);

		} catch (Exception e) {
			System.out.println("Mail failed: " + e.getMessage());
		}

		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
}
