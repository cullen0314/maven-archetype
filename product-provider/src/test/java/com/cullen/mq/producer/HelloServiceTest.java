package com.cullen.mq.producer;

import com.cullen.HelloService;
import com.cullen.ProductApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductApplication.class)
public class HelloServiceTest {

    @Resource
    private HelloService helloService;

    @Test
    public void sayHello() {
        System.out.println("result:"+helloService.sayHello());
    }
}