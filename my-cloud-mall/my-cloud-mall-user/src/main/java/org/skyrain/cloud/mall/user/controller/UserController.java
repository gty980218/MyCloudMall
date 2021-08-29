package org.skyrain.cloud.mall.user.controller;

import org.skyrain.cloud.mall.common.common.ApiRestResponse;
import org.skyrain.cloud.mall.common.common.Constant;
import org.skyrain.cloud.mall.common.exception.CloudMallException;
import org.skyrain.cloud.mall.common.exception.CloudMallExceptionEnum;
import org.skyrain.cloud.mall.user.model.pojo.User;
import org.skyrain.cloud.mall.user.service.UserService;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public ApiRestResponse register(@RequestParam("userName") String userName,@RequestParam("password") String password){

        if(StringUtils.isEmpty(userName)){

            return ApiRestResponse.error(CloudMallExceptionEnum.USER_EMPTY);
        }
        if(StringUtils.isEmpty(password)){
            return ApiRestResponse.error(CloudMallExceptionEnum.PASSWORD_EMPTY);
        }
        if(password.length()<8){
            return ApiRestResponse.error(CloudMallExceptionEnum.PASSWORD_TOO_SHORT);
        }
        userService.register(userName,password);
        return ApiRestResponse.success();
    }

    @PostMapping("/login")
    @ResponseBody
    public ApiRestResponse login(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpSession session){
        User user=userService.login(userName,password);
        session.setAttribute(Constant.CURRENT_USER,user);
        return ApiRestResponse.success();
    }

    @PostMapping("/update")
    @ResponseBody
    public ApiRestResponse update(String signature,HttpSession session){
        if(!(userService.update(signature,session)>0)){
            return ApiRestResponse.error(CloudMallExceptionEnum.UPDATE_FAILED);
        }
        return ApiRestResponse.success();
    }

    @PostMapping("/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session){
        User user = (User)session.getAttribute(Constant.CURRENT_USER);
        if(user==null){
            return ApiRestResponse.error(CloudMallExceptionEnum.NEED_LOGIN);
        }else{
            session.removeAttribute(Constant.CURRENT_USER);
        }
        return ApiRestResponse.success();
    }

    @PostMapping("/adminLogin")
    @ResponseBody
    public ApiRestResponse adminLogin(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpSession session){
        User user=userService.adminLogin(userName,password);
        session.setAttribute(Constant.CURRENT_USER,user);
        return ApiRestResponse.success();
    }

    @PostMapping("/isAdminRole")
    @ResponseBody
    public boolean isAdminRole(@RequestBody  String userName){
        boolean res=userService.isAdminRole(userName);
        return res;
    }

    @PostMapping("/curUser")
    @ResponseBody
    public User getCurUser(HttpSession session){
        User user = (User)session.getAttribute(Constant.CURRENT_USER);
        return user;
    }


}
