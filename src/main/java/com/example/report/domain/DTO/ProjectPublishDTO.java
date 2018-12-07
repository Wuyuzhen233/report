package com.example.report.domain.DTO;

import lombok.Data;

import java.util.List;

/**
 * Created by aimu on 2018/12/6.
 */
@Data
public class ProjectPublishDTO {
    private int projectId;
    private String projectName;
    private int projectStatus;// 项目的状态，默认开启，开启1，关闭0
    private String projectDesc;
    List<String> userIdList;
}
