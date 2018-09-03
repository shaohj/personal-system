package com.psys.hobb.sys.feign.sec;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.sys.config.oauth2.OAuth2FeignConguration;
import com.psys.hobb.sys.feign.sec.hystric.SecRoleFeignHystric;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "SEC-SERVER", fallback = SecRoleFeignHystric.class
        ,configuration = {OAuth2FeignConguration.class}
        )
public interface SecRoleFeign {

    @RequestMapping(value = "/sec-server/sys/role/query/pageByName",method = RequestMethod.GET)
    public JSONObject pageByName(@RequestParam("roleName") String roleName, @RequestParam("page") int page, @RequestParam("size") int size);

}
