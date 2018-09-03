package com.psys.hobb.sys.feign.sec.hystric;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.sys.feign.sec.SecResourceFeign;
import org.springframework.stereotype.Component;

@Component
public class SecResourceFeignHystric implements SecResourceFeign {

    @Override
    public JSONObject getLoginUserMenu() {
        return null;
    }

}
