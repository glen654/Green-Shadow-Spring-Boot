package lk.ijse.green_shadow.controller;

import lk.ijse.green_shadow.customStatusCodes.SelectedErrorStatus;
import lk.ijse.green_shadow.dto.EquipmentStatus;
import lk.ijse.green_shadow.dto.impl.EquipmentDTO;
import lk.ijse.green_shadow.exception.DataPersistException;
import lk.ijse.green_shadow.exception.EquipmentNotFoundException;
import lk.ijse.green_shadow.service.EquipmentService;
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
@RequestMapping("api/v1/equipment")
@CrossOrigin
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;

    private static Logger logger = LoggerFactory.getLogger(EquipmentController.class);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveEquipment(@RequestBody EquipmentDTO equipmentDTO) {
        try {
            equipmentService.saveEquipment(equipmentDTO);
            logger.info("Save equipment successful");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            logger.warn("Returning Http 400 Bad Request",e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Save equipment failed",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{equipmentId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public EquipmentStatus getSelectedEquipment(@PathVariable ("equipmentId") String equipmentId) {
        if (!Regex.equipIdMatcher(equipmentId)) {
            logger.error("Equipment Id is not valid");
            return new SelectedErrorStatus(1, "Equipment id does not match");
        }
        return equipmentService.getEquipment(equipmentId);
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EquipmentDTO> getAllEquipments() {
        return equipmentService.getAllEquipment();
    }
    @DeleteMapping(value = "/{equipmentId}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable("equipmentId") String equipmentId) {
        try {
            if(!Regex.equipIdMatcher(equipmentId)) {
                logger.error("Equipment Id is not valid to delete");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            equipmentService.deleteEquipment(equipmentId);
            logger.info("Delete equipment successful");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EquipmentNotFoundException e){
            e.printStackTrace();
            logger.warn("Equipment not found to delete",e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Delete equipment failed",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(value = "/{equipmentId}")
    public ResponseEntity<Void> updateEquipment(@PathVariable ("equipmentId") String equipmentId,
                                                @RequestBody EquipmentDTO equipmentDTO) {

        try {
            if(!Regex.equipIdMatcher(equipmentId) || equipmentDTO == null) {
                logger.error("Equipment Id is not valid to update");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            equipmentService.updateEquipment(equipmentId, equipmentDTO);
            logger.info("Update equipment successful");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EquipmentNotFoundException e){
            e.printStackTrace();
            logger.warn("Equipment not found to update",e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Update equipment failed",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
