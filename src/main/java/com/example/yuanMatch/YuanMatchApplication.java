package com.example.yuanMatch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.example.yuanMatch.mappers")
@EnableScheduling
@ConfigurationPropertiesScan()
public class YuanMatchApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(YuanMatchApplication.class, args);
    }

}
