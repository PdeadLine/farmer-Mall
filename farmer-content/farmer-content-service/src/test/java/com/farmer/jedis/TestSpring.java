package com.farmer.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * create by sintai_zx
 * 2018/9/3 11:13
 */
public class TestSpring {

    /**
     * 集群版redis整合
     */
    public void testJedisClusterClient() {
        //初始化spring 容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-jedis.xml");
        //从容其中取得jedisClient对象
        JedisClient jedisClient   = applicationContext.getBean(JedisClient.class);
        System.out.println(jedisClient.get("sintai"));
    }

    /**
     * 单机版redis整合
     */
    public void testJedisPoolClient() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-jedis.xml");
        JedisClient jedisClient   = applicationContext.getBean(JedisClient.class);
        System.out.println(jedisClient.get("sintaikey"));
    }
}
