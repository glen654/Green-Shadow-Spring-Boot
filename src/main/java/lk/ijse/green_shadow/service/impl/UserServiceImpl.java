package lk.ijse.green_shadow.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.green_shadow.dao.UserDao;
import lk.ijse.green_shadow.dto.impl.UserDTO;
import lk.ijse.green_shadow.entity.impl.UserEntity;
import lk.ijse.green_shadow.exception.DataPersistException;
import lk.ijse.green_shadow.service.UserService;
import lk.ijse.green_shadow.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveUser(UserDTO userDTO) {
        UserEntity saveUser = userDao.save(mapping.toUserEntity(userDTO));
        if(saveUser == null) {
            throw new DataPersistException("User not saved");
        }
    }

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userDao.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
