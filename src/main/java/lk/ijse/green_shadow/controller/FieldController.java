package lk.ijse.green_shadow.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.green_shadow.customStatusCodes.SelectedErrorStatus;
import lk.ijse.green_shadow.dto.FieldStatus;
import lk.ijse.green_shadow.dto.impl.CropDTO;
import lk.ijse.green_shadow.dto.impl.FieldDTO;
import lk.ijse.green_shadow.dto.impl.StaffDTO;
import lk.ijse.green_shadow.exception.DataPersistException;
import lk.ijse.green_shadow.exception.FieldNotFoundException;
import lk.ijse.green_shadow.service.FieldService;
import lk.ijse.green_shadow.util.AppUtil;
import lk.ijse.green_shadow.util.Regex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/field")
@CrossOrigin
public class FieldController {
    @Autowired
    private FieldService fieldService;

    private static Logger logger = LoggerFactory.getLogger(FieldController.class);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveField(@RequestPart ("field_name") String fieldName,
                                          @RequestPart ("location") Point location,
                                          @RequestPart ("extent_size") Double extentSize,
                                          @RequestPart ("field_image1") MultipartFile fieldImage1,
                                          @RequestPart ("field_image2") MultipartFile fieldImage2,
                                          @RequestPart ("crops") List<CropDTO> crops,
                                          @RequestPart ("staff") List<StaffDTO> staff
    ) {
        String base64FieldImage1 = "";
        String base64FieldImage2 = "";

        try {
            byte[] bytesFieldImage1 = fieldImage1.getBytes();
            base64FieldImage1 = AppUtil.cropImageToBase64(bytesFieldImage1);

            byte[] bytesFieldImage2 = fieldImage2.getBytes();
            base64FieldImage2 = AppUtil.cropImageToBase64(bytesFieldImage2);

            String field_code = AppUtil.generateFieldId();

            FieldDTO buildFieldDTO = new FieldDTO();
            buildFieldDTO.setField_code(field_code);
            buildFieldDTO.setField_name(fieldName);
            buildFieldDTO.setLocation(location);
            buildFieldDTO.setExtent_size(extentSize);
            buildFieldDTO.setField_image1(base64FieldImage1);
            buildFieldDTO.setField_image2(base64FieldImage2);
            buildFieldDTO.setCrops(crops);
            buildFieldDTO.setAllocated_staff(staff);
            fieldService.saveField(buildFieldDTO);
            logger.info("Save field successful");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            logger.warn("Returning Http 400 Bad Request",e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Field save unsuccessful",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{fieldCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldStatus getSelectedField(@PathVariable ("fieldCode") String fieldCode) {
        if(!Regex.fieldCodeMatcher(fieldCode)){
            logger.error("Invalid field code");
            return new SelectedErrorStatus(1,"Field Code Not Matched");
        }
        return fieldService.getField(fieldCode);
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FieldDTO> getAllFields() {
        return fieldService.getAllFields();
    }
    @DeleteMapping(value = "/{fieldCode}")
    public ResponseEntity<Void> deleteField(@PathVariable("fieldCode") String fieldCode) {
        try {
            if(!Regex.fieldCodeMatcher(fieldCode)){
                logger.error("Invalid field code to delete");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            fieldService.deleteField(fieldCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (FieldNotFoundException e){
            e.printStackTrace();
            logger.warn("Field not found to delete",e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Field not deleted",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping(value = "/{fieldCode}")
    public ResponseEntity<Void> updateField(@PathVariable ("fieldCode") String fieldCode,
                                            @RequestBody FieldDTO fieldDTO) {

        try {
            if(!Regex.fieldCodeMatcher(fieldCode) || fieldDTO == null){
                logger.error("Invalid field code to update");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            fieldService.updateField(fieldCode, fieldDTO);
            logger.info("Update field successful");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (FieldNotFoundException e){
            e.printStackTrace();
            logger.warn("Field not found to update",e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Field not updated",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
