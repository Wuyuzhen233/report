package com.example.report.mapper;

import com.example.report.domain.DTO.ProjectDTO;
import com.example.report.domain.Worktime;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface WorktimeMapper extends Mapper<Worktime> {
    List<String> showFailList(@Param("uid") int uid);

    List<ProjectDTO> showProjectList(@Param("uid") int uid,@Param("date") String data);

    void updateState(@Param("oldwId") int oldwId);

    void saveWorktimeReporting(@Param("wid")long wid,@Param("uid") int uid,@Param("pid") int pid,@Param("wdate") String wdate,@Param("projectNum") int projectNum,@Param("wstate") int wstate);
}
