package com.example.report.domain.DTO;

import lombok.Data;

import java.util.List;

@Data
public class MessageDTO {
    private List<String> dateList;
    private List<ParticipantDTO> participantDTOList;
    private List<ProjectDTO> participantWorktime;
}
