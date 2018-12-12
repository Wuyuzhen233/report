package com.example.report.action;

import com.example.report.common.enums.ErrorCode;
import com.example.report.domain.DTO.ProjectPublishDTO;
import com.example.report.helper.Result;
import com.example.report.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aimu on 2018/12/6.
 */
@CrossOrigin
@RestController
@RequestMapping("admin")
@Slf4j
public class AdminController {
    @Autowired
    AdminService adminService;

    /**
     * 发布项目
     * http://192.168.1.115:8082/report/admin/publishProject
     * header:Content-Type body:{"projectDesc":"pdesc111","projectName":"pname111","userIdList":["4","5"]}
     *
     * @param projectPublishDTO
     * @return
     */
    @PostMapping("publishProject")
    public Result publishProject(@RequestBody ProjectPublishDTO projectPublishDTO) {
        log.warn("############ controller /admin/publishProjectDTO PublishDTO{}", projectPublishDTO.toString());
        adminService.publishProject(projectPublishDTO);
        return Result.success();
    }

    /**
     * 展示所有项目
     * http://192.168.1.115:8082/report/admin/showAllProject
     *
     * @return
     */
    @PostMapping("showAllProject")
    public Result showAllProject() {
        List<Map<String, String>> projectList = adminService.showAllProject();
        List<Map<String, String>> userList = adminService.showAllUser();
        Map<String, List<Map<String, String>>> resMap = new HashMap<>();
        resMap.put("projectList", projectList);
        resMap.put("userList", userList);
        log.info("############ controller admin/showAllProject resMap{}", resMap);
        return Result.success(resMap);
    }

    /**
     * 发布项目时，展示所有成员id和name
     * http://192.168.1.115:8082/report/admin/showAllUser
     *
     * @return
     */
    @PostMapping("showAllUser")
    public Result showAllUser() {
        List<Map<String, String>> userList = adminService.showAllUser();
        log.info("############ controller admin/showAllUser userList{}", userList);
        return Result.success(userList);
    }

    /**
     * 成员管理时，展示所有成员信息
     *
     * @return
     */
    @PostMapping("showAllUserInfo")
    public Result showAllUserInfo() {
        List<Map<String, String>> userInfoList = adminService.showAllUserInfo();
        log.info("############ controller admin/showAllUserInfo userInfoList{}", userInfoList);
        return Result.success(userInfoList);
    }

    /**
     * 更新项目的状态
     * http://192.168.1.115:8082/report/admin/updateProjectStatus
     * {"pid":1,"status":2,"uppid":3,"upmid":4}
     *
     * @param projectStatusMap
     * @return
     */
    @PostMapping("updateProjectStatus")
    public Result updateProjectStatus(@RequestBody Map<String, String> projectStatusMap) {
        if (projectStatusMap.containsKey("pid")
                && projectStatusMap.containsKey("status")
                && projectStatusMap.containsKey("uppid")
                && projectStatusMap.containsKey("upmid")
                ) {
            log.warn("############ controller /admin/updateProjectStatus projectStatusMap:{}", projectStatusMap);
            try {
                return adminService.updateProjectStatus(projectStatusMap);
            } catch (Exception e) {
                return Result.failed(ErrorCode.FAIL_DATABASE, "更新项目状态失败");
            }
        } else {
            return Result.failed(ErrorCode.PARAMS_INCOMPLETE, "入参不完整");
        }
    }

    /**
     * 保存编辑后的项目信息
     * http://192.168.1.108:8082/report/admin/saveProject
     * {"pid":2,"projectDesc":"金牛状态0，已关闭","projectName":"金牛座"}
     *
     * @param projectInfo
     * @return
     */
    @PostMapping("saveProject")
    public Result saveProject(@RequestBody Map<String, String> projectInfo) {
        if (projectInfo.containsKey("pid")) {
            if (projectInfo.containsKey("projectDesc")
                    || projectInfo.containsKey("projectName")) {
                adminService.updateProjectNameDetail(projectInfo);
            }
            return Result.success();
        } else {
            return Result.failed(ErrorCode.PARAMS_INCOMPLETE, "缺少必要字段pid");
        }
    }

    /**
     * 新增负责人leader
     * http://192.168.1.109:8082/report/admin/addLeader
     * {"pid":2,"uid":"2"}
     *
     * @param addLeaderParamMap
     * @return
     */
    @PostMapping("addLeader")
    public Result addLeader(@RequestBody Map<String, String> addLeaderParamMap) {
        if (addLeaderParamMap.containsKey("pid") && addLeaderParamMap.containsKey("uid")) {
            try {
                return adminService.cheakLeaderIsExist(addLeaderParamMap);
            } catch (Exception e) {
                return Result.failed(ErrorCode.FAIL_DATABASE, "数据操作失败");
            }
        } else {
            return Result.failed(ErrorCode.PARAMS_INCOMPLETE, "入参不完整");
        }
    }

    /**
     * 新增负责人leader
     * http://192.168.1.109:8082/report/admin/delLeader
     * {"pid":2,"uid":"2"}
     *
     * @param delLeaderParamMap
     * @return
     */
    @PostMapping("delLeader")
    public Result delLeader(@RequestBody Map<String, String> delLeaderParamMap) {
        if (delLeaderParamMap.containsKey("pid") && delLeaderParamMap.containsKey("uid")) {
            try {
                return adminService.delLeaderInProject(delLeaderParamMap);
            } catch (Exception e) {
                return Result.failed(ErrorCode.FAIL_DATABASE, "数据操作失败");
            }
        } else {
            return Result.failed(ErrorCode.PARAMS_INCOMPLETE, "入参不完整");
        }
    }

    /**
     * 超管新增用户
     *
     * @param userInfoMap
     * @return
     */
    @PostMapping("saveUser")
    public Result saveUser(@RequestBody Map<String, String> userInfoMap) {
        if (userInfoMap.containsKey("userBase")
                && userInfoMap.containsKey("userName")
                && userInfoMap.containsKey("userPhone")) {
            return adminService.saveUser(userInfoMap);
        } else {
            return Result.failed(ErrorCode.PARAMS_INCOMPLETE, "缺少必要参数");
        }
    }

    /**
     * 超管删除用户
     *
     * @param uid
     * @return
     */
    @PostMapping("delUser")
    public Result delUser(@RequestBody String uid) {
        return adminService.delUser(uid);
    }

    /**
     * 超管重置成员密码，默认123456
     *
     * @param uid
     * @return
     */
    @PostMapping("restPassword")
    public Result restPassword(@RequestBody String uid) {
        return adminService.restPassword(uid);
    }
}
