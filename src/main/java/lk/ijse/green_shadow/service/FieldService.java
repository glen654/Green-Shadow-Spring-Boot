package lk.ijse.green_shadow.service;

import lk.ijse.green_shadow.dto.FieldStatus;
import lk.ijse.green_shadow.dto.impl.FieldDTO;

import java.util.List;

public interface FieldService {
    void saveField(FieldDTO fieldDTO);

    List<FieldDTO> getAllFields();

    FieldStatus getField(String fieldCode);

    void deleteField(String fieldCode);

    void updateField(String fieldName,FieldDTO fieldDTO);

    void updateAllocatedStaff(String fieldCode, List<String> staffId);

    List<String> getAllFieldNames();

    FieldDTO getFieldByName(String field_name);

    List<FieldDTO> getFieldListByName(List<String> field_name);
}
