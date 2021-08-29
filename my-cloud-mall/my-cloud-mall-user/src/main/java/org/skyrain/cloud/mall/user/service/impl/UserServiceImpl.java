package org.skyrain.cloud.mall.user.service.impl;

import org.skyrain.cloud.mall.common.common.Constant;
import org.skyrain.cloud.mall.common.exception.CloudMallException;
import org.skyrain.cloud.mall.common.exception.CloudMallExceptionEnum;
import org.skyrain.cloud.mall.common.utils.MD5Utils;
import org.skyrain.cloud.mall.user.model.dao.UserMapper;
import org.skyrain.cloud.mall.user.model.pojo.User;
import org.skyrain.cloud.mall.user.service.UserService;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service(value = "UserService")
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    public boolean isUserExisted(String userName){
        User user=userMapper.selectByUserName(userName);
        return user==null?false:true;
    }

    @Override
    public void register(String userName, String password) {
        if(isUserExisted(userName)){
            throw new CloudMallException(CloudMallExceptionEnum.USERNAME_EXISTED);
        }
        String md5Password= MD5Utils.getMD5Password(password);
        User user=new User();
        user.setUsername(userName);
        user.setPassword(md5Password);
        userMapper.insertSelective(user);
    }

    @Override
    public User login(String userName, String password){
        User user=userMapper.selectByUserName(userName);
        if(user==null){
            throw new CloudMallException(CloudMallExceptionEnum.USER_NOT_EXISTED);
        }
        String MD5Password=MD5Utils.getMD5Password(password);
        if(!user.getPassword().equals(MD5Password)){
            throw new CloudMallException(CloudMallExceptionEnum.PASSWORD_ERROR);
        }
        return user;
    }

    @Override
    public User isLogin(HttpSession session){
        User user=(User)session.getAttribute(Constant.CURRENT_USER);
        return user;
    }

    @Override
    public int update(String signature, HttpSession session){
        User user=isLogin(session);
        if(user==null){
            throw new CloudMallException(CloudMallExceptionEnum.NEED_LOGIN);
        }else{
            user.setPersonalizedSignature(signature);
            return userMapper.updateByPrimaryKeySelective(user);
        }
    }

    @Override
    public User adminLogin(String userName, String password){
        User user=userMapper.selectByUserName(userName);
        if(user==null){
            throw new CloudMallException(CloudMallExceptionEnum.USER_NOT_EXISTED);
        }
        if(!(user.getRole()==Constant.Role.ADMIN.getRole())){
            throw new CloudMallException(CloudMallExceptionEnum.NEED_ADMIN);
        }
        String MD5Password=MD5Utils.getMD5Password(password);
        if(!user.getPassword().equals(MD5Password)){
            throw new CloudMallException(CloudMallExceptionEnum.PASSWORD_ERROR);
        }
        return user;
    }

    @Override
    public boolean isAdminRole(String userName){
        User user=userMapper.selectByUserName(userName);
        if(!(user.getRole()==Constant.Role.ADMIN.getRole())){
            return false;
        }
        return true;
    }
}
