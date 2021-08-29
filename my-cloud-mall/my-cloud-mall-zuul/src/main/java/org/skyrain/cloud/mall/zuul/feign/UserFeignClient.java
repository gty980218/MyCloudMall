package org.skyrain.cloud.mall.zuul.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "my-cloud-mall-user")
public interface UserFeignClient {

    @PostMapping("/isAdminRole")
    @ResponseBody
    boolean isAdminRole(@RequestBody  String userName);


}
