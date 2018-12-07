package com.example.report.service.impl;

import com.example.report.common.utils.IdGeneratorUtil;
import com.example.report.domain.DTO.ProjectDTO;
import com.example.report.domain.Worktime;
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
    public void updateState(List<Integer> oldList) {
        for(int oldwId:oldList){
            worktimeMapper.updateState(oldwId);
        }
    }

    @Override
    public void saveWorktimeReporting(List<Worktime> worktimeList) {
        for(Worktime worktime:worktimeList) {
            try {
                long wid = IdGeneratorUtil.createID();
                worktime.setWid(wid);
            } catch (Exception e) {
                log.error("创建id异常", e);
            }
            worktime.setWstate(2);
            worktimeMapper.saveWorktimeReporting(worktime.getWid(),worktime.getUid(),worktime.getPid(),worktime.getWdate(),worktime.getProjectNum(),worktime.getWstate());
            worktimeDetailMapper.saveDetail(worktime.getWid(),worktime.getDetail());
            log.info("调用一次");
        }


    }


}
