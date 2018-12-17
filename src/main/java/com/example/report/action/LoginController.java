package com.example.report.action;

import com.example.report.domain.securityEntity.UserAuth;
import com.example.report.support.ResultCode;
import com.example.report.domain.DTO.UserLoginDTO;
import com.example.report.domain.User;
import com.example.report.support.Result;
import com.example.report.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    UserService userService;

    /**
     * 用户注册
     * {"username":"aimu","password":"aimu","role":"ROLE_USER/ROLE_ADMIN"}
     *
     * @param registerUserInfoMap
     * @return
     */
    @PostMapping("/register")
    public Result registerUser(@RequestBody Map<String, String> registerUserInfoMap) {
        if (registerUserInfoMap.containsKey("username")
                && registerUserInfoMap.containsKey("password")
                && registerUserInfoMap.containsKey("role")) {
            try {
                userService.register(registerUserInfoMap);
            } catch (Exception e) {
                return Result.failed(ResultCode.FAIL_DATABASE, "数据库操作失败，注册用户异常");
            }
            return Result.success(ResultCode.SUCCESS);
        } else {
            return Result.failed(ResultCode.PARAMS_INCOMPLETE, "缺少必要入参");
        }
    }

    /**
     * 用户登陆
     * {"phone":"123","password":"123"}
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("success!!!!");
        if (null == userLoginDTO) {
            return Result.failed(ResultCode.LOGIN_INFO_INCOMPLETE, "登录信息不完整");
        }
        String phone = userLoginDTO.getPhone();
        String password = userLoginDTO.getPassword();
        try {
            log.info("用户通过手机号密码登录。\t手机号：{} \t密码：{}", phone, password);
            User user = userService.userLogin(phone, password);
            if (null == user) {
                return Result.failed(ResultCode.USER_NOT_EXISTS, "用户密码错误或用户不存在");
            }
            log.info("userinfo" + user);
            return Result.success(user);
        } catch (Exception e) {
            log.error("用户手机号密码登录异常{}", e);
            return Result.failed(ResultCode.USER_NOT_LOGIN, "用户登录失败");
        }
    }

    /**
     * header:Content-Type=application/json
     * body:{"uid":1,"password":"123456","oldPassword":"111222"}
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/resetPwd")
    public Result resetPwd(@RequestBody UserLoginDTO userLoginDTO) {
        if (null == userLoginDTO) {
            return Result.failed(ResultCode.LOGIN_INFO_INCOMPLETE, "登录信息不完整");
        }
        String uid = String.valueOf(userLoginDTO.getUid());
        String newpassword = userLoginDTO.getPassword();
        String oldPassword = userLoginDTO.getOldPassword();
        try {
            log.info("用户通过手机号密码更改密码。\t用户id：{}\t新密码：{}\t原密码：{}", uid, newpassword, oldPassword);
            String userId = userService.findUserIdByPhonePwd(uid, oldPassword);
            if (null == userId) {
                return Result.failed(ResultCode.USER_NOT_EXISTS, "用户密码错误或用户不存在");
            }
            userService.resetPwd(uid, newpassword, oldPassword);
            return Result.success();
        } catch (Exception e) {
            log.error("用户修改密码异常{}", e);
            return Result.failed(ResultCode.USER_NOT_LOGIN, "用户修改密码失败");
        }
    }
}
