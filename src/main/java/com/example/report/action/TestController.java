package com.example.report.action;

import com.example.report.support.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by aimu on 2018/12/14.
 */
@CrossOrigin
@RestController
@RequestMapping("test")
@Slf4j
public class TestController {

    @PostMapping("twoRole")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LEADER')")
    public String twoRole(){
        return "two role share";
    }

    @PostMapping("admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin(){
        return "admin admin admin";
    }
    @PostMapping("leader")
    @PreAuthorize("hasRole('LEADER')")
    public String leader(){
        return "leader leader";
    }
    @PostMapping("user")
    @PreAuthorize("hasRole('USER')")
    public String user(){
        return "user";
    }
    @PostMapping("show")
    public String show(){
        return "show with permission";
    }
}
