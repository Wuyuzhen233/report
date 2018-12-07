package com.example.report.domain.DTO;

import com.example.report.domain.Worktime;
import lombok.Data;

import java.util.List;

@Data
public class WorktimeReportDTO {
    private List oldList;
    private List<Worktime> worktimeList;
}
