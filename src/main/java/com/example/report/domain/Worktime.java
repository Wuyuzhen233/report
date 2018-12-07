package com.example.report.domain;

import lombok.Data;

@Data
public class Worktime {
    private long wid;
    private int uid;
    private int pid;
    private String wdate;
    private int projectNum;
    private int wstate;
    private int leadeId;
    private String auditingDate;
    private String detail;

}
