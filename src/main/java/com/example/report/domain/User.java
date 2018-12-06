package com.example.report.domain;

import lombok.Data;



/**
 * Created by wangyuan on 2018/12/5.
 */
@Data
public class User {

    private int id;

    private String base;

    private String name;

    private String phone;

    private String password;

    private int role;// 用户角色，超管10，员工11

    private int status;// 用户状态，正常20，离职21


}
