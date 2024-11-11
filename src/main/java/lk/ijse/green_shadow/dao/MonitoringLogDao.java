package lk.ijse.green_shadow.dao;

import lk.ijse.green_shadow.entity.impl.MonitoringLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoringLogDao extends JpaRepository<MonitoringLogEntity,String> {
}
