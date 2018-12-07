package com.example.report.service;

import com.example.report.domain.DTO.ProjectPublishDTO;
import com.example.report.helper.Result;

import java.util.List;
import java.util.Map;

/**
 * Created by aimu on 2018/12/6.
 */
public interface AdminService {
    Result publishProject(ProjectPublishDTO projectPublishDTO);

    List<Map<String, String>> showAllProject();

    void updateProjectStatus();
}
