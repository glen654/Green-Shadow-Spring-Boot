package lk.ijse.green_shadow.dao;

import lk.ijse.green_shadow.entity.impl.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity,String> {
}
