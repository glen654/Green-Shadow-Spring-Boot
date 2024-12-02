package lk.ijse.green_shadow.service;

import lk.ijse.green_shadow.dto.CropStatus;
import lk.ijse.green_shadow.dto.impl.CropDTO;
import lk.ijse.green_shadow.entity.impl.CropEntity;

import java.util.List;
import java.util.Optional;

public interface CropService {
    void saveCrop(CropDTO cropDTO);

    List<CropDTO> getAllCrops();

    CropStatus getCrop(String cropCode);

    void deleteCrop(String cropCode);

    void updateCrop(String commonName,CropDTO cropDTO);

    List<String> getAllCropNames();

    List<CropDTO> getCropListByName(List<String> crops);

    Optional<CropEntity> findByCommonName(String commonName);
}
