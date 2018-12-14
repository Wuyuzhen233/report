package com.example.report.service.impl;

import com.example.report.utils.DateDistance;
import com.example.report.domain.DTO.AudtingProjectDTO;
import com.example.report.domain.DTO.ParticipantDTO;
import com.example.report.domain.DTO.ProjectDTO;
import com.example.report.mapper.ManagerMapper;
import com.example.report.mapper.ParticipantMapper;
import com.example.report.mapper.WorktimeMapper;
import com.example.report.service.AuditingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
@Slf4j
@Service
@Transactional
public class AuditingServiceImp implements AuditingService {
    @Resource
    ManagerMapper managerMapper;

    @Resource
    ParticipantMapper participantMapper;

    @Resource
    WorktimeMapper worktimeMapper;

    @Override
    public List<AudtingProjectDTO> showAllProject(int uid) {
        List<AudtingProjectDTO> projectList=managerMapper.showAllProject(uid);

        return projectList;
    }

    @Override
    public List<String> showAllDate(int uid, int pid, String date) {
        List<String> list1=managerMapper.showAllDate(pid);
        String datestart= Collections.min(list1);
        List<String> dateList=new LinkedList<>();
        String dateend=date;
        log.info(Integer.parseInt(datestart.replace("/",""))+"<="+Integer.parseInt(dateend.replace("/","")));
        while (Integer.parseInt(datestart.replace("/",""))<=Integer.parseInt(dateend.replace("/",""))){
            log.info("Start:"+datestart);
            dateList.add(datestart);
            datestart= DateDistance.add1(datestart);
        }
        log.info("dateList"+dateList);
        return dateList;
    }

    @Override
    public List<ParticipantDTO> showAllParticipant(int pid) {
        List<ParticipantDTO> participantDTOList=participantMapper.showAllParticipant(pid);
        return participantDTOList;
    }

    @Override
    public List<ProjectDTO> showAllWorktime(int pid) {
        List<ProjectDTO> participantWorktime=worktimeMapper.showAllWorktime(pid);
        return participantWorktime;
    }

    @Override
    public List<String> showDateList(int uid, int pid) {
        List<String> dateList=worktimeMapper.showDateList(pid);
        return dateList;
    }

    @Override
    public List<ParticipantDTO> showParticipant(int pid) {
        List<ParticipantDTO> participantDTOList=participantMapper.showParticipant(pid);
        return participantDTOList;
    }

    @Override
    public List<ProjectDTO> showWorktime(int pid,String startdate,String enddate) {
        List<ProjectDTO> participantWorktime=worktimeMapper.showWorktime(pid,startdate,enddate);
        return participantWorktime;
    }

    @Override
    public int showAuthority(int uid) {
        return managerMapper.showAuthority(uid);
    }

    @Override
    public void notPass(int uid, Long wid, String auditingTime) {
        worktimeMapper.notPass(uid,wid,auditingTime);
    }

    @Override
    public void saveAuditingMessages(int uid, long wid, String auditingTime) {
        worktimeMapper.saveAuditingMessages(uid,wid,auditingTime);
    }
}
