package lk.ijse.green_shadow.service;

import lk.ijse.green_shadow.dto.CropStatus;
import lk.ijse.green_shadow.dto.StaffStatus;
import lk.ijse.green_shadow.dto.impl.CropDTO;
import lk.ijse.green_shadow.dto.impl.StaffDTO;

import java.util.List;

public interface StaffService {
    void saveStaff(StaffDTO staffDTO);

    List<StaffDTO> getAllStaff();

    StaffStatus getStaff(String id);

    void deleteStaff(String id);

    void updateStaff(String id,StaffDTO staffDTO);
    List<String> getAllStaffNames();
}
