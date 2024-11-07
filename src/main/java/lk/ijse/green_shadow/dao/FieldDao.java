package lk.ijse.green_shadow.dao;

import lk.ijse.green_shadow.entity.impl.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldDao extends JpaRepository<FieldEntity,String> {
}
