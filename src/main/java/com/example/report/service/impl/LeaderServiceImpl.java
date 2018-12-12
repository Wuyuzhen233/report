package com.example.report.service.impl;

import com.example.report.common.enums.ErrorCode;
import com.example.report.common.utils.DateUtil;
import com.example.report.helper.Result;
import com.example.report.mapper.LeaderMapper;
import com.example.report.service.LeaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aimu on 2018/12/11.
 */
@Slf4j
@Service
@Transactional
public class LeaderServiceImpl implements LeaderService {
    @Resource
    private LeaderMapper leaderMapper;

    @Override
    public Result showAllProject(Map<String, String> paramMap) {
        try {
            // 对获取到的leaderInfoMapList进行去重
            List<Map<String, String>> leaderInfoMapList = leaderMapper.showAllProject(paramMap.get("uid"));
            Map<String, String> tag = new HashMap<>();
            for (Map<String, String> leaderInfoMap : leaderInfoMapList) {
                if (tag.containsKey(String.valueOf(leaderInfoMap.get("p_id")) + ";" + leaderInfoMap.get("p_name"))) {
                    tag.put(String.valueOf(leaderInfoMap.get("p_id")) + ";" + leaderInfoMap.get("p_name"), tag.get(String.valueOf(leaderInfoMap.get("p_id")) + ";" + leaderInfoMap.get("p_name")) + ":" + String.valueOf(leaderInfoMap.get("upm_id")) + ";" + String.valueOf(leaderInfoMap.get("u_id")) + ";" + leaderInfoMap.get("u_name"));
                } else {
                    tag.put(String.valueOf(leaderInfoMap.get("p_id")) + ";" + leaderInfoMap.get("p_name"), String.valueOf(leaderInfoMap.get("upm_id")) + ";" + String.valueOf(leaderInfoMap.get("u_id")) + ";" + leaderInfoMap.get("u_name"));
                }
            }
            log.info("###############{}", tag.toString());
            // 此处对tag的内容进行stringbuffer的拼接
            List<Map<String, String>> dealedLeaderInfoList = new ArrayList<>();
            for (String key : tag.keySet()) {
                Map<String, String> tmpMap = new HashMap<>();
                String pid = key.split(";")[0];
                List<Map<String, String>> participantsInfoList = leaderMapper.getAllParticipants(pid);
                tmpMap.put("participantsInfoList", participantsInfoList.toString());
                tmpMap.put("p_id", pid);
                tmpMap.put("p_name", key.split(";")[1]);
                String value = tag.get(key);
                String[] tmps = value.split(":");
                StringBuffer sb = new StringBuffer();
                sb.append("[");
                for (String tmpValue : tmps) {
                    sb.append("{");
                    sb.append("upm_id=" + tmpValue.split(";")[0] + ",");
                    sb.append("u_id=" + tmpValue.split(";")[1] + ",");
                    sb.append("u_name=" + tmpValue.split(";")[2]);
                    sb.append("}");
                }
                sb.append("]");
                tmpMap.put("p_leader", sb.toString());
                dealedLeaderInfoList.add(tmpMap);
            }
            log.info("######################### dealedLeaderInfoList:{}", dealedLeaderInfoList);

            return Result.success(dealedLeaderInfoList);
        } catch (Exception e) {
            return Result.failed(ErrorCode.FAIL_DATABASE, "查询数据库失败");
        }
    }

    @Override
    public List<Map<String, String>> showAllMember() {
        List<Map<String, String>> allMember = leaderMapper.showAllMember();
        return allMember;
    }

    @Override
    public Result cheakMemberIsExist(Map<String, String> addMemberParamMap) {
        List<Map<String, String>> resMapList = leaderMapper.cheakMemberIsExist(addMemberParamMap);
        int num = resMapList.size();
        if (num == 0) {// 若resMapList长度为零，表示不存在该用户，则需要新增
            int upParticipantId = leaderMapper.getUPParticipantTotal() + 1;
            Map<String, String> uppParamMap = new HashMap<>();
            uppParamMap.put("uppid", String.valueOf(upParticipantId));
            uppParamMap.put("uid", addMemberParamMap.get("uid"));
            uppParamMap.put("pid", addMemberParamMap.get("pid"));
            uppParamMap.put("startTime", DateUtil.getInstance().getDate_yyyyMMdd());
            leaderMapper.addUPParticipant(uppParamMap);
            log.info("================ LeaderServiceImpl cheakMemberIsExist 为该用户新增upp关系成功");
            return Result.success();
        } else if (num == 1) {// 若resMapList长度为一，表示该member参与过该项目，则需要改upp的状态
            Map<String, String> uppParamMap = new HashMap<>();
            uppParamMap.put("uid", addMemberParamMap.get("uid"));
            uppParamMap.put("pid", addMemberParamMap.get("pid"));
            uppParamMap.put("status", "1");
            leaderMapper.updateUPPStatusPersonal(uppParamMap);
            log.info("================ AdminServiceImpl cheakLeaderIsExist 为改用户更改upp和upm关系成功");
            return Result.success();
        } else {
            return Result.failed(ErrorCode.FAIL_DATABASE, "数据库中upm数据异常，请核查");
        }
    }

    @Transactional
    @Override
    public Result delUser(Map<String, String> delMemberParamMap) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("uid", delMemberParamMap.get("uid"));
            map.put("pid", delMemberParamMap.get("pid"));
            map.put("endTime", DateUtil.getInstance().getDate_yyyyMMdd());
            leaderMapper.delUserInUPP(map);
            return Result.success();
        } catch (Exception e) {
            return Result.failed(ErrorCode.FAIL_DATABASE, "删除用户失败");
        }
    }

}
