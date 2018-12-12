package com.example.report.mapper;

import com.example.report.domain.DTO.ProjectDTO;
import com.example.report.domain.Worktime;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface WorktimeMapper extends Mapper<Worktime> {
    List<String> showFailList(@Param("uid") int uid);

    List<ProjectDTO> showProjectList(@Param("uid") int uid,@Param("date") String data);

    void updateState(@Param("oldwId") long oldwId);

    void saveWorktimeReporting(@Param("wid")long wid,@Param("uid") int uid,@Param("pid") int pid,@Param("wdate") String wdate,@Param("projectNum") int projectNum,@Param("wstate") int wstate);

    List<ProjectDTO> showOnlyRead(@Param("uid") int uid,@Param("date") String date);

    List<ProjectDTO> showAllWorktime(@Param("pid") int pid);

    List<String> showDateList(@Param("pid") int pid);

    List<ProjectDTO> showWorktime(@Param("pid") int pid,@Param("starttime") String starttime,@Param("endtime") String endtime);

    void notPass(@Param("uid") int uid,@Param("wid") long wid,@Param("auditingTime") String auditingTime);

    void saveAuditingMessages(@Param("uid") int uid,@Param("wid") long wid,@Param("auditingTime") String auditingTime);

}

