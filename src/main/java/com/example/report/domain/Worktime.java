package com.example.report.domain;

import lombok.Data;

@Data
public class Worktime {
    private String wid;
    private int uid;
    private int pid;
    private String wdate;
    private int projectNum;
    private int wstate;
    private int leadeId;
    private String auditingDate;
    private String detail;
    private int Status;

}
