package com.psys.hobb.sec.config;

import com.psys.hobb.common.sso.bean.SsoUser;
import com.psys.hobb.redis.bean.User;
import com.psys.hobb.redis.config.RedisObjectSerializer;
import com.psys.hobb.sec.model.sec.SecResource;
import com.psys.hobb.sec.model.sec.SecResourceTourist;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 编  号：
 * 名  称：SecRedisConfig
 * 描  述：
 * 完成日期：2018/6/18 15:01
 * 编码作者：SHJ
 */
@Configuration
public class SecRedisConfig {

    /**
     * 自定义ssoUserRedisTemplate模板
     * @Title: ssoUserRedisTemplate
     * @param redisFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, SsoUser> ssoUserRedisTemplate(RedisConnectionFactory redisFactory) {
        RedisTemplate<String, SsoUser> template = new RedisTemplate<>();
        template.setConnectionFactory(redisFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisObjectSerializer());
        return template;
    }

    /**
     * 自定义resourceRedisTemplate模板
     * @Title: resourceRedisTemplate
     * @param redisFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, SecResource> resourceRedisTemplate(RedisConnectionFactory redisFactory) {
        RedisTemplate<String, SecResource> template = new RedisTemplate<>();
        template.setConnectionFactory(redisFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisObjectSerializer());
        return template;
    }

    /**
     * 自定义resourceRedisTemplate模板
     * @Title: resourceTouristRedisTemplate
     * @param redisFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, SecResourceTourist> resourceTouristRedisTemplate(RedisConnectionFactory redisFactory) {
        RedisTemplate<String, SecResourceTourist> template = new RedisTemplate<>();
        template.setConnectionFactory(redisFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisObjectSerializer());
        return template;
    }

}
