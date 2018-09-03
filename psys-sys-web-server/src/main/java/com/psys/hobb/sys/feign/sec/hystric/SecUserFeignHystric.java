package com.psys.hobb.sys.feign.sec.hystric;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.sys.feign.sec.SecUserFeign;
import org.springframework.stereotype.Component;

@Component
public class SecUserFeignHystric implements SecUserFeign {

    @Override
    public JSONObject pageBySearch(String userName, String status, String groupCode, int page, int size) {
        return null;
    }

    @Override
    public JSONObject getUserRoles(Integer userId) {
        return null;
    }

    @Override
    public JSONObject getLoginUser() {
        return null;
    }

}
