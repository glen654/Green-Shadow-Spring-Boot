package lk.ijse.green_shadow.controller;

import lk.ijse.green_shadow.dto.impl.UserDTO;
import lk.ijse.green_shadow.exception.DataPersistException;
import lk.ijse.green_shadow.secure.JWTAuthResponse;
import lk.ijse.green_shadow.secure.SignIn;
import lk.ijse.green_shadow.service.AuthService;
import lk.ijse.green_shadow.service.UserService;
import lk.ijse.green_shadow.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth/")
@RequiredArgsConstructor
@CrossOrigin
public class AuthUserController {
    private final UserService userService;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    private static Logger logger = LoggerFactory.getLogger(AuthUserController.class);

    @PostMapping(value = "signup",consumes = MediaType.APPLICATION_JSON_VALUE,
                                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JWTAuthResponse> saveUser(@RequestBody UserDTO userDTO) {
        try {
            userDTO.setUser_id(AppUtil.generateUserId());
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            logger.info("User saved successfully");
            return new ResponseEntity<>(authService.signUp(userDTO), HttpStatus.CREATED);
        }catch (DataPersistException e) {
            e.printStackTrace();
            logger.warn("Returning Http 400 Bad Request",e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("User save unsuccessful",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping(value = "signin",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JWTAuthResponse> signIn(@RequestBody SignIn signIn) {
        logger.info("User signed in successfully");
        return ResponseEntity.ok(authService.signIn(signIn));
    }
    @PostMapping("refresh")
    public ResponseEntity<JWTAuthResponse> refreshToken(@RequestParam ("existingToken") String existingToken) {
        logger.info("Refresh token successfully");
        return ResponseEntity.ok(authService.refreshToken(existingToken));
    }
}
