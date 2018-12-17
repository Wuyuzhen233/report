package com.example.report.service.impl;

import com.example.report.support.ResultCode;
import com.example.report.utils.DateUtil;
import com.example.report.domain.DTO.DealedLeaderInfoDTO;
import com.example.report.domain.DTO.UpManagerDTO;
import com.example.report.domain.DTO.UpParticipantDTO;
import com.example.report.support.Result;
import com.example.report.mapper.LeaderMapper;
import com.example.report.service.LeaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

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
            log.info("leaderInfoMapList"+leaderInfoMapList.toString());
            Map<String, String> tag = new HashMap<>();
            for (Map<String, String> leaderInfoMap : leaderInfoMapList) {
                if (tag.containsKey(String.valueOf(leaderInfoMap.get("p_id")) + ";" + leaderInfoMap.get("p_name"))) {
                    tag.put(String.valueOf(leaderInfoMap.get("p_id")) + ";" + leaderInfoMap.get("p_name"), tag.get(String.valueOf(leaderInfoMap.get("p_id")) + ";" + leaderInfoMap.get("p_name")) + ":" + String.valueOf(leaderInfoMap.get("upm_id")) + ";" + String.valueOf(leaderInfoMap.get("u_id")) + ";" + leaderInfoMap.get("u_name"));
                } else {
                    tag.put(String.valueOf(leaderInfoMap.get("p_id")) + ";" + leaderInfoMap.get("p_name"), String.valueOf(leaderInfoMap.get("upm_id")) + ";" + String.valueOf(leaderInfoMap.get("u_id")) + ";" + leaderInfoMap.get("u_name"));
                }
            }
            log.info("###############tag++++++"+ tag.toString());
            // 此处对tag的内容进行stringbuffer的拼接
            List<DealedLeaderInfoDTO> dealedLeaderInfoList = new ArrayList<>();
            for (String key : tag.keySet()) {
                //Map<String, Object> tmpMap = new HashMap<>();
                DealedLeaderInfoDTO tmpMap = new DealedLeaderInfoDTO();
                String pid = key.split(";")[0];
                List<UpParticipantDTO> participantsInfoList = leaderMapper.getAllParticipants(pid);
//                tmpMap.put("participantsInfoList", participantsInfoList.toString());
//                tmpMap.put("p_id", pid);
//                tmpMap.put("p_name", key.split(";")[1]);
                tmpMap.setP_id(pid);
                tmpMap.setParticipantsInfoList(participantsInfoList);
                tmpMap.setP_name(key.split(";")[1]);
                String value = tag.get(key);
                String[] tmps = value.split(":");
                List<UpManagerDTO> p_leader=new LinkedList<>();
                //StringBuffer sb = new StringBuffer();
                //sb.append("[");
                for (String tmpValue : tmps) {
                    UpManagerDTO upManager=new UpManagerDTO();
                    upManager.setU_name(tmpValue.split(";")[2]);
                    upManager.setU_id(tmpValue.split(";")[1]);
                    upManager.setUpm_id(tmpValue.split(";")[0] );
                    p_leader.add(upManager);
//                    sb.append("{");
//                    sb.append("upm_id:" + tmpValue.split(";")[0] + ",");
//                    sb.append("u_id:" + tmpValue.split(";")[1] + ",");
//                    sb.append("u_name:" + tmpValue.split(";")[2]);
//                    sb.append("}");
                }
                //sb.append("]");
                //tmpMap.put("p_leader", p_leader.toString());
                //dealedLeaderInfoList.add(tmpMap);
                tmpMap.setP_leader(p_leader);
                dealedLeaderInfoList.add(tmpMap);
            }
            log.info("######################### dealedLeaderInfoList:{}", dealedLeaderInfoList);

            return Result.success(dealedLeaderInfoList);
        } catch (Exception e) {
            return Result.failed(ResultCode.FAIL_DATABASE, "查询数据库失败");
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
        //int fistNum=leaderMapper.getUPParticipantTotal();

        if (num == 0) {// 若resMapList长度为零，表示不存在该用户，则需要新增
            int upParticipantId = leaderMapper.getUPParticipantTotal() + 1;
            Map<String, String> uppParamMap = new HashMap<>();
            uppParamMap.put("uppid", String.valueOf(upParticipantId));
            uppParamMap.put("uid", addMemberParamMap.get("u_id"));
            uppParamMap.put("pid", addMemberParamMap.get("p_id"));
            uppParamMap.put("startTime", DateUtil.getInstance().getDate_yyyyMMdd());
            leaderMapper.addUPParticipant(uppParamMap);
            log.info("================ LeaderServiceImpl cheakMemberIsExist 为该用户新增upp关系成功");

            List<UpParticipantDTO> participantsInfoList = leaderMapper.getAllParticipants(addMemberParamMap.get("p_id"));
            log.info("participantsInfoList___________________"+participantsInfoList);
            return Result.success(participantsInfoList);
        } else if (num == 1) {// 若resMapList长度为一，表示该member参与过该项目，则需要改upp的状态
            Map<String, String> uppParamMap = new HashMap<>();
            uppParamMap.put("uid", addMemberParamMap.get("u_id"));
            uppParamMap.put("pid", addMemberParamMap.get("p_id"));
            uppParamMap.put("status", "1");
            leaderMapper.updateUPPStatusPersonal(uppParamMap);
            log.info("================ AdminServiceImpl cheakLeaderIsExist 为改用户更改upp和upm关系成功");
            //int finalNum=leaderMapper.getUPParticipantTotal();
            int leaderstatus=leaderMapper.getParticipantStatus(Integer.parseInt(addMemberParamMap.get("u_id")),Integer.parseInt(addMemberParamMap.get("p_id")));
            if(leaderstatus==1){
                List<UpParticipantDTO> participantsInfoList = leaderMapper.getAllParticipants(addMemberParamMap.get("p_id"));
                log.info("participantsInfoList___________________"+participantsInfoList);
                return Result.failed(ResultCode.FAIL_DATABASE," 用户已存在",participantsInfoList);
            }else{
                List<UpParticipantDTO> participantsInfoList = leaderMapper.getAllParticipants(addMemberParamMap.get("p_id"));
                return Result.success(participantsInfoList);
            }

        } else {
            return Result.failed(ResultCode.FAIL_DATABASE, "数据库中upm数据异常，请核查");
        }
    }

    @Transactional
    @Override
    public Result delUser(Map<String, String> delMemberParamMap) {
        try {
            log.info("#####################"+delMemberParamMap);
            Map<String, String> map = new HashMap<>();
            map.put("uid", delMemberParamMap.get("u_id"));
            map.put("pid", delMemberParamMap.get("p_id"));
            map.put("endTime", DateUtil.getInstance().getDate_yyyyMMdd());
            leaderMapper.delUserInUPP(map);
            List<UpParticipantDTO> participantsInfoList = leaderMapper.getAllParticipants(delMemberParamMap.get("p_id"));
            log.info("participantsInfoList___________________"+participantsInfoList);
            return Result.success(participantsInfoList);
        } catch (Exception e) {
            List<UpParticipantDTO> participantsInfoList = leaderMapper.getAllParticipants(delMemberParamMap.get("p_id"));
            return Result.failed(ResultCode.FAIL_DATABASE, "删除用户失败",participantsInfoList);
        }
    }



}
