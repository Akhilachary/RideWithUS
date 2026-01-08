package com.robo.RideWithUs.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.robo.RideWithUs.DTO.*;
import com.robo.RideWithUs.Entity.Bookings;
import com.robo.RideWithUs.Entity.Customer;
import com.robo.RideWithUs.Service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    //  Helper method to extract mobile from JWT
    private long getLoggedInCustomerMobile() {
        String mobile = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return Long.parseLong(mobile);
    }

    //  Get logged-in customer profile
    @GetMapping("/profile")
    public ResponseEntity<ResponseStructure<Customer>> getCustomerProfile() {
        long mobile = getLoggedInCustomerMobile();
        return customerService.findCustomerByMobileNumber(mobile);
    }

    // See all available vehicles in a city
    @GetMapping("/available-vehicles/{city}")
    public ResponseEntity<ResponseStructure<AvailableVehicleDTO>> seeAllAvailableVehicles(
            @PathVariable String city) {

        long mobile = getLoggedInCustomerMobile();
        return customerService.seeAllAvailableVehicles(mobile, city);
    }

    //  Delete logged-in customer account
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseStructure<Customer>> deleteCustomer() {
        long mobile = getLoggedInCustomerMobile();
        return customerService.deleteCustomerByMobileNumber(mobile);
    }

    //  See booking history
    @GetMapping("/booking-history")
    public ResponseEntity<ResponseStructure<BookingHistoryDTO>> seeCustomerBookingHistory() {
        long mobile = getLoggedInCustomerMobile();
        return customerService.seeCustomerBookingHistory(mobile);
    }

    //  See active booking
    @GetMapping("/active-booking")
    public ResponseEntity<ResponseStructure<ActiveBookingDTO>> seeActiveBooking() {
        long mobile = getLoggedInCustomerMobile();
        return customerService.seeActiveBooking(mobile);
    }

    //  Cancel booking
    @PutMapping("/cancel-booking/{bookingId}")
    public ResponseEntity<ResponseStructure<Bookings>> cancelBooking(
            @PathVariable int bookingId) {

        long mobile = getLoggedInCustomerMobile();
        return customerService.cancelBookingByCustomer(mobile, bookingId);
    }
}
