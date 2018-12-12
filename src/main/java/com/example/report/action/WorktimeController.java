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
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/WorktimeController")
@Slf4j
public class WorktimeController {
    @Autowired
    WorktimeService worktimeService;

//    @GetMapping("/showWorktime")
//    public Result showWorktime() {
//        //@RequestBody UserWorktimeDTO userWorktimeDTO
//        UserWorktimeDTO userWorktimeDTO=new UserWorktimeDTO();
//        userWorktimeDTO.setUid(4);
//        userWorktimeDTO.setDate("2018/12/06");
//        if(userWorktimeDTO.getDate()==null){
//            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");//设置日期格式
//            userWorktimeDTO.setDate(df.format(new Date()));
//        }
//        int uid=userWorktimeDTO.getUid();
//        String date=userWorktimeDTO.getDate();
//        List<String> failList=worktimeService.showFailList(uid);
//        List<ProjectDTO> projectList=worktimeService.showProjectList(uid,date);
//        List<ProjectDTO> onlyRead=worktimeService.showOnlyRead(uid,date);
//        WorktimeDTO workTimeDTO=new WorktimeDTO();
//        workTimeDTO.setFailList(failList);
//        workTimeDTO.setProjectList(projectList);
//        workTimeDTO.setOnlyRead(onlyRead);
//        return Result.success(workTimeDTO);
//    }
    @GetMapping("/saveWorktimeReporting")
    public Result saveWorktimeReporting() {
        //@RequestBody WorktimeReportDTO worktimeReportDTO
        WorktimeReportDTO worktimeReportDTO=new WorktimeReportDTO();
        List oldList1=new ArrayList();
        List<Worktime> worktimeList1=new LinkedList<>();
        //oldList1.add(2);
        log.info("oldList1_____"+oldList1);
        Worktime worktime1=new Worktime();
        worktime1.setDetail("测试u2p3");
        worktime1.setPid(3);
        worktime1.setProjectNum(4);
        worktime1.setWdate("2018/12/05");
        worktime1.setUid(2);
        Worktime worktime2=new Worktime();
        worktime2.setDetail("测试u2p4");
        worktime2.setPid(4);
        worktime2.setProjectNum(4);
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
        List<ProjectDTO> editList=new LinkedList<>();
        List<Integer> stateList=new LinkedList<>();
        List<ProjectDTO> onlyRead=new LinkedList<>();
        WorktimeDTO workTimeDTO=new WorktimeDTO();
        List<ProjectDTO> messageList=new LinkedList<>();
        workTimeDTO.setFailList(failList);
        for(ProjectDTO message:projectList){
            stateList.add(message.getWstate());
        }
        if(stateList.contains(0)){
            editList.addAll(projectList);
        }else if(stateList.contains(1)){
            onlyRead.addAll(projectList);
        }else{
            editList.addAll(projectList);
        }
        onlyRead.addAll(worktimeService.showOnlyRead(uid,date));
        for(ProjectDTO message:editList){
            //status=1可改，status=0不可改
            message.setStatus(1);
        }
        for(ProjectDTO message:onlyRead){
            message.setStatus(0);
        }
        messageList.addAll(editList);
        messageList.addAll(onlyRead);
        workTimeDTO.setProjectList(messageList);
        return Result.success(workTimeDTO);
    }

}
