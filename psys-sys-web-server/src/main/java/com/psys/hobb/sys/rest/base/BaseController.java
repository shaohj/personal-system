package com.psys.hobb.sys.rest.base;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.common.sso.bean.SsoUser;
import com.psys.hobb.common.sys.model.SecResourceParam;
import com.psys.hobb.common.sys.util.AssertUtil;
import com.psys.hobb.sys.service.sec.SecResourceService;
import com.psys.hobb.sys.service.sec.SecResourceTouristService;
import com.psys.hobb.sys.service.sec.SecUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import java.util.Map;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.USER_NOT_LOGIN_EXCEPTION_MSG;

@Controller
public class BaseController {

    @Autowired
    protected SecUserService secUserService;

    @Autowired
    protected SecResourceService secResourceService;

    @Autowired
    protected SecResourceTouristService secResourceTouristService;

    /**
     * 设置登录用户信息
     * @param modeMap :
     * @return
     * @author SHJ
     * @since 2018/6/23 22:25
     */
    protected void setUserLoginInfo(ModelMap modeMap){
        //校验用户是否登录
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AssertUtil.notNull(auth, USER_NOT_LOGIN_EXCEPTION_MSG);

        //查询游客菜单
        SecResourceParam touristResource = secResourceTouristService.getTouristMenuByCache();
        modeMap.put("touristResource", touristResource);

        if(auth.getDetails() instanceof WebAuthenticationDetails){
            SsoUser tourist = new SsoUser();
            tourist.setUserName("游客");
            modeMap.put("user", tourist);
        } else{
            OAuth2AuthenticationDetails detail = (OAuth2AuthenticationDetails)auth.getDetails();
            AssertUtil.notNull(auth, USER_NOT_LOGIN_EXCEPTION_MSG);
            String tokenValue = detail.getTokenValue();
            modeMap.put("tokenValue", tokenValue);
            //这是用户信息
            SsoUser ssoUser = secUserService.getUserByCache(tokenValue);
            SecResourceParam userResource = secResourceService.getUserMenuByCache(ssoUser.getUserId());
            modeMap.put("user", ssoUser);
            modeMap.put("userResource", userResource);
        }
    }

}
