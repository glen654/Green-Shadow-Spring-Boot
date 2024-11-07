package lk.ijse.green_shadow.dao;

import lk.ijse.green_shadow.entity.impl.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleDao extends JpaRepository<VehicleEntity,String> {
}
