package com.example.report.mapper;

import com.example.report.domain.DTO.ParticipantDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ParticipantMapper extends Mapper {
    List<ParticipantDTO> showAllParticipant(@Param("pid") int pid);

    List<ParticipantDTO> showParticipant(@Param("pid") int pid);

}
