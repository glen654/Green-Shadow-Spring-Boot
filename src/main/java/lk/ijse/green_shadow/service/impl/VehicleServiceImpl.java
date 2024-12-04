package lk.ijse.green_shadow.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.green_shadow.customStatusCodes.SelectedErrorStatus;
import lk.ijse.green_shadow.dao.StaffDao;
import lk.ijse.green_shadow.dao.VehicleDao;
import lk.ijse.green_shadow.dto.VehicleStatus;
import lk.ijse.green_shadow.dto.impl.StaffDTO;
import lk.ijse.green_shadow.dto.impl.VehicleDTO;
import lk.ijse.green_shadow.entity.impl.StaffEntity;
import lk.ijse.green_shadow.entity.impl.VehicleEntity;
import lk.ijse.green_shadow.exception.DataPersistException;
import lk.ijse.green_shadow.exception.VehicleNotFoundException;
import lk.ijse.green_shadow.service.VehicleService;
import lk.ijse.green_shadow.util.AppUtil;
import lk.ijse.green_shadow.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleDao vehicleDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveVehicle(VehicleDTO vehicleDTO) {
        vehicleDTO.setVehicle_code(AppUtil.generateVehicleId());
        VehicleEntity saveVehicle = vehicleDao.save(mapping.toVehicleEntity(vehicleDTO));
        if(saveVehicle == null) {
            throw new DataPersistException("Vehicle not saved");
        }
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        List<VehicleEntity> vehicles = vehicleDao.findAll();
        return vehicles.stream()
                .map(vehicle -> {
                    VehicleDTO vehicleDTO = new VehicleDTO();
                    vehicleDTO.setLicensePlateNumber(vehicle.getLicensePlateNumber());
                    vehicleDTO.setVehicleCategory(vehicle.getVehicleCategory());
                    vehicleDTO.setFuelType(vehicle.getFuelType());
                    vehicleDTO.setStatus(vehicle.getStatus());
                    vehicleDTO.setRemarks(vehicle.getRemarks());
                    Optional<StaffEntity> assignedStaff = staffDao.findById(vehicle.getAssigned_staff().getId());
                    StaffDTO assignedStaffDTO = assignedStaff.isPresent() ?
                            mapping.toStaffDTO(assignedStaff.get()) : null;
                    vehicleDTO.setAssigned_staff(assignedStaffDTO);
                    return vehicleDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VehicleStatus getVehicle(String vehicleCode) {
        if(vehicleDao.existsById(vehicleCode)) {
            var selectedVehicle = vehicleDao.getReferenceById(vehicleCode);
            return mapping.toVehicleDTO(selectedVehicle);
        }else {
            return new SelectedErrorStatus(2,"Selected Vehicle Not Found");
        }
    }

    @Override
    public void deleteVehicle(String vehicleCode) {
        Optional<VehicleEntity> foundVehicle = vehicleDao.findById(vehicleCode);
        if(!foundVehicle.isPresent()) {
            throw new VehicleNotFoundException("Vehicle Not Found");
        }else{
            vehicleDao.deleteById(vehicleCode);
        }
    }

    @Override
    public void updateVehicle(String vehicleCode, VehicleDTO vehicleDTO) {
        Optional<VehicleEntity> tmpVehicle = vehicleDao.findById(vehicleCode);
        if(!tmpVehicle.isPresent()) {
            throw new VehicleNotFoundException("Vehicle Not Found");
        }else {
            tmpVehicle.get().setLicensePlateNumber(vehicleDTO.getLicensePlateNumber());
            tmpVehicle.get().setVehicleCategory(vehicleDTO.getVehicleCategory());
            tmpVehicle.get().setFuelType(vehicleDTO.getFuelType());
            tmpVehicle.get().setStatus(vehicleDTO.getStatus());
            tmpVehicle.get().setRemarks(vehicleDTO.getRemarks());
            StaffEntity staffEntity = mapping.toStaffEntity(vehicleDTO.getAssigned_staff());
            tmpVehicle.get().setAssigned_staff(staffEntity);
        }
    }

    @Override
    public Optional<VehicleEntity> findByLicenseNumber(String licenseNumber) {
        return vehicleDao.findByLicenseNumber(licenseNumber);
    }
}
