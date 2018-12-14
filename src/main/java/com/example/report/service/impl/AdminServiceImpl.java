package com.example.report.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.report.support.ResultCode;
import com.example.report.domain.DTO.*;
import com.example.report.domain.User;
import com.example.report.support.Result;
import com.example.report.mapper.AdminMapper;
import com.example.report.service.AdminService;
import com.example.report.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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
            log.info("================ 发布项目成功\tprojectId{}", projectId);
            // 获取userIdList
            List<Integer> userIdList = projectPublishDTO.getUserIdList();
            if (userIdList.isEmpty()) {
                return Result.failed(ResultCode.FAIL_DATABASE, "请为项目确定项目负责人");
            }
            // 在rt_up_manager中插入记录
            int upManagerId = adminMapper.getUPManagerTotal() + 1;
            for (int i = 0; i < userIdList.size(); i++) {
                Map<String, String> map = new HashMap<>();
                map.put("upManagerId", String.valueOf(upManagerId + i));
                map.put("userId", String.valueOf(userIdList.get(i)));
                map.put("projectId", String.valueOf(projectId));
                adminMapper.addUPManager(map);
            }
            log.info("================ 新增项目和负责人manager关系成功\tupId{},userIdList{}", upManagerId, userIdList);
            // 在rt_participant中插入记录
            int upParticipantId = adminMapper.getUPParticipantTotal() + 1;
            for (int i = 0; i < userIdList.size(); i++) {
                Map<String, String> map = new HashMap<>();
                map.put("upParticipantId", String.valueOf(upParticipantId + i));
                map.put("userId", String.valueOf(userIdList.get(i)));
                map.put("projectId", String.valueOf(projectId));
                map.put("startTime", DateUtil.getInstance().getDate_yyyyMMdd());
                adminMapper.addUPParticipant(map);
            }
            log.info("================ 新增项目和负责人participant关系成功\tupId{},userIdList{}", upManagerId, userIdList);
            return Result.success();
        } catch (Exception e) {
            log.error("发布项目异常{}", e);
            return Result.failed(ResultCode.USER_NOT_LOGIN, "发布项目异常");
        }
    }

    @Override
    public List<Map<String,String>> showAllProject() {
        List<Map<String, String>> projectInfoList = adminMapper.showAllProject();
        log.info("================ AdminServiceImpl showAllProject (from db),projectInfoList:{}", projectInfoList);
        /**
         * 下面两次循环的目的是对上面得到的projectInfoList进行同一项目多个负责人的情况进行合并，
         * 使用了两次循环，成本是O(2n)，暂时没有想出优化方案，暂定这样。181210wy
         */
        // 通过本次循环，把项目和负责人匹配出来(通过项目id可以得到全部负责人name)


        Map<String, String> tag1 = new HashMap<>();//K:V = projectId:leader1,leader2,leader3...
        for (Map<String, String> projectInfo : projectInfoList) {
            if (tag1.containsKey(projectInfo.get("p_id"))) {
                tag1.put(projectInfo.get("p_id"), tag1.get(projectInfo.get("p_id")) + "," + "{leaderName:" + projectInfo.get("projectLeader") + ",upm_id:" + String.valueOf(projectInfo.get("upm_id")) + "}");
            } else {//[{aimu=3}, {wangyuan=2}]  [{name:aimu,id:3}, {name:wangyuan,id:2}]
                tag1.put(projectInfo.get("p_id"), "{leaderName:" + projectInfo.get("projectLeader") + ",upm_id:" + String.valueOf(projectInfo.get("upm_id")) + "}");
            }
        }
        // 本次循环对除了leaderName不一样的projectInfo去重
        List<Map<String, String>> dealMapList = new ArrayList<>();
        Map<String, String> tag2 = new HashMap<>();

        for (Map<String, String> projectInfo : projectInfoList) {
            // 如果项目id重复，跳过；否则把项目的负责人替换成tag1中的负责人
            if (tag2.containsKey(projectInfo.get("p_id"))) {
                continue;
            } else {
                tag2.put(projectInfo.get("p_id"), "");
                projectInfo.remove("upm_id");

                projectInfo.put("projectLeader", "[" + tag1.get(projectInfo.get("p_id")) + "]");
                dealMapList.add(projectInfo);
            }
        }
        log.info("================ AdminServiceImpl showAllProject (dealed),projectInfoList:{}", dealMapList);
        return dealMapList;
    }

    @Override
    public Result updateProjectStatus(ProjectStstusDTO projectStatusMap) {
        log.info("TTTTTTTTTTTTTTTT"+projectStatusMap.toString());
        if (projectStatusMap.getStatus()==0 || projectStatusMap.getStatus()==1) {
            // 项目状态(p_status)变更为关闭(0)
            int pid=projectStatusMap.getPid();
            int status=projectStatusMap.getStatus();
            List<Integer> uppIdList=projectStatusMap.getUppIdList();
            List<Integer> upmIdList=projectStatusMap.getUpmIdList();
            adminMapper.updateProjectStatus(pid,status);
            // 成员项目参与关系表中状态(upp_status)变更为关闭(0)
            for(int i=0;i<uppIdList.size();i++ ){
                adminMapper.updateUPPStatus(uppIdList.get(i),status);
            }
            //adminMapper.updateUPPStatus(projectStatusMap.get("uppid"));
            // 成员项目管理关系表中状态(upp_status)变更为关闭(0)
            for(int i=0;i<upmIdList.size();i++ ){
                adminMapper.updateUPMStatus(upmIdList.get(i),status);
            }
            //adminMapper.updateUPMStatus(projectStatusMap.get("upmid"));
        } else {
            return Result.failed(ResultCode.FAIL_DATABASE, "状态值status错误");
        }
        return Result.success();
    }

    @Override
    public List<ParticipantDTO> showAllUser() {
        return adminMapper.showAllUser();
    }

    @Override
    public List<Map<String, String>> showAllUserInfo() {
        return adminMapper.showAllUserInfo();
    }

    @Override
    public List<RootProjectDTO> showProjectInfo() {
        return adminMapper.showProjectInfo();
    }

    @Override
    public List<LeaderInfoDTO> showLeaderInfo(int p_id) {

        return adminMapper.showLeaderInfo(p_id);
    }

    @Transactional
    @Override
    public Result updateProjectNameDetail(Map<String, String> projectInfo) {
        try {
            adminMapper.updateProjectNameDetail(projectInfo);
            log.error("================ AdminServiceImpl updateProjectNameDetail projectInfo{}", projectInfo);
        } catch (Exception e) {
            return Result.failed(ResultCode.FAIL_DATABASE, "数据库更新项目名称详情失败");
        }
        return Result.success();
    }

    @Override
    public Result cheakLeaderIsExist(Map<String, String> addLeaderParamMap) {
        List<Map<String, String>> resMapList = adminMapper.checkLeaderIsExist(addLeaderParamMap);
        int num = resMapList.size();
        int fistNum=adminMapper.getUPParticipantTotal();
        if (num == 0) {// 若resMapList长度为零，表示不存在该用户，则需要新增
            int upManagerId = adminMapper.getUPManagerTotal() + 1;
            Map<String, String> upmParamMap = new HashMap<>();
            upmParamMap.put("upManagerId", String.valueOf(upManagerId));
            upmParamMap.put("userId", addLeaderParamMap.get("u_id"));
            upmParamMap.put("projectId",String.valueOf(addLeaderParamMap.get("p_id")) );
            adminMapper.addUPManager(upmParamMap);
            int upParticipantId = adminMapper.getUPParticipantTotal() + 1;
            Map<String, String> uppParamMap = new HashMap<>();
            uppParamMap.put("upParticipantId", String.valueOf(upParticipantId));
            uppParamMap.put("userId", addLeaderParamMap.get("u_id"));
            uppParamMap.put("projectId", String.valueOf(addLeaderParamMap.get("p_id")));
            uppParamMap.put("startTime", DateUtil.getInstance().getDate_yyyyMMdd());
            adminMapper.addUPParticipant(uppParamMap);
            log.info("================ AdminServiceImpl cheakLeaderIsExist 为该用户新增upp和upm关系成功");
            List<LeaderInfoDTO> projectLeader=adminMapper.showLeaderInfo(Integer.parseInt(addLeaderParamMap.get("p_id")));
            log.info("participantsInfoList___________________"+projectLeader);
            return Result.success(projectLeader);
        } else if (num == 1) {// 若resMapList长度为一，表示该leader在该项目中任职过，则需要改upp和upm的状态
            int upm_status=adminMapper.showStatus(addLeaderParamMap.get("u_id"),addLeaderParamMap.get("p_id"));
            Map<String, String> upmParamMap = new HashMap<>();
            upmParamMap.put("uid", addLeaderParamMap.get("u_id"));
            upmParamMap.put("pid", String.valueOf(addLeaderParamMap.get("p_id")));
            upmParamMap.put("upm_status", "1");
            adminMapper.updateUPMStatusPersonal(upmParamMap);
            adminMapper.updateUPPStatusPersonal(upmParamMap);

            log.info("================ AdminServiceImpl cheakLeaderIsExist 为改用户更改upp和upm关系成功");

            if(upm_status==1){
                List<LeaderInfoDTO> LeaderInfoDTO=adminMapper.showLeaderInfo(Integer.parseInt(addLeaderParamMap.get("p_id")));
                log.info("participantsInfoList___________________"+LeaderInfoDTO);
                return Result.failed(ResultCode.FAIL_DATABASE,"管理员已存在",LeaderInfoDTO);
            }else{
                List<LeaderInfoDTO> LeaderInfoDTO=adminMapper.showLeaderInfo(Integer.parseInt(addLeaderParamMap.get("p_id")));
                log.info("participantsInfoList___________________"+LeaderInfoDTO);
                return Result.success(LeaderInfoDTO);
            }
        } else {
            return Result.failed(ResultCode.FAIL_DATABASE, "数据库中upm数据异常，请核查");
        }
    }

    @Transactional
    @Override
    public Result delLeaderInProject(Map<String, String> delLeaderParamMap) {
        try {
            adminMapper.zeroLeaderInUPM(delLeaderParamMap);
            // 为入参增加key结束时间，用于upp的upp_endtime字段
            delLeaderParamMap.put("endTime", DateUtil.getInstance().getDate_yyyyMMdd());
            adminMapper.zeroLeaderInUPP(delLeaderParamMap);
            List<LeaderInfoDTO> LeaderInfoDTO=adminMapper.showLeaderInfo(Integer.parseInt(delLeaderParamMap.get("p_id")));
            log.info("participantsInfoList___________________"+LeaderInfoDTO);
            return Result.success(LeaderInfoDTO);
        } catch (Exception e) {
            return Result.failed(ResultCode.FAIL_DATABASE, "数据库操作失败");
        }
    }

    @Transactional
    @Override
    public Result saveUser(Map<String, String> userInfoMap) {
        try {
            int num = adminMapper.getUserTotalNum() + 1;
            userInfoMap.put("u_id", String.valueOf(num));
            adminMapper.saveUser(userInfoMap);
            return Result.success();
        } catch (Exception e) {
            return Result.failed(ResultCode.FAIL_DATABASE, "保存用户信息，入库失败");
        }
    }

    @Transactional
    @Override
    public Result delUser(String uid) {
        try {
            adminMapper.delUser(uid);
            adminMapper.delUserInUPM(uid);
            Map<String, String> map = new HashMap<>();
            map.put("uid", uid);
            map.put("endTime", DateUtil.getInstance().getDate_yyyyMMdd());
            adminMapper.delUserInUPP(map);
            return Result.success();
        } catch (Exception e) {
            return Result.failed(ResultCode.FAIL_DATABASE, "删除用户失败");
        }

    }

    @Override
    public Result restPassword(String uid) {
        try {
            adminMapper.restPassword(uid);
            return Result.success();
        } catch (Exception e) {
            return Result.failed(ResultCode.FAIL_DATABASE, "数据库重置密码失败");
        }
    }

    @Override
    public void updateUserInfo(User user) {
        Map<String,String> map=new HashMap();
        map.put("userId",String.valueOf(user.getUserId()));
        map.put("userName",user.getUserName());
        map.put("userBase",user.getUserBase());
        map.put("userPassword",user.getUserPassword());
        map.put("userPhone",user.getUserPhone());

        adminMapper.updateUserInfo(map);
    }

}
