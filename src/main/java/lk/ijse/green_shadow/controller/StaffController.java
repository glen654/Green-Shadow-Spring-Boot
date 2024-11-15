package lk.ijse.green_shadow.controller;

import lk.ijse.green_shadow.customStatusCodes.SelectedErrorStatus;
import lk.ijse.green_shadow.dto.StaffStatus;
import lk.ijse.green_shadow.dto.impl.StaffDTO;
import lk.ijse.green_shadow.exception.DataPersistException;
import lk.ijse.green_shadow.exception.StaffNotFoundException;
import lk.ijse.green_shadow.service.StaffService;
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
@RequestMapping("api/v1/staff")
@CrossOrigin
public class StaffController {
    @Autowired
    private StaffService staffService;

    private static Logger logger = LoggerFactory.getLogger(StaffController.class);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveStaff(@RequestBody StaffDTO staffDTO) {
        try {
            staffService.saveStaff(staffDTO);
            logger.info("staff saved");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            logger.warn("Returning Http 400 Bad Request",e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Staff save failed",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public StaffStatus getSelectedStaff(@PathVariable ("id") String id){
        if(!Regex.staffIdMatcher(id)){
            logger.error("Staff Member id does not match");
            return new SelectedErrorStatus(1,"Staff ID does not match");
        }
        logger.info("Get selected staff Member");
        return staffService.getStaff(id);
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StaffDTO> getAllStaff(){
        return staffService.getAllStaff();
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable("id") String id){
        try {
            if(!Regex.staffIdMatcher(id)){
                logger.error("Staff Member id does not match to delete");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            staffService.deleteStaff(id);
            logger.info("staff deleted");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (StaffNotFoundException e){
            e.printStackTrace();
            logger.warn("Staff member not found to delete",e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Staff delete failed",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateStaff(@PathVariable ("id") String id,
                                            @RequestBody StaffDTO staffDTO){

        try {
            if(!Regex.staffIdMatcher(id) || staffDTO == null){
                logger.error("Staff Member id does not match to update");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            staffService.updateStaff(id, staffDTO);
            logger.info("staff updated");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (StaffNotFoundException e){
            e.printStackTrace();
            logger.warn("Staff member not found to update",e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Staff update failed",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
