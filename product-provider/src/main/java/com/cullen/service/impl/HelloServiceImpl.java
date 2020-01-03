package com.cullen.service.impl;

import com.cullen.HelloService;
import com.cullen.dao.cache.HelloCacheDAO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description: 
 * @author wangyijun
 * @date 2019/12/27 11:23
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Resource
    private HelloCacheDAO helloCacheDAO;

    @Override
    public String sayHello() {
        return helloCacheDAO.hello();
    }
}
