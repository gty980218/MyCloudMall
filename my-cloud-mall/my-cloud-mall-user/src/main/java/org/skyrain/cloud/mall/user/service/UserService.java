package org.skyrain.cloud.mall.user.service;

import org.skyrain.cloud.mall.user.model.pojo.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public interface UserService {
    public boolean isUserExisted(String userName);

    public void register(String userName,String password);

    User login(String userName, String password);

    User isLogin(HttpSession session);


    int update(String signature, HttpSession session);

    User adminLogin(String userName, String password);

    boolean isAdminRole(String userName);
}
