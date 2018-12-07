package com.example.report.action;



import com.example.report.helper.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/AuditingController")
@Slf4j
public class AuditingController {

    @GetMapping("/showAllProject")
    public Result showAllProject(@RequestParam int uid) {
        //List<Project> projectList=new LinkedList<>();


        return  null;
    }
}
