package lk.ijse.green_shadow.dao;

import lk.ijse.green_shadow.entity.impl.CropEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropDao extends JpaRepository<CropEntity,String> {
    @Query("SELECT c FROM CropEntity c WHERE c.common_name IN :crops")
    List<CropEntity> findByCropNameList(@Param("crops") List<String> crops);
}
