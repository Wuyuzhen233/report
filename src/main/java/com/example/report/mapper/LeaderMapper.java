package com.example.report.mapper;

import com.example.report.domain.DTO.UpParticipantDTO;
import com.example.report.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by aimu on 2018/12/11.
 */
@Mapper
public interface LeaderMapper {
    List<Map<String, String>> showAllProject(String uid);

    List<UpParticipantDTO> getAllParticipants(String pid);

    List<Map<String, String>> showAllMember();

    List<Map<String, String>> cheakMemberIsExist(Map<String, String> addMemberParamMap);

    int getUPParticipantTotal();

    void addUPParticipant(Map<String, String> uppParamMap);

    void updateUPPStatusPersonal(Map<String, String> uppParamMap);

    void delUserInUPP(Map<String, String> map);


}
