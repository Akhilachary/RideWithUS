package com.robo.RideWithUs.Repository;

import com.robo.RideWithUs.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByMobileNumber(long mobileNumber);
}
