package com.imooc.imooc_mall.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

/**
 * 缓存配置类
 *
 * @author 陈蒙欣
 * @date 2022/12/11 21:02
 */
@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    @ConditionalOnSingleCandidate
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.lockingRedisCacheWriter(connectionFactory);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofSeconds(30));
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }
}
