package com.psys.hobb.sys.feign.sec.hystric;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.sys.feign.sec.TbCodeFeign;
import org.springframework.stereotype.Component;

@Component
public class TbCodeFeignHystric implements TbCodeFeign {

    @Override
    public JSONObject getCodesByCodeType(String codeType) {
        return null;
    }

}
