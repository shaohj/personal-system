package com.psys.hobb.sec.dao.auth;

import com.alibaba.fastjson.JSON;
import com.psys.hobb.common.sso.bean.SsoUser;
import com.psys.hobb.sec.common.BaseApplicationTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class OauthAccessTokenRepoTest extends BaseApplicationTest {

    private static final Logger logger = LoggerFactory.getLogger(OauthAccessTokenRepoTest.class);

    private @Autowired  OauthAccessTokenRepo oauthAccessTokenRepo;

    @Test
    public void findByTokenIdTest(){
        String tokenId = "08a42f9f9189fc7920ca28d741079f05";
        String userJsonStr = oauthAccessTokenRepo.findByTokenId(tokenId);
        logger.info("userJson={}", userJsonStr);
        if(null != userJsonStr){
            JSON userJson = JSON.parseObject(userJsonStr);
            SsoUser user = JSON.toJavaObject(userJson, SsoUser.class);
        }
    }

}
