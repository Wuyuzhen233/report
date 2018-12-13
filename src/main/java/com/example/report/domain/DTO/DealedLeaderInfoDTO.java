package com.example.report.domain.DTO;

import lombok.Data;

import java.util.List;

@Data
public class DealedLeaderInfoDTO {
    private String p_id;
    private String p_name;
    private List<UpParticipantDTO> participantsInfoList;
    private List<UpManagerDTO> p_leader;
}
