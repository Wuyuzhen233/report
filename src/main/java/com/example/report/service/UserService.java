package com.example.report.service;

import com.example.report.domain.User;

public interface UserService {
    User userLogin(String phone,String password);
}
