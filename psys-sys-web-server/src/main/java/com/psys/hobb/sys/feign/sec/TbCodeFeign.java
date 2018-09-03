package com.psys.hobb.sys.feign.sec;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.sys.config.oauth2.OAuth2FeignConguration;
import com.psys.hobb.sys.feign.sec.hystric.TbCodeFeignHystric;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "SEC-SERVER", fallback = TbCodeFeignHystric.class
        ,configuration = {OAuth2FeignConguration.class}
        )
public interface TbCodeFeign {

    @RequestMapping(value = "/sec-server/sys/code/query/cache/getCodesByCodeType",method = RequestMethod.GET)
    public JSONObject getCodesByCodeType(@RequestParam("codeType") String codeType);

}
