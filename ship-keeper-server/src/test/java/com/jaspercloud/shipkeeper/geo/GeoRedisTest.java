package com.jaspercloud.shipkeeper.geo;

import org.junit.Test;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.*;

import java.util.Set;

public class GeoRedisTest {

    @Test
    public void test() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration("107.150.121.199", 36379);
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(configuration);
        connectionFactory.afterPropertiesSet();
        StringRedisTemplate redisTemplate = new StringRedisTemplate(connectionFactory);
        redisTemplate.afterPropertiesSet();

        redisTemplate.rename("test", "bak");

//        GeoOperations<String, String> opsForGeo = redisTemplate.opsForGeo();
//        opsForGeo.add("test", new Point(120.14648441690952, 30.278320333454758), "1");
//        opsForGeo.add("test", new Point(120.14648441690952, 30.278320333454758), "2");
//        opsForGeo.add("test", new Point(120.14648441690952, 30.278320333454758), "3");
//        opsForGeo.add("test", new RedisGeoCommands.GeoLocation<String>("4", new Point(120.14648441690952, 30.278320333454758)));
//        opsForGeo.add("test", new Point(120.14648441690952, 30.278320333454758), "6");
//        opsForGeo.add("test", new Point(120.14648441690952, 30.278320333454758), "8");
//        opsForGeo.add("test", new Point(120.14648441690952, 30.278320333454758), "16");
//        opsForGeo.add("test", new Point(120.14648441690952, 30.278320333454758), "10");
//        opsForGeo.add("test", new Point(120.14648441690952, 30.278320333454758), "9");

        ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();
        opsForZSet.add("test", "10", Integer.MAX_VALUE - 10.0);
        opsForZSet.add("test", "11", Integer.MAX_VALUE - 11.0);
        opsForZSet.add("test", "12", Integer.MAX_VALUE - 12.0);
        opsForZSet.add("test", "13", Integer.MAX_VALUE - 13.0);
        opsForZSet.add("test", "14", Integer.MAX_VALUE - 14.0);
        opsForZSet.add("test", "1", 1.0);
        opsForZSet.add("test", "2", 2.0);
        opsForZSet.add("test", "3", 3.0);
        opsForZSet.add("test", "4", 4.0);
        opsForZSet.add("test", "5", 5.0);
        opsForZSet.add("test", "6", 6.0);
        opsForZSet.add("test", "7", 7.0);
        opsForZSet.add("test", "8", 8.0);
        opsForZSet.add("test", "9", 9.0);


        Long size = opsForZSet.size("test");
        int count = 4;
        for (int i = 0; i < 5; i++) {
            Set<String> range = opsForZSet.range("test", size - count * (i + 1), size - count * i - 1);
            System.out.println(range);
        }
    }
}
