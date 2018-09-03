package com.psys.hobb.sec.redis;

import com.alibaba.fastjson.JSON;
import com.psys.hobb.common.sso.bean.SsoUser;
import com.psys.hobb.redis.bean.User;
import com.psys.hobb.sec.common.BaseApplicationTest;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class SsoUserRedisTemplateTest extends BaseApplicationTest {

    private static Logger logger = LoggerFactory.getLogger(SsoUserRedisTemplateTest.class);

    @Autowired
    private RedisTemplate<String, SsoUser> ssoUserTemplate;

    @Test
    public void testUserRedisTemplate() throws Exception {
        // 保存对象
        SsoUser ssoUser = new SsoUser();
        ssoUser.setUserId(1);
        ssoUser.setUserName("张三");

        ssoUserTemplate.opsForValue().set("test:user:"+ssoUser.getUserId(), ssoUser);

        SsoUser result = ssoUserTemplate.opsForValue().get("test:user:"+ssoUser.getUserId());

        logger.info("result={}", JSON.toJSONString(result));
    }
    
}
