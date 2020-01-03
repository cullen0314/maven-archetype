package com.cullen.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.IOException;

/**
 * @description: 
 * @author wangyijun
 * @date 2019/12/27 11:47
 */
@SpringBootApplication
@ImportResource({"classpath:config/spring-dubbo.xml"})
public class UserApplication implements CommandLineRunner {

    @Resource(name = "ordersDataSource")
    private DataSource ordersDataSource;

    @Resource(name = "usersDataSource")
    private DataSource usersDataSource;

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... strings){
        // orders 数据源
        System.out.println("ordersDataSource[获得数据源：]"+ordersDataSource.getClass());

        // users 数据源
        System.out.println("usersDataSource[获得数据源：{}]"+usersDataSource.getClass());
    }
}
