package com.example.report.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by wangyuan on 2018/12/5.
 */
@Data
public class User {

    private int userId;

    private String userName;

    private String userBase;

    private String userPhone;

    private String userPassword;

    private int userRole;// 用户角色，超管10，员工11

    private int userStatus;// 用户状态，正常20，离职21

}
