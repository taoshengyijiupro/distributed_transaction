package com.xlinclass;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>Description: </p>
 * <p>blog: http://www.xlinclass.com</p>
 *
 * @author 涛声依旧
 * @date 2019/8/26 14:14
 */

@SpringBootApplication
@MapperScan(basePackages = "com.xlinclass")
public class App1 {
    public static void main(String[] args) {
        SpringApplication.run(App1.class, args);
    }
}
