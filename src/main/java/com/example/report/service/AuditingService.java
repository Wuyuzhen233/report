package com.example.report.service;

import com.example.report.domain.DTO.AudtingProjectDTO;
import com.example.report.domain.DTO.ParticipantDTO;
import com.example.report.domain.DTO.ProjectDTO;
import com.example.report.domain.Worktime;


import java.util.List;

public interface AuditingService {
    List<AudtingProjectDTO> showAllProject(int uid);

    List<String> showAllDate(int uid,int pid,String date);

    List<ParticipantDTO> showAllParticipant(int pid);

    List<ProjectDTO> showAllWorktime(int pid);

    List<String> showDateList(int uid,int pid);

    List<ParticipantDTO> showParticipant(int pid);

    List<ProjectDTO> showWorktime(int pid,String startdate,String enddate);

    int showAuthority(int uid);

    void notPass(int uid,Long wid,String auditingTime);

    void saveAuditingMessages(int uid,long wid,String auditingTime);



 }
