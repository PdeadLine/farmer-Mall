package com.farmer.jedis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * create by sintai_zx
 * 2018/9/3 11:12
 */
public class TestJedis {

    public void testJedis() throws Exception{
        Jedis jedis = new Jedis("192.168.88.127", 6379);
        jedis.set("sintaiKey", "123456");
        String result = jedis.get("sintaiKey");
        System.out.println(result);
        jedis.close();
    }

    public void testJedisPool() {
        JedisPool jedisPool = new JedisPool("192.168.88.127", 6379);
        Jedis jedis = jedisPool.getResource();
        jedis.set("zixunKey", "987654");
        String result=jedis.get("zixunKey");
        System.out.println(result);
        //一定记得要关闭jedis
        jedis.close();
        //系统关闭前关闭连接池
        jedisPool.close();
    }

    public void testJedisCluster() throws IOException {
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort("192.168.88.127", 7001));
        nodes.add(new HostAndPort("192.168.88.127", 7002));
        nodes.add(new HostAndPort("192.168.88.127", 7003));
        nodes.add(new HostAndPort("192.168.88.127", 7004));
        nodes.add(new HostAndPort("192.168.88.127", 7005));
        nodes.add(new HostAndPort("192.168.88.127", 7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("clusterKey", "123123123131213");
        String result = jedisCluster.get("clusterKey");
        System.out.println(result);
        //系统关闭之前关闭JedisCluster
        jedisCluster.close();
    }

}
