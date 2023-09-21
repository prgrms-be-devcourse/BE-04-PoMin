package com.ray.pomin.global.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RedisConfiguration {

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisProperties properties) {
        return new LettuceConnectionFactory(properties.getHost(), properties.getPort());
    }

    @Bean
    public StringRedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);

        return template;
    }

    @Bean
    public RedisConnectionFactory mailRedisConnectionFactory(RedisProperties properties) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(properties.getHost(), properties.getPort());
        configuration.setDatabase(1);

        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public StringRedisTemplate mailRedisTemplate(RedisConnectionFactory mailRedisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(mailRedisConnectionFactory);

        return template;
    }

}
