package com.example.report.action;

import com.example.report.common.enums.ErrorCode;
import com.example.report.domain.DTO.*;
import com.example.report.domain.User;
import com.example.report.helper.Result;
import com.example.report.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
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
     *完成
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
     *完成
     * @return
     */
    @PostMapping("showAllProject")
    public Result showAllProject() {
       List<RootProjectDTO> rootProjectDTOList=adminService.showProjectInfo();
       for(RootProjectDTO rootProjectDTO:rootProjectDTOList){
            int p_id=rootProjectDTO.getP_id();
            List<LeaderInfoDTO> projectLeader=adminService.showLeaderInfo(p_id);
            rootProjectDTO.setProjectLeader(projectLeader);
       }
       log.info(rootProjectDTOList.toString());
//        Map<String, List> lists=new HashMap<>();
       // return null;
        //List<Map<String, String>> projectList = adminService.showAllProject();
        //List<Map<String, String>> userList = adminService.showAllUser();
        List<ParticipantDTO> userList =  adminService.showAllUser();
//        log.info(userList.toString());
//        Map<String, List<Map<String, String>>> resMap = new HashMap<>();
//        resMap.put("projectList", projectList);
        //resMap.put("userList", userList);
        ShowAllProjectDTO lists=new ShowAllProjectDTO();
        lists.setUserList(userList);
        lists.setRootProjectDTOList(rootProjectDTOList);
        log.info("############ controller admin/showAllProject resMap{}", lists);
        return Result.success(lists);
    }

    /**
     * 发布项目时，展示所有成员id和name
     * http://192.168.1.115:8082/report/admin/showAllUser
     *完成
     * @return
     */
    @PostMapping("showAllUser")
    public Result showAllUser() {
        List<ParticipantDTO> userList = adminService.showAllUser();
        log.info("############ controller admin/showAllUser userList{}", userList);
        return Result.success(userList);
    }

    /**
     * 成员管理时，展示所有成员信息
     *完成
     * @return
     */
    @PostMapping("showAllUserInfo")
    public Result showAllUserInfo() {
        List<Map<String, String>> userInfoList = adminService.showAllUserInfo();
        log.info("############ controller admin/showAllUserInfo userInfoList{}+++++++++++++"+ userInfoList);
        return Result.success(userInfoList);
    }

    /**
     * 更新项目的状态
     * http://192.168.1.115:8082/report/admin/updateProjectStatus
     * {"pid":1,"status":2,"uppid":3,"upmid":4}
     *完成
     * @param projectStatusMap
     * @return
     */
    @PostMapping("updateProjectStatus")
    public Result updateProjectStatus(@RequestBody ProjectStstusDTO projectStatusMap) {
        log.info(projectStatusMap.toString());

            log.warn("############ controller /admin/updateProjectStatus projectStatusMap:{}", projectStatusMap);
            try {

                return adminService.updateProjectStatus(projectStatusMap);
            } catch (Exception e) {
                return Result.failed(ErrorCode.FAIL_DATABASE, "更新项目状态失败");
            }

    }

    /**
     * 保存编辑后的项目信息
     * http://192.168.1.108:8082/report/admin/saveProject
     * {"pid":2,"projectDesc":"金牛状态0，已关闭","projectName":"金牛座"}
     *完成
     * @param projectInfo
     * @return
     */
    @PostMapping("saveProject")
    public Result saveProject(@RequestBody Map<String, String> projectInfo) {
        if (projectInfo.containsKey("p_id")) {

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
     *完成
     * @param addLeaderParamMap
     * @return
     */
    @PostMapping("addLeader")
    public Result addLeader(@RequestBody Map<String, String> addLeaderParamMap) {
        if (addLeaderParamMap.containsKey("p_id") && addLeaderParamMap.containsKey("u_id")) {
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
     * 删除负责人leader
     * http://192.168.1.109:8082/report/admin/delLeader
     * {"pid":2,"uid":"2"}
     *完成
     * @param delLeaderParamMap
     * @return
     */
    @PostMapping("delLeader")
    public Result delLeader(@RequestBody Map<String, String> delLeaderParamMap) {
        log.info(delLeaderParamMap.toString());
        if (delLeaderParamMap.containsKey("p_id") && delLeaderParamMap.containsKey("u_id")) {
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
        log.info(userInfoMap.toString());
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
     *完成
     * @param
     * @return
     */
    @PostMapping("delUser")
    public Result delUser(@RequestBody User user) {
        log.info("uid++++++++++++++++"+user.getUserId());
        return adminService.delUser(String.valueOf(user.getUserId()));
    }

    /**
     * 超管修改用户信息
     *
     */
    @PostMapping("restUserInfo")
    public Result restUserInfo(@RequestBody User user){
        adminService.updateUserInfo(user);
        return Result.success();
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
