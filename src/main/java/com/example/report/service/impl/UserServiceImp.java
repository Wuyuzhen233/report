package com.example.report.service.impl;

import com.example.report.domain.securityEntity.UserAuth;
import com.example.report.support.ResultCode;
import com.example.report.domain.User;
import com.example.report.support.Result;
import com.example.report.mapper.UserMapper;
import com.example.report.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImp implements UserService {
    @Resource
    UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User userLogin(String phone, String password) {
        if (null == phone || null == password) {
            return null;
        }
        try {
            log.info("service：通过手机号密码查询用户。\t用户手机号：{}\t用户密码：{}", phone, password);
            List<User> LoginUserDTOList = userMapper.queryUserByNameAndPassWord(phone, password);
            log.info("List" + LoginUserDTOList);
            if (null != LoginUserDTOList && LoginUserDTOList.size() > 0) {
                log.info("List" + LoginUserDTOList.toString());
                User user = LoginUserDTOList.get(0);
                return user;
            }
            log.info("service：用户不存在。\t用户手机号：{}\t用户密码：{}\t查询结果数量：{}" + phone + "," + password);
            return null;
        } catch (Exception e) {
            log.error("通过手机号密码查询用户异常。\t用户手机号：{}\t用户密码：{}\t{}", phone, password, e);
            return null;
        }
    }

    @Override
    public String findUserIdByPhonePwd(String uid, String oldPassword) {
        Map<String,String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("oldPassword", oldPassword);
        String userId = userMapper.findUserIdByUidPwd(map);
        return userId;
    }

    @Override
    public Result resetPwd(String uid, String newpassword, String oldPassword) {
        if (oldPassword.trim().length() > 0) {
            Map<String,String> map = new HashMap<>();
            map.put("uid", uid);
            map.put("newpassword", newpassword);
            map.put("oldPassword", oldPassword);
            userMapper.resetPwd(map);
            return Result.success();
        } else {
            log.error("通过手机号密码更改密码异常。\t新密码：{}长度为零", oldPassword);
            return Result.failed(ResultCode.FAIL_DATABASE, "用户修改密码异常");
        }
    }

    @Override
    public void register(Map<String, String> registerUserInfoMap) {
        registerUserInfoMap.put("password", bCryptPasswordEncoder.encode(registerUserInfoMap.get("password")));
        userMapper.register(registerUserInfoMap);
    }


}
