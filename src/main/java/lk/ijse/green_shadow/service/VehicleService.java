package lk.ijse.green_shadow.service;

import lk.ijse.green_shadow.dto.EquipmentStatus;
import lk.ijse.green_shadow.dto.VehicleStatus;
import lk.ijse.green_shadow.dto.impl.EquipmentDTO;
import lk.ijse.green_shadow.dto.impl.VehicleDTO;

import java.util.List;

public interface VehicleService {
    void saveVehicle(VehicleDTO vehicleDTO);

    List<VehicleDTO> getAllVehicles();

    VehicleStatus getVehicle(String vehicleCode);

    void deleteVehicle(String vehicleCode);

    void updateVehicle(String licenseNumber,VehicleDTO vehicleDTO);

    VehicleDTO getVehicleByLicenseNumber(String licenseNumber);
}
