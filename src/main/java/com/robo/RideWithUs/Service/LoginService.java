package com.robo.RideWithUs.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.robo.RideWithUs.DTO.LoginDTO;
import com.robo.RideWithUs.DTO.ResponseStructure;
import com.robo.RideWithUs.Entity.Customer;
import com.robo.RideWithUs.Entity.Driver;
import com.robo.RideWithUs.Repository.CustomerRepository;
import com.robo.RideWithUs.Repository.DriverRepository;
import com.robo.RideWithUs.security.JwtUtil;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DriverRepository driverRepository;

    public ResponseEntity<ResponseStructure<String>> login(LoginDTO dto) {

    	System.out.println("LOGIN API HIT");

        // 1️ Authenticate credentials
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                dto.getMobile(),
                dto.getPassword()
            )
        );

        String role;

        // 2️ Identify role and make sure user exists
        Customer customer = customerRepository.findByMobileNumber(Long.parseLong(dto.getMobile())).orElse(null);
        Driver driver = driverRepository.findByMobileNumber(Long.parseLong(dto.getMobile())).orElse(null);

        if (customer != null) {
            role = "CUSTOMER";
        } else if (driver != null) {
            role = "DRIVER";
        } else {
            throw new RuntimeException("User not found");
        }

        // 3️ Generate JWT token
        String token = jwtUtil.generateToken(dto.getMobile(), role);

        // 4️ Build response
        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatusCode(200);
        rs.setMessage("Login successful");
        rs.setData("Bearer " + token);

        return ResponseEntity.ok(rs);
    }
}
