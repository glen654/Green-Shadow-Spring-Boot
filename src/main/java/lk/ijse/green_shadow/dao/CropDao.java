package lk.ijse.green_shadow.dao;

import lk.ijse.green_shadow.entity.impl.CropEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropDao extends JpaRepository<CropEntity,String> {
}
