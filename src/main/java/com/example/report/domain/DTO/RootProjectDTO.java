package com.example.report.domain.DTO;


import lombok.Data;

import java.util.List;

@Data
public class RootProjectDTO {
    private int p_id;
    private String projectName;
    private String projectDesc;
    private int p_status;
    private List<LeaderInfoDTO> projectLeader;



}
