package lk.ijse.green_shadow.dao;

import lk.ijse.green_shadow.entity.impl.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FieldDao extends JpaRepository<FieldEntity,String> {
}
