package com.example.report.domain.DTO;

import com.example.report.domain.Worktime;
import lombok.Data;

import java.util.List;

@Data
public class WorktimeReportDTO {
    private List<String> oldList;
    private List<ProjectDTO> worktimeList;
}
