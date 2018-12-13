package com.example.report.service;

import com.example.report.domain.User;
import com.example.report.helper.Result;

public interface UserService {
    User userLogin(String phone, String password);

    String findUserIdByPhonePwd(String uid, String oldPassword);

    Result resetPwd(String uid, String newpassword, String oldPassword);
}
