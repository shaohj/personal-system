package com.psys.hobb.redis.service;

import java.util.concurrent.TimeUnit;

public interface IRedisCommonService {

    public void putString(String key, String value);

    public void putLoginUserString(String key, String value);

    public void putString(String key, String value, long expiration, TimeUnit timeUnit);

    public String getString(String key);

    public void delete(String key);

}
