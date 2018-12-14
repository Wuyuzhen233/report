package com.example.report.service.impl;

import com.example.report.utils.IdGenUtils;
import com.example.report.domain.DTO.ProjectDTO;
import com.example.report.mapper.WorktimeDetailMapper;
import com.example.report.mapper.WorktimeMapper;
import com.example.report.service.WorktimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class WorktimeServiceImp implements WorktimeService {
    @Resource
    WorktimeMapper worktimeMapper;

    @Resource
    WorktimeDetailMapper worktimeDetailMapper;

    @Override
    public List<String> showFailList(int uid) {
        List<String> failDateList=worktimeMapper.showFailList(uid);
        return failDateList;
    }

    @Override
    public List<ProjectDTO> showProjectList(int uid,String Data) {
        List<ProjectDTO> projectList=worktimeMapper.showProjectList(uid, Data);
        return projectList;
    }

    @Override
    public void updateState(List<String> oldList) {
        for(int i=0;i<oldList.size();i++){
            if(oldList.get(i)!=null){
                log.info(oldList.get(i));
                worktimeMapper.updateState(Long.parseLong(oldList.get(i)));
            }
        }
    }

    @Override
    public void saveWorktimeReporting(List<ProjectDTO> worktimeList) {
        for(ProjectDTO worktime:worktimeList) {
            try {
//                long wid = IdGeneratorUtil.createID();
                IdGenUtils idGenUtils = new IdGenUtils(0,0);
                long wid = idGenUtils.nextId();
                worktime.setWid(String.valueOf(wid));
            } catch (Exception e) {
                log.error("创建id异常", e);
            }
            worktime.setWstate(2);
            worktimeMapper.saveWorktimeReporting(Long.parseLong(worktime.getWid()),worktime.getUid(),worktime.getPid(),worktime.getWdate(),Integer.parseInt(worktime.getProjectNum()),worktime.getWstate());
            log.info(worktime.getWid()+","+worktime.getUid()+","+worktime.getPid()+","+worktime.getWdate()+","+Integer.parseInt(worktime.getProjectNum())+","+worktime.getWstate());
            log.info("detail:"+worktime.getWid()+","+worktime.getDetail());
            worktimeDetailMapper.saveDetail(Long.parseLong(worktime.getWid()),worktime.getDetail());
        }


    }

    @Override
    public List<ProjectDTO> showOnlyRead(int uid, String date) {
        return worktimeMapper.showOnlyRead(uid, date);
    }


}
