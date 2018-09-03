package com.psys.hobb.auth.feign.hystric;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.auth.feign.TbCodeFeign;
import org.springframework.stereotype.Component;

@Component
public class TbCodeFeignHystric implements TbCodeFeign {

    @Override
    public JSONObject getByCodeType(String codeType) {
        return null;
    }

}
