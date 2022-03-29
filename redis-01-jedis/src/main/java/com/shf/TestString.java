package com.shf;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

public class TestString {
    @Test
    public void test1() throws InterruptedException {
        Jedis jedis = new Jedis("192.168.120.10", 6379);

        jedis.flushDB();
        System.out.println("================增加数据==================");
        System.out.println(jedis.set("key1", "value1"));
        System.out.println(jedis.set("key2", "value2"));
        System.out.println(jedis.set("key3", "value3"));
        System.out.println("删除键key2:"+jedis.del("key2"));
        System.out.println("获取键key2:"+jedis.get("key2"));
        System.out.println("修改键key2:"+jedis.set("key1", "value1Changed"));
        System.out.println("获取key1的值:"+jedis.get("key1"));
        System.out.println("在key3后面插入值:"+jedis.append("key3", "End"));
        System.out.println("key3的值:"+jedis.get("key3"));
        System.out.println("增加多个键值对:"+jedis.mset("key01","value1","key02","value2","key03","value3"));
        System.out.println("获取多个键值对:"+jedis.mget("key01","key02","key03"));
        System.out.println("获取多个键值对:"+jedis.mget("key01","key02","key03","key04"));
        System.out.println("删除多个键值对:"+jedis.del("key01","key02"));
        System.out.println("获取多个键值对:"+jedis.mget("key01","key02","key03"));

        jedis.flushDB();
        System.out.println("================新增键值对以防止覆盖原先值===========================");
        System.out.println(jedis.setnx("key1", "value1"));
        System.out.println(jedis.setnx("key2", "value2"));
        System.out.println(jedis.setnx("key2", "value2-new"));
        System.out.println(jedis.get("key1"));
        System.out.println(jedis.get("key2"));

        System.out.println("===============新增键值对并设置有效时间========================");
        System.out.println(jedis.setex("key3", 2, "value3"));
        System.out.println(jedis.get("key3"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println(jedis.get("key3"));

        System.out.println("==================获取原值,更新为新值=========================");
        System.out.println(jedis.getSet("key2", "key2GetSet"));
        System.out.println(jedis.get("key2"));

        System.out.println("获得key2的值的字符串:"+jedis.getrange("key2", 2, 4));

        jedis.close();
    }
}
