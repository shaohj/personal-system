package com.psys.hobb.sys.service.sec.impl;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.common.sys.model.SecResourceParam;
import com.psys.hobb.common.sys.util.AssertUtil;
import com.psys.hobb.common.sys.util.constant.SysRedisConstants;
import com.psys.hobb.sys.feign.sec.SecResourceFeign;
import com.psys.hobb.sys.service.sec.SecResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.psys.hobb.common.sys.util.constant.SysRedisConstants.SEC_USER_MENU_KEY;
import static com.psys.hobb.common.sys.util.constant.UiPathConstants.CALL_SERVICE_NO_RESPONSE_MSG;
import static com.psys.hobb.common.sys.util.constant.UiPathConstants.CALL_SERVICE_RESPONSE_EMPTY_DATE_MSG;

@Service
public class SecResourceServiceImpl implements SecResourceService {

    @Autowired
    private SecResourceFeign secResourceFeign;

    @Autowired
    private RedisTemplate<String, SecResourceParam> secResourceTouristTemplate;

    @Override
    public SecResourceParam getUserMenuByCache(Integer userId) {
        //对象序列化和反序列化存储对象
        String userResourceRedisKey = SEC_USER_MENU_KEY + userId;

        SecResourceParam userMenu = secResourceTouristTemplate.opsForValue().get(userResourceRedisKey);

        if(null == userMenu){
            JSONObject userMenuJsonResp =  secResourceFeign.getLoginUserMenu();
            AssertUtil.notNull(userMenuJsonResp, "获取用户菜单失败,"+ CALL_SERVICE_NO_RESPONSE_MSG);
            JSONObject userMenuJson = new JSONObject((Map<String, Object>)userMenuJsonResp.get("result"));
            AssertUtil.notNull(userMenuJson, "获取用户菜单失败,"+ CALL_SERVICE_RESPONSE_EMPTY_DATE_MSG);
            userMenu = userMenuJson.toJavaObject(SecResourceParam.class);
            secResourceTouristTemplate.opsForValue().set(userResourceRedisKey, userMenu, SysRedisConstants.LOGIN_USER_EXPIRATION, SysRedisConstants.LOGIN_USER_EXPIRATION_TIMEUNIT);
        } else{
            secResourceTouristTemplate.expire(userResourceRedisKey, SysRedisConstants.LOGIN_USER_EXPIRATION, SysRedisConstants.LOGIN_USER_EXPIRATION_TIMEUNIT);
        }
        return userMenu;
    }
}
