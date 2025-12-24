package com.robo.RideWithUs.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.robo.RideWithUs.Entity.Bookings;

@Repository
public interface BookingRepository extends JpaRepository<Bookings, Integer>{

	 // Customer booking history (completed rides)
    List<Bookings> findByCustomerMobileNumberAndBookingStatus(
            long mobileNo,
            String bookingStatus
    );

    // Customer active booking (only one)
    Bookings findFirstByCustomerMobileNumberAndBookingStatus(
            long mobileNo,
            String bookingStatus
    );

    // Driver booking history
    List<Bookings> findByVehicle_Driver_MobileNumberAndBookingStatus(
            long mobileNo,
            String bookingStatus
    );

    // ❗ FIXED: Driver + date + status
    List<Bookings> findByVehicle_Driver_IdAndBookingDateAndBookingStatus(
            int driverId,
            LocalDateTime bookingDate,
            String bookingStatus
    );

    // ❗ FIXED: Driver booking count between dates
    int countByVehicle_Driver_IdAndBookingStatusAndBookingDateBetween(
            int driverId,
            String bookingStatus,
            LocalDateTime start,
            LocalDateTime end
    );

    
}


