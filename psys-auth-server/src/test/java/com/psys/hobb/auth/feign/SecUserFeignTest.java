package com.psys.hobb.auth.feign;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.auth.common.BaseApplicationTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 待解决：eureka junit怎么测试等
 * 编  号：
 * 名  称：SecUserFeignTest
 * 描  述：
 * 完成日期：2018/5/19 15:45
 * 编码作者：SHJ
 */
public class SecUserFeignTest extends BaseApplicationTest {

    private static final Logger logger = LoggerFactory.getLogger(SecUserFeignTest.class);

    private @Autowired
    SecUserFeign secUserFeign;

    @Test
    public void getByUserNameTest(){
        JSONObject result = secUserFeign.getByUserName("admin");

        logger.info("user={}", JSONObject.toJSONString(result));
    }
}
