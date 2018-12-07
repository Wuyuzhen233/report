package com.example.report.domain;

import lombok.Data;

/**
 * Created by aimu on 2018/12/6.
 */
@Data
public class Project {
    private int projectId;
    private String projectName;
    private int projectStatus;// 项目的状态，默认开启，开启1，关闭0
    private String projectDesc;
}
