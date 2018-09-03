package com.psys.hobb.redis.service;

import com.psys.hobb.redis.common.BaseApplicationTest;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class IRedisCommonServiceTest extends BaseApplicationTest {

    private static Logger logger = LoggerFactory.getLogger(IRedisCommonServiceTest.class);

    private @Autowired IRedisCommonService redisCommonService;

    private @Autowired StringRedisTemplate stringTemplate;

    @Test
    public void putStringTest() throws Exception {
        redisCommonService.putString("strTest", "222");

        String val = stringTemplate.opsForValue().get("strTest");
        Assert.assertEquals("222", val);
        logger.info("strTest.val={}", val);

        Thread.sleep(5000);
        val = stringTemplate.opsForValue().get("strTest");
        Assert.assertEquals("222", val);
        logger.info("strTest.val={}", val);
    }
    
    
}
