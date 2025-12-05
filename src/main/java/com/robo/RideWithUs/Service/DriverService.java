package com.robo.RideWithUs.Service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.robo.RideWithUs.DTO.RegisterDriverVehicleDTO;
import com.robo.RideWithUs.DTO.ResponseStructure;
import com.robo.RideWithUs.Entity.Driver;
import com.robo.RideWithUs.Entity.Vehicle;
import com.robo.RideWithUs.Repository.DriverRepository;

@Service
public class DriverService {
	
	@Autowired
	DriverRepository driverRepository;

	public ResponseEntity<ResponseStructure<Driver>> registerDriver(RegisterDriverVehicleDTO driverVehicleDTO) {

		Driver driver = new Driver();

		driver.setLicenseNumber(driverVehicleDTO.getLicenseNumber());
		driver.setUpiID(driverVehicleDTO.getUpiID());
		driver.setDriverName(driverVehicleDTO.getDriverName());
		driver.setAge(driverVehicleDTO.getDriverAge());
		driver.setMobileNumber(driverVehicleDTO.getDriverMobileNumber());
		driver.setGender(driverVehicleDTO.getGender());
		driver.setMailID(driverVehicleDTO.getMailID());

		Vehicle vehicle = new Vehicle();

		
		vehicle.setBrandName(driverVehicleDTO.getVehicleName());
		vehicle.setVehicleNumber(driverVehicleDTO.getVehicleNumber());
		vehicle.setType(driverVehicleDTO.getVehicletype());
		vehicle.setModal(driverVehicleDTO.getVehicleModel());
		vehicle.setCapacity(driverVehicleDTO.getCapacity());
		vehicle.setPricePerKM(driverVehicleDTO.getPriceperKilometer());
		String city = getLocation(driverVehicleDTO.getLatitude(), driverVehicleDTO.getLongitude());
		vehicle.setCity(city);
		
		vehicle.setDriver(driver);  
		driver.setVehicle(vehicle);
		
		Driver saveddriver = driverRepository.save(driver);
		
		ResponseStructure<Driver> responseStructure = new ResponseStructure<Driver>();
		responseStructure.setStatusCode(HttpStatus.ACCEPTED.value());
		responseStructure.setMessage("Driver saved successfully");
		responseStructure.setData(saveddriver);
		
		return new ResponseEntity<ResponseStructure<Driver>>(responseStructure,HttpStatus.ACCEPTED);
		
		

	}

	private final RestTemplate restTemplate = new RestTemplate();
	private final String apikey = "pk.9f97384d7176ae66c2b751ed432be655";

	public String getLocation(double latitude, double longitude) {

		
		String url = UriComponentsBuilder.fromUriString("https://us1.locationiq.com/v1/reverse")
				.queryParam("key", apikey).queryParam("lat", latitude).queryParam("lon", longitude)
				.queryParam("format", "json").build().toUriString();


		System.err.println(url);
		
		try {
		    Map<String, Object> response = restTemplate.getForObject(url, Map.class);
		    System.out.println(response);
		    Map<String, Object> address = (Map<String, Object>) response.get("address");

		   //  Full fallback order
		    String[] keys = {
		        "city", 
		        "town", 
		        "village", 
		        "municipality",
		        "county",
		        "state_district",
		        "suburb"
		    };

		    for (String key : keys) {
		        if (address.get(key) != null) {
		            return address.get(key).toString();
		        }
		    }
//			
		} catch (Exception ex) {
		    System.out.println("ERROR: " + ex.getMessage());
		}

			

		return "Unkown";
	}

}
