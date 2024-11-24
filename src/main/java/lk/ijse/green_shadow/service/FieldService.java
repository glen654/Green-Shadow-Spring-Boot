package lk.ijse.green_shadow.service;

import lk.ijse.green_shadow.dto.FieldStatus;
import lk.ijse.green_shadow.dto.impl.FieldDTO;
import lk.ijse.green_shadow.dto.impl.StaffDTO;

import java.util.List;

public interface FieldService {
    void saveField(FieldDTO fieldDTO);

    List<FieldDTO> getAllFields();

    FieldStatus getField(String fieldCode);

    void deleteField(String fieldCode);

    void updateField(String fieldCode,FieldDTO fieldDTO);

    void updateAllocatedStaff(String fieldCode, List<String> staffId);

    List<FieldDTO> getAllFieldNames();
}
