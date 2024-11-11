package lk.ijse.green_shadow.service.impl;

import lk.ijse.green_shadow.dto.impl.UserDTO;
import lk.ijse.green_shadow.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public void saveUser(UserDTO userDTO) {

    }

    @Override
    public UserDetailsService userDetailsService() {
        return null;
    }
}
