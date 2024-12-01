package lk.ijse.green_shadow.service;

import lk.ijse.green_shadow.dto.CropStatus;
import lk.ijse.green_shadow.dto.EquipmentStatus;
import lk.ijse.green_shadow.dto.impl.CropDTO;
import lk.ijse.green_shadow.dto.impl.EquipmentDTO;
import lk.ijse.green_shadow.entity.impl.EquipmentEntity;

import java.util.List;
import java.util.Optional;

public interface EquipmentService {
    void saveEquipment(EquipmentDTO equipmentDTO);

    List<EquipmentDTO> getAllEquipment();

    EquipmentStatus getEquipment(String equipmentId);

    void deleteEquipment(String equipmentId);

    void updateEquipment(String equipmentId,EquipmentDTO equipmentDTO);

    Optional<EquipmentEntity> findByEquipName(String equipmentName);
}
