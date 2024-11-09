package lk.ijse.green_shadow.controller;

import lk.ijse.green_shadow.dto.CropStatus;
import lk.ijse.green_shadow.dto.impl.CropDTO;
import lk.ijse.green_shadow.dto.impl.FieldDTO;
import lk.ijse.green_shadow.exception.DataPersistException;
import lk.ijse.green_shadow.service.CropService;
import lk.ijse.green_shadow.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/v1/crop")
public class CropController {
    @Autowired
    private CropService cropService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCrop(@RequestPart ("common_name") String commonName,
                                         @RequestPart ("scientific_name") String scientificName,
                                         @RequestPart ("crop_image") MultipartFile cropImage,
                                         @RequestPart ("category") String category,
                                         @RequestPart ("season") String season,
                                         @RequestPart ("field")FieldDTO fieldDTO
    ){
        String base64CropImage = "";

        try {
            byte[] bytesCropImage = cropImage.getBytes();
            base64CropImage = AppUtil.cropImageToBase64(bytesCropImage);

            String crop_code = AppUtil.generateCropId();

            CropDTO buildCropDTO = new CropDTO();
            buildCropDTO.setCrop_code(crop_code);
            buildCropDTO.setCommon_name(commonName);
            buildCropDTO.setScientific_name(scientificName);
            buildCropDTO.setCrop_image(base64CropImage);
            buildCropDTO.setCategory(category);
            buildCropDTO.setSeason(season);
            buildCropDTO.setField(fieldDTO);
            cropService.saveCrop(buildCropDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CropStatus getSelectedCrop(@PathVariable ("crop_code") String crop_code){

    }
}
