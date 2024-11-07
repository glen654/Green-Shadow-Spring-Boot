package lk.ijse.green_shadow.dao;

import lk.ijse.green_shadow.entity.impl.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffDao extends JpaRepository<StaffEntity,String> {
}
