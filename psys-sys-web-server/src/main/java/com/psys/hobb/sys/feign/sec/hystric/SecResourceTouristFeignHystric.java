package com.psys.hobb.sys.feign.sec.hystric;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.sys.feign.sec.SecResourceTouristFeign;
import org.springframework.stereotype.Component;

@Component
public class SecResourceTouristFeignHystric implements SecResourceTouristFeign {

    @Override
    public JSONObject getTouristMenu() {
        return null;
    }

}
