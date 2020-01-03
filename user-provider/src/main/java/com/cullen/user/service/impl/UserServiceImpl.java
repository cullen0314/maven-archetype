package com.cullen.user.service.impl;

import com.cullen.user.UserService;
import org.springframework.stereotype.Service;

/**
 * @description: 
 * @author wangyijun
 * @date 2019/12/27 11:50
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public String testUser() {
        return "user test";
    }
}
