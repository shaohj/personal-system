package com.psys.hobb.sys.service.sec.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.common.sys.model.SecResourceParam;
import com.psys.hobb.common.sys.util.AssertUtil;
import com.psys.hobb.common.sys.util.constant.SysRedisConstants;
import com.psys.hobb.sys.feign.sec.SecResourceTouristFeign;
import com.psys.hobb.sys.service.sec.SecResourceTouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.psys.hobb.common.sys.util.constant.SysRedisConstants.SEC_TOURIST_MENU_KEY;
import static com.psys.hobb.common.sys.util.constant.UiPathConstants.*;

@Service
public class SecResourceTouristServiceImpl implements SecResourceTouristService {

    @Autowired
    private SecResourceTouristFeign secResourceTouristFeign;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, SecResourceParam> secResourceTouristTemplate;

    @Override
    public SecResourceParam getTouristMenuByCache() {
        //对象序列化和反序列化存储对象
        SecResourceParam touristMenu = secResourceTouristTemplate.opsForValue().get(SEC_TOURIST_MENU_KEY);

        if(null == touristMenu){
            JSONObject touristMenuJsonResp =  secResourceTouristFeign.getTouristMenu();
            AssertUtil.notNull(touristMenuJsonResp, "获取游客菜单失败,"+ CALL_SERVICE_NO_RESPONSE_MSG);
            JSONObject touristMenuJson = new JSONObject((Map<String, Object>)touristMenuJsonResp.get("result"));
            AssertUtil.notNull(touristMenuJsonResp, "获取游客菜单失败,"+ CALL_SERVICE_RESPONSE_EMPTY_DATE_MSG);
            touristMenu = touristMenuJson.toJavaObject(SecResourceParam.class);
            secResourceTouristTemplate.opsForValue().set(SEC_TOURIST_MENU_KEY, touristMenu, SysRedisConstants.LOGIN_USER_EXPIRATION, SysRedisConstants.LOGIN_USER_EXPIRATION_TIMEUNIT);
        } else{
            secResourceTouristTemplate.expire(SEC_TOURIST_MENU_KEY, SysRedisConstants.LOGIN_USER_EXPIRATION, SysRedisConstants.LOGIN_USER_EXPIRATION_TIMEUNIT);
        }
        return touristMenu;
    }

    /**
     * JSON流存储对象备份
     * @return
     * @author SHJ
     * @since 2018/7/14 23:28
     */
    public SecResourceParam getTouristMenuByCache_BakJson() {
        //JSON流存储对象，1.58KB,对象流存储字节为1,41KB，对象流占内存少些
        String value = stringRedisTemplate.opsForValue().get(SEC_TOURIST_MENU_KEY);

        if(null == value){
            JSONObject touristMenuJsonResp =  secResourceTouristFeign.getTouristMenu();
            AssertUtil.notNull(touristMenuJsonResp, "获取游客菜单失败,"+ CALL_SERVICE_NO_RESPONSE_MSG);
            JSONObject touristMenuJson = new JSONObject((Map<String, Object>)touristMenuJsonResp.get("result"));
            AssertUtil.notNull(touristMenuJsonResp, "获取游客菜单失败,"+ CALL_SERVICE_RESPONSE_EMPTY_DATE_MSG);
            value = touristMenuJson.toJSONString();
            stringRedisTemplate.opsForValue().set(SEC_TOURIST_MENU_KEY, value, SysRedisConstants.LOGIN_USER_EXPIRATION, SysRedisConstants.LOGIN_USER_EXPIRATION_TIMEUNIT);
        } else{
            secResourceTouristTemplate.expire(SEC_TOURIST_MENU_KEY, SysRedisConstants.LOGIN_USER_EXPIRATION, SysRedisConstants.LOGIN_USER_EXPIRATION_TIMEUNIT);
        }
        return JSON.parseObject(value).toJavaObject(SecResourceParam.class);
    }

}
