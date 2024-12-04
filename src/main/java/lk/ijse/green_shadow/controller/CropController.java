package lk.ijse.green_shadow.controller;

import lk.ijse.green_shadow.customStatusCodes.SelectedErrorStatus;
import lk.ijse.green_shadow.dto.CropStatus;
import lk.ijse.green_shadow.dto.impl.CropDTO;
import lk.ijse.green_shadow.dto.impl.FieldDTO;
import lk.ijse.green_shadow.entity.impl.CropEntity;
import lk.ijse.green_shadow.entity.impl.VehicleEntity;
import lk.ijse.green_shadow.exception.CropNotFoundException;
import lk.ijse.green_shadow.exception.DataPersistException;
import lk.ijse.green_shadow.exception.VehicleNotFoundException;
import lk.ijse.green_shadow.service.CropService;
import lk.ijse.green_shadow.service.FieldService;
import lk.ijse.green_shadow.util.AppUtil;
import lk.ijse.green_shadow.util.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/v1/crop")
@CrossOrigin
public class CropController {
    @Autowired
    private CropService cropService;
    @Autowired
    private FieldService fieldService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCrop(@RequestParam ("common_name") String commonName,
                                         @RequestParam ("scientific_name") String scientificName,
                                         @RequestPart ("crop_image") MultipartFile cropImage,
                                         @RequestParam ("category") String category,
                                         @RequestParam ("season") String season,
                                         @RequestParam ("field_name") String field_name
    ){
        String base64CropImage = "";

        try {
            FieldDTO field = fieldService.getFieldByName(field_name);
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
    public ResponseEntity<Void> deleteCrop(@PathVariable ("cropCode") String cropCode){
        try {
            if(!Regex.cropCodeMatcher(cropCode)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            cropService.deleteCrop(cropCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (CropNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{commonName}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateCrop(@PathVariable @RequestParam ("common_name") String commonName,
                                           @RequestParam ("scientific_name") String scientificName,
                                           @RequestPart ("crop_image") MultipartFile cropImage,
                                           @RequestParam ("category") String category,
                                           @RequestParam ("season") String season,
                                           @RequestParam ("field_name") String field_name
    ){
        String base64CropImage = "";

        try {
            FieldDTO field = fieldService.getFieldByName(field_name);
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
            cropService.updateCrop(commonName,buildCropDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping(value = "getallcropnames",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getAllCropName(){
        List<String> cropNames = cropService.getAllCropNames();
        return ResponseEntity.ok(cropNames);
    }
    @GetMapping("/getcropcode/{commonName}")
    public ResponseEntity<String> getCropCode(@PathVariable("commonName") String commonName){
        try {
            Optional<CropEntity> cropEntity = cropService.findByCommonName(commonName);
            return ResponseEntity.ok(cropEntity.get().getCrop_code());
        }catch (CropNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
