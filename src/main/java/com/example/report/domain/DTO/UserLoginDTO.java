package com.example.report.domain.DTO;

import lombok.Data;

@Data
public class UserLoginDTO {
    private String phone;
    private String password;
    private String oldPassword;

}
