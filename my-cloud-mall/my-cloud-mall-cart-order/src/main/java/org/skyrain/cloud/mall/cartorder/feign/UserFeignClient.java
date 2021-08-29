package org.skyrain.cloud.mall.cartorder.feign;

import org.skyrain.cloud.mall.common.common.Constant;
import org.skyrain.cloud.mall.user.model.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@FeignClient(value = "my-cloud-mall-user")
public interface UserFeignClient {

    @PostMapping("/curUser")
    @ResponseBody
    User getCurUser();
}
