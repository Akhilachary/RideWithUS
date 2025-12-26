package com.robo.RideWithUs.DAO;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.robo.RideWithUs.DTO.DestinationLocationResponse;
import com.robo.RideWithUs.Exceptions.APIwillNotGivingTheLocationException;
import com.robo.RideWithUs.Exceptions.IncorrectLocationException;
import com.robo.RideWithUs.Exceptions.LocationIQErrorForCityNameException;
import com.robo.RideWithUs.Exceptions.LocationNotFoundForCityNameException;

@Service
public class GetLocation {

	private final RestTemplate restTemplate = new RestTemplate();
	private final String apikey = "pk.9f97384d7176ae66c2b751ed432be655";

	public String getLocation(double latitude, double longitude) {

		String url = UriComponentsBuilder.fromUriString("https://us1.locationiq.com/v1/reverse")
				.queryParam("key", apikey)
				.queryParam("lat", latitude)
				.queryParam("lon", longitude)
				.queryParam("format", "json")
				.build()
				.toUriString();

		try {
			Map<String, Object> response = restTemplate.getForObject(url, Map.class);

			if (response == null || response.get("address") == null) {
				throw new APIwillNotGivingTheLocationException();
			}

			Map<String, Object> address = (Map<String, Object>) response.get("address");

			String[] keys = { "city", "town", "village", "municipality", "county", "state_district", "suburb" };

			for (String key : keys) {
				if (address.get(key) != null) {
					return address.get(key).toString();
				}
			}

		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}

		throw new IncorrectLocationException();
	}

	private static final String API_KEY1 = "pk.9f97384d7176ae66c2b751ed432be655";
	private static final String BASE_URL1 = "https://us1.locationiq.com/v1/search";

	public DestinationLocationResponse getCoordinates1(String cityName) {

		String url = BASE_URL1 + "?key=" + API_KEY1 + "&q=" + cityName + "&format=json&limit=1";

		try {
			ResponseEntity<String> rawResponse = restTemplate.getForEntity(url, String.class);
			System.err.println("RAW LOCATIONIQ RESPONSE = " + rawResponse.getBody());

			DestinationLocationResponse[] response =
					restTemplate.getForObject(url, DestinationLocationResponse[].class);

			if (response != null && response.length > 0) {
				return response[0];
			} else {
				throw new LocationNotFoundForCityNameException();
			}

		} catch (Exception e) {
			throw new LocationIQErrorForCityNameException();
		}
	}

	private static final String API_KEY2 = "pk.1d09567f3559e6ccaeff4bf00639b9fd";
	private static final String BASE_URL2 = "https://us1.locationiq.com/v1/search";

	public DestinationLocationResponse getCoordinates2(String cityName) {

		String url = BASE_URL2 + "?key=" + API_KEY2 + "&q=" + cityName + "&format=json&limit=1";

		try {
			ResponseEntity<String> rawResponse = restTemplate.getForEntity(url, String.class);
			System.err.println("RAW LOCATIONIQ RESPONSE = " + rawResponse.getBody());

			DestinationLocationResponse[] response =
					restTemplate.getForObject(url, DestinationLocationResponse[].class);

			if (response != null && response.length > 0) {
				return response[0];
			} else {
				throw new LocationNotFoundForCityNameException();
			}

		} catch (Exception e) {
			throw new LocationIQErrorForCityNameException();
		}
	}
}
