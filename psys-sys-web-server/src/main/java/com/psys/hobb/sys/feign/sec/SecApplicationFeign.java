package com.psys.hobb.sys.feign.sec;

import com.psys.hobb.sys.config.oauth2.OAuth2FeignConguration;
import com.psys.hobb.sys.feign.sec.hystric.SecResourceFeignHystric;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "SEC-SERVER", fallback = SecResourceFeignHystric.class
        ,configuration = {OAuth2FeignConguration.class}
        )
public interface SecApplicationFeign {

    @RequestMapping(value = "/sec-server/sys/removeUserRedisCache",method = RequestMethod.GET)
    public void removeUserRedisCache();

}
