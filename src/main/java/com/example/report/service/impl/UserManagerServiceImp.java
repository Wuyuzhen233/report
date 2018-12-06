package com.example.report.service.impl;

import com.example.report.domain.User;
import com.example.report.mapper.UserMapper;
import com.example.report.service.UserManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserManagerServiceImp implements UserManageService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User userLogin(String phone, String password) {
        if (null == phone || null == password) {
            return null;
        }
        try {
            log.info("service：通过手机号密码查询竞拍者。\n用户手机号：{}\n用户密码：{}", phone, password);
            List<User> LoginUserDTOList=userMapper.queryUserByNameAndPassWord(phone, password);
            User user=new User();
            user=LoginUserDTOList.get(0);
            return user;
//            User user = new User();
//            List<User> LoginUserDTOList = userMapper.queryUserByNameAndPassWord(phone, password);
//            log.info("List"+ LoginUserDTOList);
//            if (null != LoginUserDTOList && LoginUserDTOList.size() > 0) {
//                log.info("List"+LoginUserDTOList.toString());
//                //List<String> rowList = new ArrayList();
//                user=LoginUserDTOList.get(0);
//
//                //rowList.add(SecurityConst.ROLE_USER);
//                //bidderUserDTO.setTypeList(rowList);
//                return user;
//            }
//            log.info("service：竞拍者询不存在。\n用户手机号：{}\n用户密码：{}\n查询结果数量：{}" +phone+","+password);
//            return null;
        }
        catch (Exception e) {
            log.error("通过手机号密码查询竞拍者异常。\n用户手机号：{}\n用户密码：{}\n{}", phone, password, e);
            return null;
        }
        //return null;
    }
}
