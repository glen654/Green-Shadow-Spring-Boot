package lk.ijse.green_shadow.dao;

import lk.ijse.green_shadow.entity.impl.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentDao extends JpaRepository<EquipmentEntity,String> {
}
