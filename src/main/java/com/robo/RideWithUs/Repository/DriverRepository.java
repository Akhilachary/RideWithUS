package com.robo.RideWithUs.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.robo.RideWithUs.Entity.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

}
