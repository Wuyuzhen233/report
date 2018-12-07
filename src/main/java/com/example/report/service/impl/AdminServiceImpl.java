package com.example.report.service.impl;

import com.example.report.common.enums.ErrorCode;
import com.example.report.domain.DTO.ProjectPublishDTO;
import com.example.report.helper.Result;
import com.example.report.mapper.AdminMapper;
import com.example.report.service.AdminService;
import com.example.report.support.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aimu on 2018/12/6.
 */
@Slf4j
@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Resource
    AdminMapper adminMapper;

    @Override
    public Result publishProject(ProjectPublishDTO projectPublishDTO) {
        try {
            // 在rt_project中插入记录
            int projectId = adminMapper.getProjectTotal() + 1;
            projectPublishDTO.setProjectId(projectId);
            adminMapper.publishProject(projectPublishDTO);
            log.info("发布项目成功\tprojectId{}", projectId);
            // 获取userIdList
            List<String> userIdList = projectPublishDTO.getUserIdList();
            if (userIdList.isEmpty()) {
                return Result.failed(ErrorCode.FAIL_DATABASE, "请为项目确定项目负责人");
            }
            // 在rt_up_manager中插入记录
            int upManagerId = adminMapper.getUPManagerTotal() + 1;
            for (int i = 0; i < userIdList.size(); i++) {
                Map<String, String> map = new HashMap<>();
                map.put("upManagerId", String.valueOf(upManagerId + i));
                map.put("userId", String.valueOf(userIdList.get(i)));
                map.put("projectId", String.valueOf(projectId));
                log.warn("################ map" + map);
                adminMapper.addUPManager(map);
            }
            log.info("新增项目和负责人manager关系成功\tupId{},userIdList{}", upManagerId, userIdList);
            // 在rt_participant中插入记录
            int upParticipantId = adminMapper.getUPParticipantTotal() + 1;
            for (int i = 0; i < userIdList.size(); i++) {
                Map<String, String> map = new HashMap<>();
                map.put("upParticipantId", String.valueOf(upParticipantId + i));
                map.put("userId", String.valueOf(userIdList.get(i)));
                map.put("projectId", String.valueOf(projectId));
                map.put("startTime", DateUtil.getInstance().getDate_yyyyMMdd());
                log.warn("################ map" + map);
                adminMapper.addUPParticipant(map);
            }
            log.info("新增项目和负责人participant关系成功\tupId{},userIdList{}", upManagerId, userIdList);
            return Result.success();
        } catch (Exception e) {
            log.error("发布项目异常{}", e);
            return Result.failed(ErrorCode.USER_NOT_LOGIN, "发布项目异常");
        }
    }

    @Override
    public List<Map<String, String>> showAllProject() {
        List<Map<String, String>> projectInfoList = adminMapper.showAllProject();
        return projectInfoList;
    }

    @Override
    public void updateProjectStatus() {

    }
}
