package lk.ijse.green_shadow.controller;

import lk.ijse.green_shadow.dto.impl.CropDTO;
import lk.ijse.green_shadow.dto.impl.FieldDTO;
import lk.ijse.green_shadow.dto.impl.StaffDTO;
import lk.ijse.green_shadow.exception.DataPersistException;
import lk.ijse.green_shadow.service.FieldService;
import lk.ijse.green_shadow.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/field")
public class FieldController {
    @Autowired
    private FieldService fieldService;

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
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
