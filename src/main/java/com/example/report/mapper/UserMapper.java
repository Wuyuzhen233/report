package com.example.report.mapper;

import com.example.report.domain.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;


import java.util.List;
public interface UserMapper extends Mapper<User> {
    List<User> queryUserByNameAndPassWord(@Param("phone") String phone,@Param("password") String password);
}
