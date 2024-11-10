package lk.ijse.green_shadow.service;

import lk.ijse.green_shadow.dto.CropStatus;
import lk.ijse.green_shadow.dto.EquipmentStatus;
import lk.ijse.green_shadow.dto.impl.CropDTO;
import lk.ijse.green_shadow.dto.impl.EquipmentDTO;

import java.util.List;

public interface EquipmentService {
    void saveEquipment(EquipmentDTO equipmentDTO);

    List<EquipmentDTO> getAllEquipment();

    EquipmentStatus getEquipment(String equipmentId);

    void deleteEquipment(String equipmentId);

    void updateEquipment(String equipmentId,EquipmentDTO equipmentDTO);
}
