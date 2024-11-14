package lk.ijse.green_shadow.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
@RequestMapping("api/v1/health")
public class HealthCheckController {
    @GetMapping
    public String healthTest(){
        return "Green Shadow API is working";
    }
}
