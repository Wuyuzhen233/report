package com.example.report.action;




import com.example.report.domain.DTO.ProjectDTO;
import com.example.report.domain.DTO.UserWorktimeDTO;
import com.example.report.domain.DTO.WorktimeDTO;
import com.example.report.domain.DTO.WorktimeReportDTO;
import com.example.report.domain.Worktime;
import com.example.report.helper.Result;
import com.example.report.service.WorktimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/WorktimeController")
@Slf4j
public class WorktimeController {
    @Autowired
    WorktimeService worktimeService;

    @GetMapping("/showWorktime")
    public Result showWorktime() {
        //@RequestBody UserWorktimeDTO userWorktimeDTO
        UserWorktimeDTO userWorktimeDTO=new UserWorktimeDTO();
        userWorktimeDTO.setUid(2);
        userWorktimeDTO.setDate("2018/12/05");
        if(userWorktimeDTO.getDate()==null){
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");//设置日期格式
            userWorktimeDTO.setDate(df.format(new Date()));
        }
        int uid=userWorktimeDTO.getUid();
        String date=userWorktimeDTO.getDate();
        List<String> failList=worktimeService.showFailList(uid);
        List<ProjectDTO> projectList=worktimeService.showProjectList(uid,date);
        WorktimeDTO workTimeDTO=new WorktimeDTO();
        workTimeDTO.setFailList(failList);
        workTimeDTO.setProjectList(projectList);
        return Result.success(workTimeDTO);
    }
    @GetMapping("/saveWorktimeReporting")
    public Result saveWorktimeReporting() {
        //@RequestBody WorktimeReportDTO worktimeReportDTO
        WorktimeReportDTO worktimeReportDTO=new WorktimeReportDTO();
        List oldList1=new ArrayList();
        List<Worktime> worktimeList1=new LinkedList<>();
        oldList1.add(2);
        log.info("oldList1_____"+oldList1);
        Worktime worktime1=new Worktime();
        worktime1.setDetail("测试接口2");
        worktime1.setPid(2);
        worktime1.setProjectNum(3);
        worktime1.setWdate("2018/12/05");
        worktime1.setUid(2);
        Worktime worktime2=new Worktime();
        worktime2.setDetail("测试接口2");
        worktime2.setPid(2);
        worktime2.setProjectNum(3);
        worktime2.setWdate("2018/12/05");
        worktime2.setUid(2);
        worktimeList1.add(worktime1);
        worktimeList1.add(worktime2);
        worktimeReportDTO.setOldList(oldList1);
        worktimeReportDTO.setWorktimeList(worktimeList1);
        log.info("worktimeReportDTO++++++++"+worktimeReportDTO.toString());


        List<Integer> oldList=worktimeReportDTO.getOldList();
        List<Worktime> worktimeList=worktimeReportDTO.getWorktimeList();
        if(oldList!=null){
            worktimeService.updateState(oldList);
        }
        worktimeService.saveWorktimeReporting(worktimeList);


        return Result.success(1000);
    }

}
