package com.psys.hobb.sys.rest;

import com.psys.hobb.common.http.util.RestUtils;
import com.psys.hobb.sys.feign.sec.SecApplicationFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SecApplicationFeign secApplicationFeign;

	private @Value("${shaohj.auth-service}") String authService;

	@RequestMapping(value = "/revokeToken", method = RequestMethod.GET)
	public String revokeToken(HttpServletRequest request){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		OAuth2AuthenticationDetails detail = (OAuth2AuthenticationDetails)auth.getDetails();

		//移除用户管理系统redis缓存
		secApplicationFeign.removeUserRedisCache();

		if(null != detail){
			//access_token移除
			String tokenValue = detail.getTokenValue();
			String url = authService + "/user/oauth/revokeToken?tokenValue="+tokenValue;
			RestUtils.sendBaseGetJsonRequest(url, Void.class);
		}
		//session失效
		request.getSession().invalidate();
		//重定向：单点登录退出
		return "redirect:" + authService + "/loginout";
	}

}
