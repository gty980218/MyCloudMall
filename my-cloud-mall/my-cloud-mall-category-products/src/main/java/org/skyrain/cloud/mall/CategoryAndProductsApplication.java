package org.skyrain.cloud.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@SpringBootApplication
@MapperScan(value = "org.skyrain.cloud.mall.categoryproducts.model.dao")
public class CategoryAndProductsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CategoryAndProductsApplication.class,args);
    }
}
