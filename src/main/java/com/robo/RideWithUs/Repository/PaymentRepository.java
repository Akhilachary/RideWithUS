package com.robo.RideWithUs.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.robo.RideWithUs.Entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>{

}
