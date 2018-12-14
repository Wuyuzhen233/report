package com.example.report.domain.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ProjectStstusDTO {
    private int pid;
    private int status;
    private List<Integer> uppIdList;
    private List<Integer> upmIdList;
}
