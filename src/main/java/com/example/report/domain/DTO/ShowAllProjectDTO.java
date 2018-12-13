package com.example.report.domain.DTO;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ShowAllProjectDTO {
    private List<RootProjectDTO> rootProjectDTOList;
    private List<ParticipantDTO> userList;
}
