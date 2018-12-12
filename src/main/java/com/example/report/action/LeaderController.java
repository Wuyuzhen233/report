package com.example.report.action;

import com.example.report.common.enums.ErrorCode;
import com.example.report.helper.Result;
import com.example.report.service.LeaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by aimu on 2018/12/11.
 */
@CrossOrigin
@RestController
@RequestMapping("leader")
@Slf4j
public class LeaderController {
    @Autowired
    private LeaderService leaderService;

    /**
     * 负责人查看项目人员管理
     * http://192.168.1.109:8082/report/leader/showAllProject
     * {"uid":4}
     *
     * @param paramMap
     * @return
     */
    @PostMapping("showAllProject")
    public Result showAllProject(@RequestBody Map<String, String> paramMap) {
        return leaderService.showAllProject(paramMap);
    }

    /**
     * 查看所有状态正常的成员
     *
     * @return
     */
    @PostMapping("showAllMember")
    public Result showAllMember() {
        List<Map<String, String>> memberInfoList = leaderService.showAllMember();
        return Result.success(memberInfoList);
    }

    /**
     * 添加成员，区别于admin的增加需要维护upp和upm，此处仅需要对upp维护
     *
     * @param addMemberParamMap
     * @return
     */
    @PostMapping("addMember")
    public Result addMember(@RequestBody Map<String, String> addMemberParamMap) {
        if (addMemberParamMap.containsKey("pid") && addMemberParamMap.containsKey("uid")) {
            try {
                return leaderService.cheakMemberIsExist(addMemberParamMap);
            } catch (Exception e) {
                return Result.failed(ErrorCode.FAIL_DATABASE, "数据操作失败");
            }
        } else {
            return Result.failed(ErrorCode.PARAMS_INCOMPLETE, "入参不完整");
        }
    }

    /**
     * 负责人剔除成员，区别于admin的剔除，此处仅需要对upp维护
     *
     * @param delMemberParamMap
     * @return
     */
    @PostMapping("delMember")
    public Result delMember(@RequestBody Map<String, String> delMemberParamMap) {
        if (delMemberParamMap.containsKey("pid") && delMemberParamMap.containsKey("uid")) {
            return leaderService.delUser(delMemberParamMap);
        } else {
            return Result.failed(ErrorCode.PARAMS_INCOMPLETE, "缺少必要参数");
        }

    }
}
