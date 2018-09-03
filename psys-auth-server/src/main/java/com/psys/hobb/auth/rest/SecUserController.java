package com.psys.hobb.auth.rest;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.auth.feign.SecUserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class SecUserController {

    @Autowired
    private SecUserFeign secUserFeign;

    @Autowired
    private TokenStore tokenStore;

    @RequestMapping(value = "/oauth/revokeToken", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void logout(String tokenValue) {
        if (!StringUtils.isEmpty(tokenValue)) {
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            if(null != accessToken){
                tokenStore.removeAccessToken(accessToken);
            }
        }
    }

    /**  start 代码是测试时使用的,实际项目没有使用 */
    @RequestMapping(value = "/query/getByUserName",method = RequestMethod.GET)
    public JSONObject getByUserName(@RequestParam String userName){
        return secUserFeign.getByUserName(userName);
    }

    @RequestMapping(value = "/query/getByUserNameAndPassword",method = RequestMethod.GET)
    public JSONObject getByUserNameAndPassword(@RequestParam String userName, @RequestParam String password){
        return secUserFeign.getByUserNameAndPassword(userName, password);
    }

    @RequestMapping(value = "/query/getUserInfoByToken",method = RequestMethod.GET)
    public OAuth2AccessToken getUserInfoByToken(String tokenValue){
        if (!StringUtils.isEmpty(tokenValue)) {
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            return accessToken;
        }
        return null;
    }

    /**  end 代码是测试时使用的,实际项目没有使用 */

}
