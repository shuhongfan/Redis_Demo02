package com.shf;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class TestPing {
    @Test
    public void test1() {
//        1. new Jedis对象即可
        Jedis jedis = new Jedis("192.168.120.10", 6379);
        System.out.println(jedis.ping());

        jedis.close();
    }
}
