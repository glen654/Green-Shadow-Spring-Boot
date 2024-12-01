package lk.ijse.green_shadow.dao;

import lk.ijse.green_shadow.entity.impl.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipmentDao extends JpaRepository<EquipmentEntity,String> {
    @Query("SELECT e FROM EquipmentEntity e WHERE e.name = :equipmentName")
    Optional<EquipmentEntity> findByEquipmentName(@Param("equipmentName") String equipmentName);
}
