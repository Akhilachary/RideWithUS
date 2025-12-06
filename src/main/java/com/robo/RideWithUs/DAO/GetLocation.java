package com.robo.RideWithUs.DAO;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.robo.RideWithUs.Exceptions.APIwillNotGivingTheLocationException;
@Service
public class GetLocation {

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
		    
		    if (response == null || response.get("address") == null) {
	            throw new APIwillNotGivingTheLocationException();
	        }
		    
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
		
		} catch (Exception ex) {
		    System.out.println("ERROR: " + ex.getMessage());
		}

			

		return "Unkown";
	}
	
	
	public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

	    final int EARTH_RADIUS_KM = 6371; // Radius of earth in KM

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);

	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

	    return EARTH_RADIUS_KM * c; // distance in KM
	}
	
	
	public int calculateFare(double distance, double pricePerKM) {
	    return (int) Math.ceil(distance * pricePerKM);
	}


}
