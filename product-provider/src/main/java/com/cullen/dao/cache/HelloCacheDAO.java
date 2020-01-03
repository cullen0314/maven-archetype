package com.cullen.dao.cache;

import com.cullen.utils.JedisUtil;
import org.springframework.stereotype.Repository;

/**
 * @description: 
 * @author wangyijun
 * @date 2019/12/27 15:40
 */
@Repository
public class HelloCacheDAO {

    JedisUtil jedisUtil = new JedisUtil();

    public String hello() {
         return jedisUtil.getValue("UserKey:id1");
    }
}
