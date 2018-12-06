package com.example.report.service;

import com.example.report.domain.User;

public interface UserManageService {
    User userLogin(String phone,String password);
}
