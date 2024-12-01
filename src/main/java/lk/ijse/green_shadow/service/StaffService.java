package lk.ijse.green_shadow.service;

import lk.ijse.green_shadow.dto.StaffStatus;
import lk.ijse.green_shadow.dto.impl.StaffDTO;
import lk.ijse.green_shadow.entity.impl.StaffEntity;

import java.util.List;
import java.util.Optional;

public interface StaffService {
    void saveStaff(StaffDTO staffDTO);

    List<StaffDTO> getAllStaff();

    void deleteStaff(String id);

    void updateStaff(String staffId,StaffDTO staffDTO);

    List<String> getAllStaffNames();

    List<StaffDTO> getStaffListByName(List<String> staffs);

    StaffDTO getStaffByName(String assignedStaff);

    Optional<StaffEntity> findByFirstName(String firstName);
}
