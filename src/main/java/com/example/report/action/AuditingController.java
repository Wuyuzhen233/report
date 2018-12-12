package com.example.report.action;



import com.example.report.common.enums.ErrorCode;
import com.example.report.common.utils.DateUtil;
import com.example.report.domain.DTO.*;
import com.example.report.helper.Result;
import com.example.report.service.AuditingService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public Result showAllProject(@RequestParam int uid) {
        //@RequestParam int uid
        //int uid=2;
        List<AudtingProjectDTO> projectList=auditingService.showAllProject(uid);
        return  Result.success(projectList);
    }

    @GetMapping("/showAllMessages")
    public Result showAllMessages(){
        //@RequestBody AudtingMangerInfoDTO audtingMangerInfoDTO
        AudtingMangerInfoDTO audtingMangerInfoDTO=new AudtingMangerInfoDTO();
        audtingMangerInfoDTO.setP_id(1);
        audtingMangerInfoDTO.setU_id(2);
        String date=DateUtil.getInstance().getDate_yyyyMMdd();
        MessageDTO messageDTO=new MessageDTO();
        List<String> dateList=auditingService.showAllDate(audtingMangerInfoDTO.getU_id(),
                audtingMangerInfoDTO.getP_id(),date);
        List<ParticipantDTO> participantDTOList=auditingService.showAllParticipant(audtingMangerInfoDTO.getP_id());
        List<ProjectDTO> participantWorktime=auditingService.showAllWorktime(audtingMangerInfoDTO.getP_id());
        messageDTO.setDateList(dateList);
        messageDTO.setParticipantDTOList(participantDTOList);
        messageDTO.setParticipantWorktime(participantWorktime);
        return Result.success(messageDTO);
    }
    @GetMapping("/showAllAuditing")
    public Result showAllAuditing(){
        //@RequestBody AudtingMangerInfoDTO audtingMangerInfoDTO
        AudtingMangerInfoDTO audtingMangerInfoDTO=new AudtingMangerInfoDTO();
        audtingMangerInfoDTO.setP_id(5);
        audtingMangerInfoDTO.setU_id(4);
        MessageDTO messageDTO=new MessageDTO();
        List<ProjectDTO> participantWorktime=new LinkedList<>();
        List<String> dateList=auditingService.showDateList(audtingMangerInfoDTO.getU_id(),
                audtingMangerInfoDTO.getP_id());
        List<ParticipantDTO> participantList=auditingService.showParticipant(audtingMangerInfoDTO.getP_id());
        log.info("dateList:"+dateList.toString());
        log.info("size"+dateList.size());
        if(dateList.size()==0){
            participantList=null;
        }else{
            participantWorktime=auditingService.showWorktime(audtingMangerInfoDTO.getP_id(),dateList.get(0),dateList.get(dateList.size()-1));
        }
        messageDTO.setDateList(dateList);
        messageDTO.setParticipantDTOList(participantList);
        messageDTO.setParticipantWorktime(participantWorktime);
        return Result.success(messageDTO);
    }

    @GetMapping("/showAuthority")
    public Result showAuthority(){
        //@RequestParam int uid
        int uid=5;
        int userNum=auditingService.showAuthority(uid);
        if(userNum!=0){
            return Result.success(ErrorCode.SUCCESS);
        }else{
            return Result.failed(ErrorCode.NOT_HAVE_AUTHORITY,"没有权限");
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
//            return Result.failed(ErrorCode.FAIL_DATABASE,"驳回失败");
//        }
//
//        return Result.success(ErrorCode.SUCCESS);
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
//        return Result.success(ErrorCode.SUCCESS);
//
//    }

    @GetMapping("/checkAllAuditingMessages")
    public Result checkAllAuditingMessages(){
        //@RequestBody PassDTO passDTO
        AuditingMessageDTO auditingMessageDTO=new AuditingMessageDTO();
        auditingMessageDTO.setUid(5);
        List<Long> passList=new ArrayList();
        passList.add(1072022444123881472L);
        passList.add(1072022442764926976L);
        auditingMessageDTO.setPassList(passList);
        List<Long> notPassList=new ArrayList();
        notPassList.add(1072022645949595648L);
        notPassList.add(1072022647564402688L);
        auditingMessageDTO.setNotPassList(notPassList);

        String auditingTime= DateUtil.getInstance().getDate_yyyyMMdd();
        int uid=auditingMessageDTO.getUid();
        for(Long wid:auditingMessageDTO.getPassList()){
            auditingService.saveAuditingMessages(uid,wid,auditingTime);
        }
        for(Long wid:auditingMessageDTO.getNotPassList()){
            auditingService.notPass(uid,wid,auditingTime);
        }
        return Result.success(ErrorCode.SUCCESS);

    }



}
