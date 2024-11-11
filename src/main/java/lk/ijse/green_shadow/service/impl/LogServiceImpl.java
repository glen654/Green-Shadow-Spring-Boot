package lk.ijse.green_shadow.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.green_shadow.customStatusCodes.SelectedErrorStatus;
import lk.ijse.green_shadow.dao.MonitoringLogDao;
import lk.ijse.green_shadow.dto.MonitoringLogStatus;
import lk.ijse.green_shadow.dto.impl.MonitoringLogDTO;
import lk.ijse.green_shadow.entity.impl.MonitoringLogEntity;
import lk.ijse.green_shadow.exception.DataPersistException;
import lk.ijse.green_shadow.exception.LogNotFoundException;
import lk.ijse.green_shadow.service.LogService;
import lk.ijse.green_shadow.util.AppUtil;
import lk.ijse.green_shadow.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Autowired
    private MonitoringLogDao monitoringLogDao;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveLog(MonitoringLogDTO monitoringLogDTO) {
        monitoringLogDTO.setLog_code(AppUtil.generateLogId());
        MonitoringLogEntity saveLog = monitoringLogDao.save(mapping.toMonitoringLogEntity
                                                                                (monitoringLogDTO));
        if(saveLog == null){
            throw new DataPersistException("Log not saved");
        }
    }

    @Override
    public List<MonitoringLogDTO> getAllLogs() {
        return mapping.toMonitoringLogDTOList(monitoringLogDao.findAll());
    }

    @Override
    public MonitoringLogStatus getLog(String logCode) {
        if(monitoringLogDao.existsById(logCode)){
            var selectedLog = monitoringLogDao.getReferenceById(logCode);
            return mapping.toMonitoringLogDTO(selectedLog);
        }else{
            return new SelectedErrorStatus(2,"Selected Log not found");
        }
    }

    @Override
    public void deleteLog(String logCode) {
        Optional<MonitoringLogEntity> foundLog = monitoringLogDao.findById(logCode);
        if(foundLog.isPresent()){
            throw new LogNotFoundException("Log not found");
        }else{
            monitoringLogDao.deleteById(logCode);
        }
    }

    @Override
    public void updateLog(String logCode, MonitoringLogDTO monitoringLogDTO) {
        Optional<MonitoringLogEntity> tmpLog = monitoringLogDao.findById(logCode);
        if(!tmpLog.isPresent()){
            throw new LogNotFoundException("Log not found");
        }else{
            tmpLog.get().setLog_date(monitoringLogDTO.getLog_date());
            tmpLog.get().setLog_details(monitoringLogDTO.getLog_details());
            tmpLog.get().setObserved_image(monitoringLogDTO.getObserved_image());
        }
    }
}
