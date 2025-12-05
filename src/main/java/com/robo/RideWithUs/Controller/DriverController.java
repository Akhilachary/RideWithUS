package com.robo.RideWithUs.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robo.RideWithUs.DTO.RegisterDriverVehicleDTO;
import com.robo.RideWithUs.DTO.ResponseStructure;
import com.robo.RideWithUs.Entity.Driver;
import com.robo.RideWithUs.Service.DriverService;

@RestController
@RequestMapping("/driver")
public class DriverController {

	@Autowired
	DriverService driverService;
	
	@PostMapping("/registerDriver")
	public ResponseEntity<ResponseStructure<Driver>> registerDriver(@RequestBody RegisterDriverVehicleDTO driverVehicleDTO) {
		return driverService.registerDriver(driverVehicleDTO);
	}
	
	@GetMapping("/finddriverbyID/{id}")
	public ResponseEntity<ResponseStructure<Driver>> findbydriverID(@PathVariable int id) {
		
		return driverService.findbyDriverID(id);
	}
	
	@DeleteMapping("/deleteDriverbyID/{id}")
	public ResponseEntity<ResponseStructure<Driver>> deleteDriverbyID(@PathVariable int id) {
		return driverService.deleteDriverbyID(id);
	}
}
