package com.example.report.service.impl;

import com.example.report.domain.securityEntity.JwtUser;
import com.example.report.domain.securityEntity.UserAuth;
import com.example.report.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by aimu on 2018/12/14.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserAuth userAuth = userMapper.findByUsername(s);
        return new JwtUser(userAuth);
    }

}
