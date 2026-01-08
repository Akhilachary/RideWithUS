package com.robo.RideWithUs.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.robo.RideWithUs.Entity.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
	
	 Optional<Driver> findByMobileNumber(long mobileNumber);

	 boolean existsByMobileNumber(long mobileNumber);
}
