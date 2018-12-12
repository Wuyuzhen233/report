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
     * @param date
     * @return
     */

    List<ProjectDTO> showProjectList(int uid,String date);

    void updateState(List<String> oldList);

    void saveWorktimeReporting(List<ProjectDTO> worktimeList);

    /**
     * 展示当天只读列表内容（即现已经没有权限但提交过的内容）
     * @param uid
     * @param date
     * @return
     */
    List<ProjectDTO> showOnlyRead(int uid,String date);

}
