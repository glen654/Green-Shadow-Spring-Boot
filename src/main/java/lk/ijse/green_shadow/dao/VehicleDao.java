package lk.ijse.green_shadow.dao;

import lk.ijse.green_shadow.entity.impl.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleDao extends JpaRepository<VehicleEntity,String> {
    @Query("SELECT v FROM VehicleEntity v WHERE v.licensePlateNumber = :licenseNumber")
    Optional<VehicleEntity> findByLicenseNumber(@Param("licenseNumber") String licenseNumber);
}
