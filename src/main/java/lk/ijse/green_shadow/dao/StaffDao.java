package lk.ijse.green_shadow.dao;

import lk.ijse.green_shadow.entity.impl.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffDao extends JpaRepository<StaffEntity,String> {
    @Query("SELECT s FROM StaffEntity s WHERE s.first_name IN :staffs")
    List<StaffEntity> findByStaffNameList(@Param("staffs") List<String> staffs);
}
