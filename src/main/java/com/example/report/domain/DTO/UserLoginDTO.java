package com.example.report.domain.DTO;

import lombok.Data;

@Data
public class UserLoginDTO {
    private int uid;
    private String phone;
    private String password;
    private String oldPassword;

}
