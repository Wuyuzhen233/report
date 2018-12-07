package com.example.report.domain.DTO;

import lombok.Data;

import java.util.List;

@Data
public class WorktimeDTO {
    private List<String> failList;
    private List<ProjectDTO> projectList;
}
