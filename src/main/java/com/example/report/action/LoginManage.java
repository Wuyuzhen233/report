package com.example.report.action;

import com.example.report.common.enums.ErrorCode;
import com.example.report.common.utils.MD5Util;
import com.example.report.domain.DTO.UserLoginDTO;
import com.example.report.domain.User;
import com.example.report.helper.Result;
import com.example.report.service.UserManageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@CrossOrigin
@RestController
@RequestMapping("/userMange")
@Slf4j
public class LoginManage {
    @Autowired
    UserManageService userManageService;

    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO userLoginDTO) {
        System.out.println("22222222222222222");
        String phone=userLoginDTO.getPhone();
        String password=userLoginDTO.getPassword();
        if (null == userLoginDTO) {
            return  Result.failed(ErrorCode.LOGIN_INFO_INCOMPLETE, "登录信息不完整");
        }
        //String md5Password = MD5Util.string2MD5(userLoginDTO.getPassWord());
        try {
            log.info("竞拍者用户名(手机号)密码登录。\n手机号：{} \n密码：{}",  userLoginDTO.getPhone(),userLoginDTO.getPassword());
            User user =
                    userManageService.userLogin(userLoginDTO.getPhone(), userLoginDTO.getPassword());
            if (null == user) {
                return Result.failed(ErrorCode.USER_NOT_EXISTS, "用户密码错误或用户不存在");
            }
            // 生成token传入参数对象
            //LoginInfoResponseDTO loginInfoResponseDTO = new LoginInfoResponseDTO(bidderUserDTO);
            // 生成token封装对象
           // LoginTokenResponseDTO loginTokenResponseDTO = tokenGenerator.generateLoginToken(loginInfoResponseDTO);
            return Result.success(user);
            //return Result.success(loginTokenResponseDTO);
        } catch (Exception e) {
            log.error("竞拍者用户名(手机号)密码登录异常{}", e);
            return Result.failed(ErrorCode.USER_NOT_LOGIN, "用户登录失败");
        }

    }

}
