package com.cullen;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * @description: 
 * @author wangyijun
 * @date 2019/12/27 10:50
 */
@Slf4j
@SpringBootApplication
@ImportResource({"classpath:config/spring-dubbo.xml"})
@MapperScan(basePackages = "com.cullen.mapper") // 扫描对应 Mapper 接口所在的包路径
public class ProductApplication implements CommandLineRunner {

    @Resource
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... strings){
        System.out.println("获得数据源："+ dataSource.getClass());
    }
}
