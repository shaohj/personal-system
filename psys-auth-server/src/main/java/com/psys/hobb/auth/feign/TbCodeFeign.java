package com.psys.hobb.auth.feign;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.auth.feign.hystric.TbCodeFeignHystric;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "SEC-SERVER", fallback = TbCodeFeignHystric.class)
public interface TbCodeFeign {

    @RequestMapping(value = "sec-server/sys/code/query/getByCodeType/{codeType}",method = RequestMethod.GET)
    JSONObject getByCodeType(@PathVariable(value = "codeType") String codeType);

}
