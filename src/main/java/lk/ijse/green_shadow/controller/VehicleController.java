package lk.ijse.green_shadow.controller;

import lk.ijse.green_shadow.customStatusCodes.SelectedErrorStatus;
import lk.ijse.green_shadow.dto.VehicleStatus;
import lk.ijse.green_shadow.dto.impl.StaffDTO;
import lk.ijse.green_shadow.dto.impl.VehicleDTO;
import lk.ijse.green_shadow.exception.DataPersistException;
import lk.ijse.green_shadow.exception.VehicleNotFoundException;
import lk.ijse.green_shadow.service.FieldService;
import lk.ijse.green_shadow.service.StaffService;
import lk.ijse.green_shadow.service.VehicleService;
import lk.ijse.green_shadow.util.Regex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/vehicle")
@CrossOrigin
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private StaffService staffService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveVehicle(@RequestBody VehicleDTO vehicleDTO) {
        try {
            StaffDTO staff = staffService.getStaffByName(vehicleDTO.getAssigned_staff().getFirst_name());
            vehicleDTO.setAssigned_staff(staff);
            vehicleService.saveVehicle(vehicleDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = {"getvehiclecode","/{licenseNumber}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSelectedVehicle(@PathVariable ("licenseNumber") String licenseNumber) {
        try{
            VehicleDTO vehicleDTO = vehicleService.getVehicleByLicenseNumber(licenseNumber);
            if (vehicleDTO != null) {
                return ResponseEntity.ok(vehicleDTO.getVehicle_code());
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VehicleDTO> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @DeleteMapping(value = "/{vehicleCode}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("vehicleCode") String vehicleCode) {
        try {
            if(!Regex.vehicleCodeMatcher(vehicleCode)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            vehicleService.deleteVehicle(vehicleCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (VehicleNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "/{licenseNumber}")
    public ResponseEntity<Void> updateVehicle(@PathVariable ("licenseNumber") String licenseNumber,
                                              @RequestBody VehicleDTO vehicleDTO) {

        try {
            if(!Regex.vehicleLicenseMatcher(licenseNumber) || vehicleDTO == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            StaffDTO staff = staffService.getStaffByName(vehicleDTO.getAssigned_staff().getFirst_name());
            vehicleDTO.setAssigned_staff(staff);
            vehicleService.updateVehicle(licenseNumber, vehicleDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (VehicleNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
