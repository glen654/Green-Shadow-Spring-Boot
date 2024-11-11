package lk.ijse.green_shadow.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.green_shadow.customStatusCodes.SelectedErrorStatus;
import lk.ijse.green_shadow.dao.CropDao;
import lk.ijse.green_shadow.dto.CropStatus;
import lk.ijse.green_shadow.dto.impl.CropDTO;
import lk.ijse.green_shadow.entity.impl.CropEntity;
import lk.ijse.green_shadow.exception.CropNotFoundException;
import lk.ijse.green_shadow.exception.DataPersistException;
import lk.ijse.green_shadow.service.CropService;
import lk.ijse.green_shadow.util.AppUtil;
import lk.ijse.green_shadow.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CropServiceImpl implements CropService {
    @Autowired
    private CropDao cropDao;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveCrop(CropDTO cropDTO) {
        cropDTO.setCrop_code(AppUtil.generateCropId());
        CropEntity saveCrop = cropDao.save(mapping.toCropEntity(cropDTO));
        if(saveCrop == null) {
            throw new DataPersistException("Crop not saved");
        }
    }

    @Override
    public List<CropDTO> getAllCrops() {
       return mapping.toCropDTOList(cropDao.findAll());
    }

    @Override
    public CropStatus getCrop(String cropCode) {
        if(cropDao.existsById(cropCode)) {
            var selectedCrop = cropDao.getReferenceById(cropCode);
            return mapping.toCropDTO(selectedCrop);
        }else {
            return new SelectedErrorStatus(2,"Selected crop does not exist");
        }
    }

    @Override
    public void deleteCrop(String cropCode) {
        Optional<CropEntity> foundCrop = cropDao.findById(cropCode);
        if(foundCrop.isPresent()) {
            throw new CropNotFoundException("Crop not found");
        }else {
            cropDao.deleteById(cropCode);
        }
    }

    @Override
    public void updateCrop(String cropCode, CropDTO cropDTO) {
        Optional<CropEntity> tmpCrop = cropDao.findById(cropCode);
        if(!tmpCrop.isPresent()) {
            throw new CropNotFoundException("Crop not found");
        }else {
            tmpCrop.get().setCommon_name(cropDTO.getCommon_name());
            tmpCrop.get().setScientific_name(cropDTO.getScientific_name());
            tmpCrop.get().setCrop_image(cropDTO.getCrop_image());
            tmpCrop.get().setCategory(cropDTO.getCategory());
            tmpCrop.get().setSeason(cropDTO.getSeason());
        }
    }
}
