package com.example.report.action;



import com.example.report.support.ResultCode;
import com.example.report.utils.DateUtil;
import com.example.report.domain.DTO.*;
import com.example.report.domain.User;
import com.example.report.support.Result;
import com.example.report.service.AuditingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/AuditingController")
@Slf4j
public class AuditingController {
    @Autowired
    AuditingService auditingService;


    @PostMapping("/showAllProject")
    public Result showAllProject(@RequestBody  User user) {
        //@RequestParam int uid
        //int uid=2;
        log.info(user.toString());
        List<AudtingProjectDTO> projectList=auditingService.showAllProject(user.getUserId());
        log.info(projectList.toString());
        return  Result.success(projectList);
    }

    @PostMapping("/showAllMessages")
    public Result showAllMessages(@RequestBody AudtingMangerInfoDTO audtingMangerInfoDTO){
        //@RequestBody AudtingMangerInfoDTO audtingMangerInfoDTO
//        AudtingMangerInfoDTO audtingMangerInfoDTO=new AudtingMangerInfoDTO();
//        audtingMangerInfoDTO.setP_id(1);
//        audtingMangerInfoDTO.setU_id(2);
        String date=DateUtil.getInstance().getDate_yyyyMMdd();
        MessageDTO messageDTO=new MessageDTO();
        List<String> dateList=auditingService.showAllDate(audtingMangerInfoDTO.getUid(),
                audtingMangerInfoDTO.getPid(),date);
        List<ParticipantDTO> participantDTOList=auditingService.showAllParticipant(audtingMangerInfoDTO.getPid());
        List<ProjectDTO> participantWorktime=auditingService.showAllWorktime(audtingMangerInfoDTO.getPid());
        messageDTO.setDateList(dateList);
        messageDTO.setParticipantDTOList(participantDTOList);
        messageDTO.setParticipantWorktime(participantWorktime);
        log.info(messageDTO.toString());
        return Result.success(messageDTO);
    }
    @PostMapping("/showAllAuditing")
    public Result showAllAuditing(@RequestBody AudtingMangerInfoDTO audtingMangerInfoDTO){
        //@RequestBody AudtingMangerInfoDTO audtingMangerInfoDTO
//        AudtingMangerInfoDTO audtingMangerInfoDTO=new AudtingMangerInfoDTO();
//        audtingMangerInfoDTO.setP_id(5);
//        audtingMangerInfoDTO.setU_id(4);
        log.info("WWWWWWWWWW"+audtingMangerInfoDTO.toString());
        MessageDTO messageDTO=new MessageDTO();
        List<ProjectDTO> participantWorktime=new LinkedList<>();
        List<String> dateList=auditingService.showDateList(audtingMangerInfoDTO.getUid(),
                audtingMangerInfoDTO.getPid());
        List<ParticipantDTO> participantList=auditingService.showParticipant(audtingMangerInfoDTO.getPid());
        log.info("dateList:"+dateList.toString());
        log.info("size"+dateList.size());
        if(dateList.size()==0){
            participantList=null;
        }else{
            participantWorktime=auditingService.showWorktime(audtingMangerInfoDTO.getPid(),dateList.get(0),dateList.get(dateList.size()-1));
        }
        messageDTO.setDateList(dateList);
        messageDTO.setParticipantDTOList(participantList);
        messageDTO.setParticipantWorktime(participantWorktime);
        log.info("_____________"+messageDTO);
        return Result.success(messageDTO);
    }

    @PostMapping("/showAuthority")
    public Result showAuthority(@RequestBody User user){
        log.info("******:"+user.getUserId());
        //@RequestParam int uid
        //int uid=5;
        int uid=user.getUserId();
        int userNum=auditingService.showAuthority(uid);
        if(userNum!=0){
            return Result.success(ResultCode.SUCCESS);
        }else{
            return Result.failed(ResultCode.NOT_HAVE_AUTHORITY,"没有权限");
        }

    }

//    @GetMapping("/notPass")
//    public Result notPass(@RequestBody NotPassDTO notPassDTO){
//        //@RequestBody NotPassDTO notPassDTO
////        NotPassDTO notPassDTO=new NotPassDTO();
////        notPassDTO.setUid(5);
////        long j=1072022647564402688L;
////        notPassDTO.setWid(j);
//        String auditingTime= DateUtil.getInstance().getDate_yyyyMMdd();
//        try {
//            auditingService.notPass(notPassDTO.getUid(),notPassDTO.getWid(),auditingTime);
//        }catch (Exception e){
//            return Result.failed(ResultCode.FAIL_DATABASE,"驳回失败");
//        }
//
//        return Result.success(ResultCode.SUCCESS);
//    }

//    @GetMapping("/passAllAuditingMessages")
//    public Result passAllAuditingMessages(){
//        //@RequestBody PassDTO passDTO
//        PassDTO passDTO=new PassDTO();
//        passDTO.setUid(5);
//        List<Long> passList=new ArrayList();
//        passList.add(1072022444123881472L);
//        passList.add(1072022442764926976L);
//        passDTO.setPassList(passList);
//        String auditingTime= DateUtil.getInstance().getDate_yyyyMMdd();
//        int uid=passDTO.getUid();
//        for(Long wid:passDTO.getPassList()){
//            auditingService.saveAuditingMessages(uid,wid,auditingTime);
//        }
//        return Result.success(ResultCode.SUCCESS);
//
//    }

    @PostMapping("/checkAllAuditingMessages")
    public Result checkAllAuditingMessages(@RequestBody AuditingMessageDTO auditingMessageDTO){
        //@RequestBody PassDTO passDTO
//        AuditingMessageDTO auditingMessageDTO=new AuditingMessageDTO();
//        auditingMessageDTO.setUid(5);
//        List<Long> passList=new ArrayList();
//        passList.add(1072022444123881472L);
//        passList.add(1072022442764926976L);
//        auditingMessageDTO.setPassList(passList);
//        List<Long> notPassList=new ArrayList();
//        notPassList.add(1072022645949595648L);
//        notPassList.add(1072022647564402688L);
//        auditingMessageDTO.setNotPassList(notPassList);

        String auditingTime= DateUtil.getInstance().getDate_yyyyMMdd();
        int uid=auditingMessageDTO.getUid();
        for(String wid:auditingMessageDTO.getPassList()){
            auditingService.saveAuditingMessages(uid,Long.parseLong(wid),auditingTime);
        }
        for(String wid:auditingMessageDTO.getNotPassList()){
            auditingService.notPass(uid,Long.parseLong(wid),auditingTime);
        }
        return Result.success(ResultCode.SUCCESS);

    }



}
