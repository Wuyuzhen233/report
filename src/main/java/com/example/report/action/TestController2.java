package com.example.report.action;

import com.example.report.domain.DTO.ParticipantDTO;
import com.example.report.support.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by aimu on 2018/12/14.
 */
@CrossOrigin
@RestController
@RequestMapping("test2")
@Slf4j
public class TestController2 {
    @PostMapping("show")
    public String show() {
        return "public without permission";
    }
}
