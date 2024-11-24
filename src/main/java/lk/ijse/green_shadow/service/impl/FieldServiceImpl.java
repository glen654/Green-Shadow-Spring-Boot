package lk.ijse.green_shadow.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.green_shadow.customStatusCodes.SelectedErrorStatus;
import lk.ijse.green_shadow.dao.FieldDao;
import lk.ijse.green_shadow.dao.StaffDao;
import lk.ijse.green_shadow.dto.FieldStatus;
import lk.ijse.green_shadow.dto.impl.FieldDTO;
import lk.ijse.green_shadow.dto.impl.StaffDTO;
import lk.ijse.green_shadow.entity.impl.CropEntity;
import lk.ijse.green_shadow.entity.impl.FieldEntity;
import lk.ijse.green_shadow.entity.impl.StaffEntity;
import lk.ijse.green_shadow.exception.FieldNotFoundException;
import lk.ijse.green_shadow.exception.StaffNotFoundException;
import lk.ijse.green_shadow.service.FieldService;
import lk.ijse.green_shadow.util.AppUtil;
import lk.ijse.green_shadow.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FieldServiceImpl implements FieldService {
    @Autowired
    private FieldDao fieldDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private Mapping mapping;
    @Override
    public void saveField(FieldDTO fieldDTO) {
        fieldDTO.setField_code(AppUtil.generateFieldId());
        FieldEntity fieldEntity = fieldDao.save(mapping.toFieldEntity(fieldDTO));
        if (fieldEntity == null) {
            throw new FieldNotFoundException("Field not found");
        }
    }

    @Override
    public List<FieldDTO> getAllFields() {
        return mapping.toFieldDTOList(fieldDao.findAll());
    }

    @Override
    public FieldStatus getField(String fieldCode) {
        if(fieldDao.existsById(fieldCode)){
            var selectedField = fieldDao.getReferenceById(fieldCode);
            return mapping.toFieldDTO(selectedField);
        }else {
            return new SelectedErrorStatus(2,"Selected field not found");
        }
    }

    @Override
    public void deleteField(String fieldCode) {
        Optional<FieldEntity> foundField = fieldDao.findById(fieldCode);
        if(foundField.isPresent()){
            throw new FieldNotFoundException("Field not found");
        }else {
            fieldDao.deleteById(fieldCode);
        }
    }

    @Override
    public void updateField(String fieldCode, FieldDTO fieldDTO) {
        Optional<FieldEntity> tmpField = fieldDao.findById(fieldCode);
        if(!tmpField.isPresent()){
            throw new FieldNotFoundException("Field not found");
        }else{
            tmpField.get().setField_name(fieldDTO.getField_name());
            tmpField.get().setLocation(fieldDTO.getLocation());
            tmpField.get().setExtent_size(fieldDTO.getExtent_size());
            tmpField.get().setField_image1(fieldDTO.getField_image1());
            tmpField.get().setField_image2(fieldDTO.getField_image2());
            List<CropEntity> cropEntityList = mapping.toCropEntityList(fieldDTO.getCrops());
            tmpField.get().setCrops(cropEntityList);
            List<StaffEntity> staffEntityList = mapping.toStaffEntityList(fieldDTO.getAllocated_staff());
            tmpField.get().setAllocated_staff(staffEntityList);
        }
    }

    @Override
    public void updateAllocatedStaff(String fieldCode,List<String> staffId) {
        Optional<FieldEntity> tmpField = fieldDao.findById(fieldCode);
        if(!tmpField.isPresent()){
            throw new FieldNotFoundException("Field not found");
        }
        FieldEntity fieldEntity = tmpField.get();
        for(String staff : staffId){
            Optional<StaffEntity> tmpStaff = staffDao.findById(staff);
            if(!tmpStaff.isPresent()){
                throw new StaffNotFoundException("Staff not found");
            }else{
                StaffEntity staffEntity = tmpStaff.get();
                fieldEntity.getAllocated_staff().add(staffEntity);
                staffEntity.getFields().add(fieldEntity);
                staffDao.save(staffEntity);
            }
        }
        fieldDao.save(fieldEntity);
    }

    @Override
    public List<FieldDTO> getAllFieldNames() {
        List<FieldEntity> fieldEntities = fieldDao.findAll();
        return fieldEntities.stream()
                .map(fieldEntity -> mapping.toFieldDTO(fieldEntity))
                .collect(Collectors.toList());
    }

}
