package com.example.report.action;

import com.example.report.support.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/loginController")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);


    @PostMapping("/login")
    public JsonResponse login(@RequestBody Map<String,String> loginmap) {
        String uphone=loginmap.get("uphone");
        String upassword=loginmap.get("upassword");
        return null;
    }

}
