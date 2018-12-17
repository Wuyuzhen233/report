package com.example.report.action;




import com.example.report.support.ResultCode;
import com.example.report.utils.DateUtil;
import com.example.report.domain.DTO.ProjectDTO;
import com.example.report.domain.DTO.UserWorktimeDTO;
import com.example.report.domain.DTO.WorktimeDTO;
import com.example.report.domain.DTO.WorktimeReportDTO;
import com.example.report.support.Result;
import com.example.report.service.WorktimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/WorktimeController")
@Slf4j
public class WorktimeController {
    @Autowired
    WorktimeService worktimeService;


    @PostMapping("/saveWorktimeReporting")
    public Result saveWorktimeReporting(@RequestBody WorktimeReportDTO worktimeReportDTO) {
        //@RequestBody WorktimeReportDTO worktimeReportDTO
//        WorktimeReportDTO worktimeReportDTO=new WorktimeReportDTO();
//        List oldList1=new ArrayList();
//        List<Worktime> worktimeList1=new LinkedList<>();
//        //oldList1.add(2);
//        log.info("oldList1_____"+oldList1);
//        Worktime worktime1=new Worktime();
//        worktime1.setDetail("12/12测试");
//        worktime1.setPid(3);
//        worktime1.setProjectNum(2);
//        worktime1.setWdate("2018/12/10");
//        worktime1.setUid(2);
//        Worktime worktime2=new Worktime();
//        worktime2.setDetail("12/12测试");
//        worktime2.setPid(4);
//        worktime2.setProjectNum(2);
//        worktime2.setWdate("2018/12/10");
//        worktime2.setUid(2);
//        worktimeList1.add(worktime1);
//        worktimeList1.add(worktime2);
//        worktimeReportDTO.setOldList(oldList1);
//        worktimeReportDTO.setWorktimeList(worktimeList1);
        log.info("worktimeReportDTO++++++++"+worktimeReportDTO.toString());

        List<String> oldList=worktimeReportDTO.getOldList();
        List<ProjectDTO> worktimeList=worktimeReportDTO.getWorktimeList();
        try{
            if(oldList!=null){
                log.info(oldList.toString());
                worktimeService.updateState(oldList);
            }
            worktimeService.saveWorktimeReporting(worktimeList);

            return Result.success(ResultCode.SUCCESS);
        }catch(Exception e){
            return  Result.failed(ResultCode.FAIL_DATABASE,"提交失败");
        }

    }

    @PostMapping("/showWorktime")
    public Result showWorktime(@RequestBody UserWorktimeDTO userWorktimeDTO) {
        //@RequestBody UserWorktimeDTO userWorktimeDTO
//        UserWorktimeDTO userWorktimeDTO=new UserWorktimeDTO();
//        userWorktimeDTO.setUid(2);
//        userWorktimeDTO.setDate("2018/12/05");
        log.info(userWorktimeDTO.toString());

        String date=null;

        if(userWorktimeDTO.getDate()==null){
            date=DateUtil.getInstance().getDate_yyyyMMdd();
        }else{
            date=userWorktimeDTO.getDate();
        }
        int uid=userWorktimeDTO.getUid();

        List<String> failList=worktimeService.showFailList(uid);
        List<ProjectDTO> projectList=worktimeService.showProjectList(uid,date);
        log.info("projectList"+projectList);
        List<ProjectDTO> editList=new LinkedList<>();
        List<Integer> stateList=new LinkedList<>();
        List<ProjectDTO> onlyRead=new LinkedList<>();
        WorktimeDTO workTimeDTO=new WorktimeDTO();
        List<ProjectDTO> messageList=new LinkedList<>();
        workTimeDTO.setFailList(failList);
        for(ProjectDTO message:projectList){
            if(message.getWdate()==null||message.equals(null)){
                //状态5还没填写
                message.setWstate(5);
                message.setUid(uid);
                message.setWdate(date);
            }
            stateList.add(message.getWstate());
        }
        log.info("stateList"+stateList.toString());
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
            log.info("调用一次");
        }
        for(ProjectDTO message:onlyRead){
            message.setStatus(0);
        }
        messageList.addAll(editList);
        messageList.addAll(onlyRead);
        workTimeDTO.setProjectList(messageList);
        log.info(workTimeDTO.toString());
        log.info("messageList"+messageList.toString());
        return Result.success(workTimeDTO);
    }

}
