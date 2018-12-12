package com.example.report.mapper;

import com.example.report.domain.DTO.AudtingProjectDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ManagerMapper extends Mapper {
    List<AudtingProjectDTO> showAllProject(@Param("uid") int uid);

    List<String> showAllDate(@Param("pid") int pid);

    int showAuthority(int uid);

}
