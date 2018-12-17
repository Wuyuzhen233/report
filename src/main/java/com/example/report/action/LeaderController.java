package com.example.report.action;

import com.example.report.support.ResultCode;
import com.example.report.support.Result;
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
        log.info("传入参数："+paramMap.toString());
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
        log.info(memberInfoList.toString());
        return Result.success(memberInfoList);
    }

//    @PostMapping("saveAllMember")
//    public Result saveAllMember(@RequestBody Map<String, String> addMemberParamMap) {
//        int pid=Integer.parseInt(addMemberParamMap.get("pid"));
//        int uid=Integer.parseInt(addMemberParamMap.get("uid"));
//        List<Integer> memberList=null;
//        //List<Integer> oldList=leaderService.showAllId(pid);
//        for(int i=0;i<memberList.size();i++){
//            for(int j=0;j<oldList.size();i++){
//                if(memberList.get(i)==oldList.get(j)){
//                    //添加的
//                    memberList.remove(memberList.get(i));
//                    //删除的
//                    oldList.remove(oldList.get(j));
//                }
//            }
//        }
//        for(int addId:memberList){
////            Map<String,String> addMemberParamMap=new HashMap<>();
////            addMemberParamMap.put("uid",)
//            return leaderService.cheakMemberIsExist(addMemberParamMap);
//        }
//        return Result.success();
//    }

    /**
     * 添加成员，区别于admin的增加需要维护upp和upm，此处仅需要对upp维护
     *
     * @param
     * @return
     */
    @GetMapping("addMember")
    public Result addMember() {
        //@RequestBody Map<String, String> addMemberParamMap
        Map<String, String> addMemberParamMap=new HashMap<>();
        addMemberParamMap.put("u_id","2");
        addMemberParamMap.put("p_id","6");
        log.info("addMemberParamMap:"+addMemberParamMap);
        if (addMemberParamMap.containsKey("p_id") && addMemberParamMap.containsKey("u_id")) {
            try {

                return leaderService.cheakMemberIsExist(addMemberParamMap);
            } catch (Exception e) {
                return Result.failed(ResultCode.FAIL_DATABASE, "数据操作失败");
            }
        } else {
            return Result.failed(ResultCode.PARAMS_INCOMPLETE, "入参不完整");
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
        if (delMemberParamMap.containsKey("p_id") && delMemberParamMap.containsKey("u_id")) {
            return leaderService.delUser(delMemberParamMap);
        } else {
            return Result.failed(ResultCode.PARAMS_INCOMPLETE, "缺少必要参数");
        }

    }
}
