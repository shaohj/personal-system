package com.psys.hobb.sys.feign.sec.hystric;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.sys.feign.sec.SecRoleFeign;
import org.springframework.stereotype.Component;

@Component
public class SecRoleFeignHystric implements SecRoleFeign {

    @Override
    public JSONObject pageByName(String roleName, int page, int size) {
        return null;
    }
}
