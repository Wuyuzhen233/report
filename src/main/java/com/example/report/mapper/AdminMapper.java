package com.example.report.mapper;

import com.example.report.domain.DTO.LeaderInfoDTO;
import com.example.report.domain.DTO.ParticipantDTO;
import com.example.report.domain.DTO.ProjectPublishDTO;
import com.example.report.domain.DTO.RootProjectDTO;
import com.example.report.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by aimu on 2018/12/6.
 */
@Mapper
public interface AdminMapper {
    // 发布项目
    void publishProject(ProjectPublishDTO projectPublishDTO);

    // 关系表中增加发布项目的记录
    void addUPManager(Map<String, String> map);

    // 关系表中增加发布项目的记录
    void addUPParticipant(Map<String, String> map);

    // 获取项目总数
    int getProjectTotal();

    // 获取关系表中的关系总数
    int getUPManagerTotal();

    // 获取关系表中的关系总数
    int getUPParticipantTotal();

    // 显示所有的项目
    List<Map<String, String>> showAllProject();

    // 发布项目时，展示user表中全部的userId和userName
    List<ParticipantDTO> showAllUser();

    // 成员管理时，展示所有成员信息
    List<Map<String, String>> showAllUserInfo();

    List<RootProjectDTO> showProjectInfo();

    List<LeaderInfoDTO> showLeaderInfo(@Param("p_id") int p_id);

    // 更新项目状态
    void updateProjectStatus(Map<String, String> projectStatusMap);

    // 成员项目参与关系表中状态
    void updateUPPStatus(Map<String, String> projectStatusMap);

    // 成员项目管理关系表中状态
    void updateUPMStatus(Map<String, String> projectStatusMap);

    // 更新项目名称详情
    void updateProjectNameDetail(Map<String, String> projectInfo);

    // 更新项目leader
    void updateProjectLeaderList(String leaderIdList);

    // 查看该leader是否在该项目中任职过。如果为空，则新增；如果不为空，则改upp和upm的状态
    List<Map<String, String>> checkLeaderIsExist(Map<String, String> addLeaderParamMap);

    // 为一个人打开项目管理权限
    void updateUPMStatusPersonal(Map<String, String> upmParamMap);

    // 为一个人打开项目参与状态
    void updateUPPStatusPersonal(Map<String, String> upmParamMap);

    // 在upp中为一个人置零status
    void zeroLeaderInUPP(Map<String, String> delLeaderParamMap);

    // 在upm中为一个人置零status
    void zeroLeaderInUPM(Map<String, String> delLeaderParamMap);

    // 超管增加成员
    void saveUser(Map<String, String> userInfoMap);

    // 获取user表记录总数
    int getUserTotalNum();

    // 超管删除用户，实际操作是把该uid在user、upp、ypm中的status设为不可用
    void delUser(String uid);

    void delUserInUPM(String uid);

    void delUserInUPP(Map<String, String> uid);

    // 超管重置成员密码，默认密码123456
    void restPassword(String uid);

    int showStatus(@Param("uid") String uid,@Param("pid") String pid);
}
