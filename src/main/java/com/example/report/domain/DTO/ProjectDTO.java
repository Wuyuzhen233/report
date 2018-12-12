package com.example.report.domain.DTO;

import lombok.Data;

@Data
public class ProjectDTO {
    private int pid;
    private String pname;
    private int uid;
    private long wid;
    private String wdate;
    private String projectNum;
    private int wstate;
    private String leaderName;
    private String auditingDate;
    private String detail;
    private int status;
}
