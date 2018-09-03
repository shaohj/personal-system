package com.psys.hobb.auth.feign.hystric;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.auth.feign.SecUserFeign;
import org.springframework.stereotype.Component;

@Component
public class SecUserFeignHystric implements SecUserFeign {

    @Override
    public JSONObject getByUserName(String userName) {
        return null;
    }

    @Override
    public JSONObject getByUserNameAndPassword(String userName, String password) {
        return null;
    }

}
