package com.psys.hobb.sys.service.sec.impl;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.common.sso.bean.SsoUser;
import com.psys.hobb.common.sys.util.AssertUtil;
import com.psys.hobb.common.sys.util.constant.SysRedisConstants;
import com.psys.hobb.sys.feign.sec.SecUserFeign;
import com.psys.hobb.sys.service.sec.SecUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.psys.hobb.common.sys.util.constant.SysRedisConstants.SEC_TOURIST_MENU_KEY;
import static com.psys.hobb.common.sys.util.constant.UiPathConstants.CALL_SERVICE_NO_RESPONSE_MSG;
import static com.psys.hobb.common.sys.util.constant.UiPathConstants.CALL_SERVICE_RESPONSE_EMPTY_DATE_MSG;

@Service
public class SecUserServiceImpl implements SecUserService {

    @Autowired
    private SecUserFeign secUserFeign;

    @Autowired
    private RedisTemplate<String, SsoUser> ssoUserRedisTemplate;

    @Override
    public SsoUser getUserByCache(String tokenValue){
        String ssoUserRedisKey = SysRedisConstants.SEC_LOGIN_USER_KEY + tokenValue;
        SsoUser user = ssoUserRedisTemplate.opsForValue().get(ssoUserRedisKey);

        if(null == user){
            JSONObject userJsonResp =  secUserFeign.getLoginUser();
            AssertUtil.notNull(userJsonResp, "获取登录用户信息失败,"+ CALL_SERVICE_NO_RESPONSE_MSG);
            JSONObject userJson = new JSONObject((Map<String, Object>)userJsonResp.get("result"));
            AssertUtil.notNull(userJson, "获取登录用户信息失败,"+ CALL_SERVICE_RESPONSE_EMPTY_DATE_MSG);
            user = userJson.toJavaObject(SsoUser.class);
            ssoUserRedisTemplate.opsForValue().set(ssoUserRedisKey, user, SysRedisConstants.LOGIN_USER_EXPIRATION, SysRedisConstants.LOGIN_USER_EXPIRATION_TIMEUNIT);
        }
        return user;
    }

}
