package lk.ijse.green_shadow.controller;

import lk.ijse.green_shadow.dto.impl.UserDTO;
import lk.ijse.green_shadow.exception.DataPersistException;
import lk.ijse.green_shadow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUser(@RequestBody UserDTO userDTO) {
        try {
            userService.saveUser(userDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
