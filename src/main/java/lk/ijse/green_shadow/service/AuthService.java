package lk.ijse.green_shadow.service;

import lk.ijse.green_shadow.dto.impl.UserDTO;
import lk.ijse.green_shadow.secure.JWTAuthResponse;
import lk.ijse.green_shadow.secure.SignIn;

public interface AuthService {
    JWTAuthResponse signIn(SignIn signIn);
    JWTAuthResponse signUp(UserDTO userDTO);
    JWTAuthResponse refreshToken(String accessToken);
}
