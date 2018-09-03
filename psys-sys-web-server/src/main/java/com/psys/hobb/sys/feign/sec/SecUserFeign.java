package com.psys.hobb.sys.feign.sec;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.sys.config.oauth2.OAuth2FeignConguration;
import com.psys.hobb.sys.feign.sec.hystric.SecUserFeignHystric;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "SEC-SERVER", fallback = SecUserFeignHystric.class
        ,configuration = {OAuth2FeignConguration.class}
        )
public interface SecUserFeign {

    @RequestMapping(value = "/sec-server/sys/user/query/pageBySearch",method = RequestMethod.GET)
    public JSONObject pageBySearch(@RequestParam("userName") String userName, @RequestParam("status") String status, @RequestParam("groupCode") String groupCode,
                                   @RequestParam("page") int page, @RequestParam("size") int size);

    @RequestMapping(value = "/sec-server/sys/user/query/getUserRoles",method = RequestMethod.GET)
    public JSONObject getUserRoles(@RequestParam("userId") Integer userId);

    @RequestMapping(value = "/sec-server/sys/user/query/getLoginUser",method = RequestMethod.GET)
    public JSONObject getLoginUser();

}
