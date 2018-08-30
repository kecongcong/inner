package com.inner;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * springboot 启动类
 * 允许跨越访问
 *
 * @author kecc
 */
@CrossOrigin
@EnableEncryptableProperties
@EnableDubboConfiguration
@SpringBootApplication
@MapperScan("com.inner.service.*.dao")
public class InnerWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(InnerWebApplication.class, args);
    }
}

