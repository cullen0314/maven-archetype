package com.cullen.controller;

import com.cullen.HelloService;
import com.cullen.user.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description: 
 * @author wangyijun
 * @date 2019/12/27 11:23
 */
@RestController
public class HelloController {
    @Resource
    private HelloService helloService;
    @Resource
    private UserService userService;

    @RequestMapping("/say_hello")
    public String hello() {
        return helloService.sayHello();
    }

    @RequestMapping("/user")
    public String user() {
        return userService.testUser();
    }
}
