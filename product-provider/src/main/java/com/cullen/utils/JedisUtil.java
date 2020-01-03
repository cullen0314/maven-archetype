package com.cullen.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Set;

/**
 * @description: 
 * @author wangyijun
 * @date 2019/12/27 15:31
 */
@Component
public class JedisUtil {

    private JedisPool pool;

    private static String URL = "127.0.0.1";

    private static int PORT = 6379;

    private static String PASSWORD = "root";

    // ThreadLocal，给每个线程 都弄一份 自己的资源
    private final static ThreadLocal<JedisPool> threadPool = new ThreadLocal<JedisPool>();
    private final static ThreadLocal<Jedis> threadJedis = new ThreadLocal<Jedis>();

    private final static int MAX_TOTAL = 100;   // 最大分配实例
    private final static int MAX_IDLE = 50;     // 最大空闲数
    private final static int MAX_WAIT_MILLIS = -1; // 最大等待数

    /**
     * 获取 jedis池
     * @return
     */
    public JedisPool getPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取，如果赋值为-1，则表示不限制；
        // 如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)
        jedisPoolConfig.setMaxTotal(MAX_TOTAL);
        // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例
        jedisPoolConfig.setMaxIdle(MAX_IDLE);
        // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException
        jedisPoolConfig.setMaxWaitMillis(MAX_WAIT_MILLIS);

        final int timeout = 60 * 1000;
        pool = new JedisPool(jedisPoolConfig, URL, PORT, timeout);

        return pool;
    }

    /**
     * 在jedis池中 获取 jedis
     * @return
     */
    private Jedis common(){
        // 从 threadPool中取出 jedis连接池
        pool = threadPool.get();
        // 为空，则重新产生 jedis连接池
        if(pool == null){
            pool = this.getPool();
            // 将jedis连接池维护到threadPool中
            threadPool.set(pool);
        }
        // 在threadJedis中获取jedis实例
        Jedis jedis = threadJedis.get();
        // 为空，则在jedis连接池中取出一个
        if(jedis == null){
            jedis = pool.getResource();
            // 验证密码
            jedis.auth(PASSWORD);
            // 将jedis实例维护到threadJedis中
            threadJedis.set(jedis);
        }
        return jedis;
    }

    /**
     * 释放资源
     */
    public void closeAll(){
        Jedis jedis = threadJedis.get();
        if(jedis != null){
            threadJedis.set(null);
            JedisPool pool = threadPool.get();
            if(pool != null){
                // 释放连接，归还给连接池
                pool.returnResource(jedis);
            }
        }
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean existsKey(String key){
        Jedis jedis = this.common();
        return jedis.exists(key);
    }

    /**
     * 删除
     * @param key
     * @return
     */
    public Long delValue(String key){
        Jedis jedis = this.common();
        return jedis.del(key);
    }

    // ----------------------------对String类型的操作-----------------------------------------
    /**
     * 增加  修改
     * @param key
     * @param value
     */
    public String setValue(String key, String value) {
        Jedis jedis = this.common();
        return jedis.set(key, value);
    }

    /**
     * 查询
     * @param key
     * @return
     */
    public String getValue(String key){
        Jedis jedis = this.common();
        return jedis.get(key);
    }

    /**
     * 追加数据
     * @param key
     * @param value
     */
    public void appendValue(String key, String value){
        Jedis jedis = this.common();
        jedis.append(key, value);
    }

    /**
     * 测试 String
     */
    // @Test
    public void testString(){
        if(this.existsKey("name")){
            System.out.println("这一个key存在了！");
            this.appendValue("name", "xbq6666");

            String name = this.getValue("name");
            System.out.println("name===" + name);

            long flag = this.delValue("name");
            System.out.println(flag);

        }else {
            this.setValue("name", "javaCoder");
            String name = this.getValue("name");
            System.out.println("name===" + name);
        }
    }

    // ----------------------------对List类型的操作------------------------------------------
    /**
     * 保存到链表
     * @param key
     * @param keys
     * @return
     */
    public long lpush(String key, String ...keys){
        Jedis jedis = this.common();
        return jedis.lpush(key, keys);
    }

    /**
     * 取出链表中的全部元素
     * @param key
     * @return
     */
    public List<String> lrange(String key) {
        Jedis jedis = this.common();
        return jedis.lrange(key, 0, -1);
    }

    /**
     * 查询出链表中的元素个数
     * @param key
     * @return
     */
    public long llen(String key){
        Jedis jedis = this.common();
        return jedis.llen(key);
    }

    /**
     * 取出链表中的头部元素
     * @param key
     * @return
     */
    public String lpop(String key){
        Jedis jedis = this.common();
        return jedis.lpop(key);
    }

    // ----------------------------对Hash类型的操作------------------------------------------
    /**
     * 添加
     * @param key
     * @param field
     * @param value
     * @return
     */
    public long hset(String key, String field, String value) {
        Jedis jedis = this.common();
        return jedis.hset(key, field, value);
    }

    /**
     * 查询
     * @param key
     * @param field
     * @return
     */
    public String hget(String key, String field){
        Jedis jedis = this.common();
        return jedis.hget(key, field);
    }

    /**
     * 判断 key 中的field 是否存在
     * @param key
     * @param field
     * @return
     */
    public boolean hexists(String key, String field){
        Jedis jedis = this.common();
        return jedis.hexists(key, field);
    }

    /**
     * 删除
     * @param key
     * @param fields
     * @return
     */
    public long hdel(String key, String ...fields){
        Jedis jedis = this.common();
        return jedis.hdel(key, fields);
    }

    // ----------------------------对Set类型的操作--------------------------------------------
    /**
     * 添加元素
     * @param key
     * @param members
     * @return
     */
    public long sadd(String key, String ...members){
        Jedis jedis = this.common();
        return jedis.sadd(key, members);
    }

    /**
     * 查询出set中的所有元素
     * @param key
     * @return
     */
    public Set<String> sMember(String key){
        Jedis jedis = this.common();
        return jedis.smembers(key);
    }

    /**
     * 查询出 set中元素的个数
     * @param key
     * @return
     */
    public long scard(String key){
        Jedis jedis = this.common();
        return jedis.scard(key);
    }

    // ----------------------------对ZSet类型的操作--------------------------------------------
    /**
     * 在zset中添加元素
     * @param key
     * @param score
     * @param member
     * @return
     */
    public long zadd(String key, double score ,String member){
        Jedis jedis = this.common();
        return jedis.zadd(key, score, member);
    }

    /**
     * 查询所有元素
     * @param key
     * @return
     */
    public Set<String> zrange(String key){
        Jedis jedis = this.common();
        return jedis.zrange(key, 0, -1);
    }

    /**
     * 查询zset中的元素个数
     * @param key
     * @return
     */
    public long zcard(String key){
        Jedis jedis = this.common();
        return jedis.zcard(key);
    }

    /**
     * 删除zset中的一个 或者多个元素
     * @param key
     * @param members
     * @return
     */
    public long zrem(String key, String ...members){
        Jedis jedis = this.common();
        return jedis.zrem(key, members);
    }
}
