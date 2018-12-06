package com.example.report.action;

import com.example.report.common.enums.ErrorCode;
import com.example.report.domain.DTO.UserLoginDTO;
import com.example.report.domain.User;
import com.example.report.helper.Result;
import com.example.report.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/loginController")
@Slf4j
public class LoginController {
    @Autowired
    UserService userService;

    /**
     * 用户登陆
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO userLoginDTO) {
        if (null == userLoginDTO) {
            return Result.failed(ErrorCode.LOGIN_INFO_INCOMPLETE, "登录信息不完整");
        }
        String phone = userLoginDTO.getPhone();
        String password = userLoginDTO.getPassword();
        try {
            log.info("用户通过手机号密码登录。\t手机号：{} \t密码：{}", phone, password);
            User user = userService.userLogin(phone, password);
            if (null == user) {
                return Result.failed(ErrorCode.USER_NOT_EXISTS, "用户密码错误或用户不存在");
            }
            return Result.success(user);
        } catch (Exception e) {
            log.error("用户手机号密码登录异常{}", e);
            return Result.failed(ErrorCode.USER_NOT_LOGIN, "用户登录失败");
        }
    }
}
