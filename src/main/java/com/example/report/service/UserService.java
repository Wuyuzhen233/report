package com.example.report.service;

import com.example.report.domain.User;
import com.example.report.domain.securityEntity.UserAuth;
import com.example.report.support.Result;

import java.util.Map;

public interface UserService {
    User userLogin(String phone, String password);

    String findUserIdByPhonePwd(String uid, String oldPassword);

    Result resetPwd(String uid, String newpassword, String oldPassword);

    void register(Map<String, String> registerUserInfoMap);
}
