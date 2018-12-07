package com.example.report.action;

import com.example.report.common.enums.ErrorCode;
import com.example.report.domain.DTO.ProjectPublishDTO;
import com.example.report.helper.Result;
import com.example.report.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by aimu on 2018/12/6.
 */
@CrossOrigin
@RestController
@RequestMapping("/adminController")
@Slf4j
public class AdminController {
    @Autowired
    AdminService adminService;

    /**
     * 发布项目
     * http://192.168.1.115:8082/report/adminController/publishProject
     * header:Content-Type body:{"projectDesc":"pdesc111","projectName":"pname111","userIdList":["4"]}
     *
     * @param projectPublishDTO
     * @return
     */
    @PostMapping("publishProject")
    public Result publishProject(@RequestBody ProjectPublishDTO projectPublishDTO) {
        log.warn(projectPublishDTO.toString());
        adminService.publishProject(projectPublishDTO);
        return Result.success();
    }

    /**
     * 展示所有项目
     * http://192.168.1.115:8082/report/adminController/showAllProject
     *
     * @return
     */
    @PostMapping("showAllProject")
    public Result showAllProject() {
        List<Map<String, String>> projectList = adminService.showAllProject();
        log.warn("############ controller adminController/showAllProject data:" + projectList);
        return Result.success(projectList);
    }

    /**
     * 更新项目的状态
     * http://192.168.1.115:8082/report/adminController/updateProjectStatus
     * {"projectId":1,"status":0}
     *
     * @param projectStatusMap
     * @return
     */
    @PostMapping("updateProjectStatus")
    public Result updateProjectStatus(@RequestBody Map<String, String> projectStatusMap) {
        if (projectStatusMap.containsKey("projectId") && projectStatusMap.containsKey("status")) {
            String projectId = projectStatusMap.get("projectId");
            String status = projectStatusMap.get("status");
            log.warn("########################" + projectId + "    " + status);
            try {
                adminService.updateProjectStatus();
            } catch (Exception e) {
                return Result.failed(ErrorCode.FAIL_DATABASE, "更新项目状态失败");
            }
            return Result.success();
        } else {
            return Result.failed(ErrorCode.PARAMS_INCOMPLETE, "入参不完整");
        }
    }
}
