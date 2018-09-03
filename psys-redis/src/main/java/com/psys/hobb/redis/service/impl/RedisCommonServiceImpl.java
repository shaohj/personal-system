package com.psys.hobb.redis.service.impl;

import com.psys.hobb.common.sys.util.constant.SysRedisConstants;
import com.psys.hobb.redis.service.IRedisCommonService;
import com.psys.hobb.redis.util.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisCommonServiceImpl implements IRedisCommonService {

    private @Autowired StringRedisTemplate stringTemplate;

    @Override
    public void putString(String key, String value) {
        putString(key, value, RedisConstants.DEFAULT_EXPIRATION, RedisConstants.DEFAULT_EXPIRATION_TIMEUNIT);
    }

    public void putLoginUserString(String key, String value){
        putString(key, value, SysRedisConstants.LOGIN_USER_EXPIRATION, SysRedisConstants.LOGIN_USER_EXPIRATION_TIMEUNIT);
    };

    @Override
    public void putString(String key, String value, long expiration, TimeUnit timeUnit) {
        ValueOperations<String, String> valOpt = stringTemplate.opsForValue();
        valOpt.set(key, value, expiration, timeUnit);
    }

    public String getString(String key){
        return stringTemplate.opsForValue().get(key);
    };

    public void delete(String key){
        stringTemplate.delete(key);
    };

}
