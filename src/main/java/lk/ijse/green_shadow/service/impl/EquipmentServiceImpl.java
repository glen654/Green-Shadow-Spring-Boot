package lk.ijse.green_shadow.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.green_shadow.customStatusCodes.SelectedErrorStatus;
import lk.ijse.green_shadow.dao.EquipmentDao;
import lk.ijse.green_shadow.dao.FieldDao;
import lk.ijse.green_shadow.dao.StaffDao;
import lk.ijse.green_shadow.dto.EquipmentStatus;
import lk.ijse.green_shadow.dto.impl.EquipmentDTO;
import lk.ijse.green_shadow.dto.impl.FieldDTO;
import lk.ijse.green_shadow.dto.impl.StaffDTO;
import lk.ijse.green_shadow.entity.impl.EquipmentEntity;
import lk.ijse.green_shadow.entity.impl.FieldEntity;
import lk.ijse.green_shadow.entity.impl.StaffEntity;
import lk.ijse.green_shadow.exception.DataPersistException;
import lk.ijse.green_shadow.exception.EquipmentNotFoundException;
import lk.ijse.green_shadow.service.EquipmentService;
import lk.ijse.green_shadow.util.AppUtil;
import lk.ijse.green_shadow.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private EquipmentDao equipmentDao;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveEquipment(EquipmentDTO equipmentDTO) {
        equipmentDTO.setEquipment_id(AppUtil.generateEquipmentId());
        EquipmentEntity saveEquipment = equipmentDao.save(mapping.toEquipmentEntity(equipmentDTO));
        if(saveEquipment == null) {
            throw new DataPersistException("Equipment not saved");
        }
    }

    @Override
    public List<EquipmentDTO> getAllEquipment() {
        List<EquipmentEntity> equipments = equipmentDao.findAll();
        return equipments.stream()
                .map(equipment -> {
                    EquipmentDTO equipmentDTO = new EquipmentDTO();
                    equipmentDTO.setName(equipment.getName());
                    equipmentDTO.setType(equipment.getType());
                    equipmentDTO.setStatus(equipment.getStatus());
                    StaffDTO staffDTO = Optional.ofNullable(equipment.getAssigned_staff())
                            .map(staff -> {
                                StaffDTO minimalStaffDto = new StaffDTO();
                                minimalStaffDto.setFirst_name(staff.getFirst_name());
                                return minimalStaffDto;
                            })
                            .orElse(null);
                    FieldDTO fieldDTO = Optional.ofNullable(equipment.getAssigned_field())
                            .map(field -> {
                                FieldDTO minimalFieldDTO = new FieldDTO();
                                minimalFieldDTO.setField_name(field.getField_name());
                                return minimalFieldDTO;
                            })
                            .orElse(null);
                    equipmentDTO.setAssigned_staff(staffDTO);
                    equipmentDTO.setAssigned_field(fieldDTO);
                    return equipmentDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public EquipmentStatus getEquipment(String equipmentId) {
        if(equipmentDao.existsById(equipmentId)) {
            var selectedEquipment = equipmentDao.getReferenceById(equipmentId);
            return mapping.toEquipmentDTO(selectedEquipment);
        }else{
            return new SelectedErrorStatus(2,"Selected Equipment not found");
        }
    }

    @Override
    public void deleteEquipment(String equipmentId) {
        Optional<EquipmentEntity> foundEquipment = equipmentDao.findById(equipmentId);
        if(!foundEquipment.isPresent()) {
            throw new EquipmentNotFoundException("Equipment not found");
        }else{
            equipmentDao.deleteById(equipmentId);
        }
    }

    @Override
    public void updateEquipment(String equipmentId, EquipmentDTO equipmentDTO) {
        Optional<EquipmentEntity> tmpEquipment = equipmentDao.findById(equipmentId);
        if(!tmpEquipment.isPresent()) {
            throw new EquipmentNotFoundException("Equipment not found");
        }else{
            tmpEquipment.get().setName(equipmentDTO.getName());
            tmpEquipment.get().setType(equipmentDTO.getType());
            tmpEquipment.get().setStatus(equipmentDTO.getStatus());
            StaffEntity staffEntity = mapping.toStaffEntity(equipmentDTO.getAssigned_staff());
            tmpEquipment.get().setAssigned_staff(staffEntity);
            FieldEntity fieldEntity = mapping.toFieldEntity(equipmentDTO.getAssigned_field());
            tmpEquipment.get().setAssigned_field(fieldEntity);
        }
    }

    @Override
    public Optional<EquipmentEntity> findByEquipName(String equipmentName) {
        return equipmentDao.findByEquipmentName(equipmentName);
    }
}
