package org.skyrain.cloud.mall.common.utils;

import org.skyrain.cloud.mall.common.common.Constant;
import org.springframework.util.DigestUtils;

public class MD5Utils {

    public static String getMD5Password(String password){
        String base=password+Constant.getSalt();
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

}
