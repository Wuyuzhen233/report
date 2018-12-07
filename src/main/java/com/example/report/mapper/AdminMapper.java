package com.example.report.mapper;

import com.example.report.domain.DTO.ProjectPublishDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by aimu on 2018/12/6.
 */
public interface AdminMapper extends Mapper {
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
}
