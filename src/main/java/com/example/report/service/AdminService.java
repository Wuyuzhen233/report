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

    List<Map<String, String>> showAllUser();

    List<Map<String, String>> showAllUserInfo();

    Result updateProjectStatus(Map<String, String> projectStatusMap);

    Result updateProjectNameDetail(Map<String, String> projectInfo);

    Result cheakLeaderIsExist(Map<String, String> addLeaderParamMap);

    Result delLeaderInProject(Map<String, String> delLeaderParamMap);

    Result saveUser(Map<String, String> userInfoMap);

    Result delUser(String uid);

    Result restPassword(String uid);
}
