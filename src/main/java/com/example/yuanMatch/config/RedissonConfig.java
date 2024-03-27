package com.example.yuanMatch.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 *@author  fyy
 *@version 1.0
 *@time    2024-03-26 15:10
 *@project YuanMatch
 */
@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
@Data
public class RedissonConfig
{
    private String host;

    private String port;

    @Bean
    public RedissonClient redissonClient() {
        // 1. 创建配置
        Config config = new Config();
        System.out.println(host+port);
        String redisAddress = String.format("redis://%s:%s", host, port);

        config.useSingleServer().setAddress(redisAddress).setDatabase(1).setPassword("123456");

        // 2. 创建实例
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
