package com.psys.hobb.auth.feign;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.auth.feign.hystric.SecUserFeignHystric;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "SEC-SERVER", fallback = SecUserFeignHystric.class)
public interface SecUserFeign {

    @RequestMapping(value = "/sec-server/sys/user/query/getByUserName",method = RequestMethod.GET)
    JSONObject getByUserName(@RequestParam(value = "userName") String userName);

    @RequestMapping(value = "/sec-server/sys/user/query/getByUserNameAndPassword",method = RequestMethod.GET)
    JSONObject getByUserNameAndPassword(@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password);

}
