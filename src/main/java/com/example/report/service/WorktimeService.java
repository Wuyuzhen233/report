package com.example.report.service;

import com.example.report.domain.DTO.ProjectDTO;
import com.example.report.domain.Worktime;

import java.util.List;

public interface WorktimeService {
    /**
     * 展示所有含有未通过审批工时的日期
     * @param uid
     * @return
     */
    List<String> showFailList(int uid);

    /**
     * 展示当天的所有项目及其信息
     * @param uid
     * @return
     */

    List<ProjectDTO> showProjectList(int uid,String date);

    void updateState(List<Integer> oldList);

    void saveWorktimeReporting(List<Worktime> worktimeList);

}
