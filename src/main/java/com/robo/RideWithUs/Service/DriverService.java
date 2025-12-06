package com.robo.RideWithUs.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.robo.RideWithUs.DAO.GetLocation;   // member1 added
import com.robo.RideWithUs.DTO.LocationDto;
import com.robo.RideWithUs.DTO.RegisterDriverVehicleDTO;
import com.robo.RideWithUs.DTO.ResponseStructure;
import com.robo.RideWithUs.DTO.UpdateDriverVehicleLocationDTO;
import com.robo.RideWithUs.Entity.Driver;
import com.robo.RideWithUs.Entity.Vehicle;

import com.robo.RideWithUs.Exceptions.DriverNotFoundException;
import com.robo.RideWithUs.Exceptions.DriverNotFoundExceptionForthisNumber;  // member1 added
import com.robo.RideWithUs.Exceptions.VehicleNotFoundException;              // member1 added

import com.robo.RideWithUs.Repository.DriverRepository;
import com.robo.RideWithUs.Repository.VehicleRepository;

@Service
public class DriverService {

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    VehicleRepository vehiclerepository;

    @Autowired
    GetLocation getLocation;

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
        vehicle.setAverageSpeed(driverVehicleDTO.getAverageSpeed());

        String city = getLocation.getLocation(driverVehicleDTO.getLatitude(), driverVehicleDTO.getLongitude());
        vehicle.setCity(city);

        vehicle.setDriver(driver);
        driver.setVehicle(vehicle);

        Driver saveddriver = driverRepository.save(driver);

        ResponseStructure<Driver> responseStructure = new ResponseStructure<>();
        responseStructure.setStatusCode(HttpStatus.ACCEPTED.value());
        responseStructure.setMessage("Driver saved successfully");
        responseStructure.setData(saveddriver);

        return new ResponseEntity<>(responseStructure, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<ResponseStructure<Vehicle>> UpdateDriverVehicleLocation(UpdateDriverVehicleLocationDTO updatelocation) {

        Driver driver = driverRepository.findByMobileNumber(updatelocation.getDriverMobileNumber())
                .orElseThrow(() -> new DriverNotFoundExceptionForthisNumber());

        Vehicle vehicle = driver.getVehicle();
        if (vehicle == null) {
            throw new VehicleNotFoundException();
        }

        String city = getLocation.getLocation(updatelocation.getLatitude(), updatelocation.getLongitude());
        vehicle.setCity(city);

        Vehicle updatedvehicle = vehiclerepository.save(vehicle);

        ResponseStructure<Vehicle> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.ACCEPTED.value());
        response.setMessage("Vehicle Location updated successfully");
        response.setData(updatedvehicle);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<ResponseStructure<Driver>> findbyDriverID(long mobileNo) {

        Driver driver = driverRepository.findByMobileNumber(mobileNo)
                .orElseThrow(() -> new DriverNotFoundExceptionForthisNumber());

        ResponseStructure<Driver> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.FOUND.value());
        response.setMessage("Driver Found successfully");
        response.setData(driver);

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    public ResponseEntity<ResponseStructure<Driver>> deleteDriverbyID(long mobileNo) {

        Driver driver = driverRepository.findByMobileNumber(mobileNo)
                .orElseThrow(() -> new DriverNotFoundExceptionForthisNumber());

        driverRepository.delete(driver);

        ResponseStructure<Driver> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.MOVED_PERMANENTLY.value());
        response.setMessage("Driver deleted successfully");
        response.setData(driver);

        return new ResponseEntity<>(response, HttpStatus.MOVED_PERMANENTLY);
    }

    public ResponseEntity<ResponseStructure<Driver>> findbyDriverID(int id) {

        Driver driver = driverRepository.findById(id).orElseThrow(() -> new DriverNotFoundException());

        ResponseStructure<Driver> responseStructure = new ResponseStructure<>();
        responseStructure.setStatusCode(HttpStatus.FOUND.value());
        responseStructure.setMessage("Driver found successfully");
        responseStructure.setData(driver);

        return new ResponseEntity<>(responseStructure, HttpStatus.FOUND);
    }

    public ResponseEntity<ResponseStructure<Driver>> deleteDriverbyID(int id) {

        Driver driver = driverRepository.findById(id).orElseThrow(() -> new DriverNotFoundException());

        driverRepository.delete(driver);

        ResponseStructure<Driver> responseStructure = new ResponseStructure<>();
        responseStructure.setStatusCode(HttpStatus.FOUND.value());
        responseStructure.setMessage("Driver deleted successfully");
        responseStructure.setData(driver);

        return new ResponseEntity<>(responseStructure, HttpStatus.FOUND);
    }

    public ResponseEntity<ResponseStructure<Driver>> updateDriver(LocationDto dto, int driverId) {

        Driver driver = driverRepository.findById(driverId).orElseThrow(() -> new DriverNotFoundException());

        String city = getLocation.getLocation(dto.getLattitude(), dto.getLongitude());

        Vehicle vehicle = driver.getVehicle();
        vehicle.setCity(city);

        driver.setVehicle(vehicle);
        driverRepository.save(driver);

        ResponseStructure<Driver> responseStructure = new ResponseStructure<>();
        responseStructure.setStatusCode(HttpStatus.ACCEPTED.value());
        responseStructure.setMessage("Driver UPDATED successfully");
        responseStructure.setData(driver);

        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
    }
}
