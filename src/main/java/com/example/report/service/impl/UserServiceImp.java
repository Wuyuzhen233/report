package com.example.report.service.impl;

import com.example.report.domain.User;
import com.example.report.mapper.UserMapper;
import com.example.report.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class UserServiceImp implements UserService {
    @Resource
    UserMapper userMapper;

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
                User user  = LoginUserDTOList.get(0);
                return user;
            }
            log.info("service：用户询不存在。\t用户手机号：{}\t用户密码：{}\t查询结果数量：{}" + phone + "," + password);
            return null;
        } catch (Exception e) {
            log.error("通过手机号密码查询用户异常。\t用户手机号：{}\t用户密码：{}\t{}", phone, password, e);
            return null;
        }
    }
}
