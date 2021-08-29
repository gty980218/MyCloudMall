package org.skyrain.cloud.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@SpringBootApplication
@EnableFeignClients
@MapperScan(value = "org.skyrain.cloud.mall.cartorder.model.dao")
public class CartAndOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartAndOrderApplication.class,args);
    }
}
