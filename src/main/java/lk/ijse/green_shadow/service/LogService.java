package lk.ijse.green_shadow.service;

import lk.ijse.green_shadow.dto.EquipmentStatus;
import lk.ijse.green_shadow.dto.MonitoringLogStatus;
import lk.ijse.green_shadow.dto.impl.EquipmentDTO;
import lk.ijse.green_shadow.dto.impl.MonitoringLogDTO;

import java.util.List;

public interface LogService {
    void saveLog(MonitoringLogDTO monitoringLogDTO);

    List<MonitoringLogDTO> getAllLogs();

    MonitoringLogStatus getLog(String logCode);

    void deleteLog(String logCode);

    void updateLog(String logCode,MonitoringLogDTO monitoringLogDTO);
}
