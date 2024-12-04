package lk.ijse.green_shadow.dao;

import lk.ijse.green_shadow.entity.impl.MonitoringLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonitoringLogDao extends JpaRepository<MonitoringLogEntity,String> {
    @Query("SELECT l FROM MonitoringLogEntity l WHERE l.log_details = :logDesc")
    Optional<MonitoringLogEntity> findByLogDesc(@Param("logDesc") String logDesc);
}
