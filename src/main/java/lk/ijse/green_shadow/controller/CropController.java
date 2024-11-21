package lk.ijse.green_shadow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.green_shadow.customStatusCodes.SelectedErrorStatus;
import lk.ijse.green_shadow.dto.CropStatus;
import lk.ijse.green_shadow.dto.impl.CropDTO;
import lk.ijse.green_shadow.dto.impl.FieldDTO;
import lk.ijse.green_shadow.exception.CropNotFoundException;
import lk.ijse.green_shadow.exception.DataPersistException;
import lk.ijse.green_shadow.service.CropService;
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

import java.util.List;


@RestController
@RequestMapping("api/v1/crop")
@CrossOrigin
public class CropController {
    @Autowired
    private CropService cropService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCrop(@RequestParam ("common_name") String commonName,
                                         @RequestParam ("scientific_name") String scientificName,
                                         @RequestPart ("crop_image") MultipartFile cropImage,
                                         @RequestParam ("category") String category,
                                         @RequestParam ("season") String season,
                                         @RequestParam ("field") String fieldDTO
    ){
        String base64CropImage = "";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FieldDTO field = objectMapper.readValue(fieldDTO, FieldDTO.class);
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
            buildCropDTO.setField(field);
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
    @GetMapping(value = "/{cropCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CropStatus getSelectedCrop(@PathVariable ("crop_code") String crop_code){
        if(!Regex.cropCodeMatcher(crop_code)){
            return new SelectedErrorStatus(1,"Crop code is invalid");
        }
        return cropService.getCrop(crop_code);
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CropDTO> getAllCrops(){
        return cropService.getAllCrops();
    }
    @DeleteMapping(value = "/{cropCode}")
    public ResponseEntity<Void> deleteCrop(@PathVariable ("crop_code") String crop_code){
        try {
            if(!Regex.cropCodeMatcher(crop_code)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            cropService.deleteCrop(crop_code);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (CropNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping(value = "/{cropCode}")
    public ResponseEntity<Void> updateCrop(@PathVariable ("cropCode") String cropCode,
                                           @RequestBody CropDTO cropDTO){

        try {
            if(!Regex.cropCodeMatcher(cropCode) || cropDTO ==null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            cropService.updateCrop(cropCode, cropDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (CropNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
