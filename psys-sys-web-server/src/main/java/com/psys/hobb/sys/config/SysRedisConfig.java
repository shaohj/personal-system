package com.psys.hobb.sys.config;

import com.psys.hobb.common.sso.bean.SsoUser;
import com.psys.hobb.common.sys.model.SecResourceParam;
import com.psys.hobb.redis.config.RedisObjectSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 编  号：
 * 名  称：SysRedisConfig
 * 描  述：
 * 完成日期：2018/7/14 23:10
 * 编码作者：SHJ
 */
@Configuration
public class SysRedisConfig {

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
     * @Title: resourceTouristRedisTemplate
     * @param redisFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, SecResourceParam> resourceTouristRedisTemplate(RedisConnectionFactory redisFactory) {
        RedisTemplate<String, SecResourceParam> template = new RedisTemplate<>();
        template.setConnectionFactory(redisFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisObjectSerializer());
        return template;
    }

}
