package com.psys.hobb.auth.config.security;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.auth.feign.SecUserFeign;
import com.psys.hobb.common.http.constants.HttpConstants;
import com.psys.hobb.common.http.util.CallRemoteServiceUtils;
import com.psys.hobb.common.sso.bean.SsoUser;
import com.psys.hobb.common.sys.util.constant.SysRedisConstants;
import com.psys.hobb.common.sys.util.constant.UiPathConstants;
import com.psys.hobb.redis.service.IRedisCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.CALL_SERVICE_NO_RESPONSE_MSG;

@Component
public class SecAuthenticationProvider implements AuthenticationProvider{

    @Autowired
	private SecUserFeign secUserFeign;

    @Autowired
	private IRedisCommonService redisCommonService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) authentication.getDetails();

        if(null == details || null == details.getValidateCode()){
        	throw new BadCredentialsException("请输入验证码！");
        }

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String sessionId  = request.getSession().getId();

		//比较验证码
		String validRedisKey = SysRedisConstants.AUTH_VERCODE_KEY + sessionId;
		String validValue = redisCommonService.getString(validRedisKey);
		redisCommonService.delete(validRedisKey); //比较之后移除redis验证码
		if(null == validValue || !validValue.equalsIgnoreCase(details.getValidateCode())){
			throw new BadCredentialsException("验证码错误！");
		}

		/** 使用自定义的工具类,实现若远程服务调用不成功,则指定最大次数调用,直到成功为止 */
		CallRemoteServiceUtils callRSUtils = new CallRemoteServiceUtils(HttpConstants.REQUEST_SERVICE_TIMES_LIMIT);
		JSONObject userResponse = callRSUtils.apply(secUserFeign, (secUserFeign) -> secUserFeign.getByUserNameAndPassword(username, password));
		if(null == userResponse){
			throw new BadCredentialsException("校验用户信息失败," + CALL_SERVICE_NO_RESPONSE_MSG);
		}

		JSONObject user = new JSONObject((Map<String, Object>)userResponse.get("result"));
        if(user == null){
            throw new BadCredentialsException("用户名或密码错误！");
        }
		SsoUser ssoUser = JSONObject.toJavaObject(user, SsoUser.class);

		if(!UiPathConstants.CODE_USER_STATE_NORMAL.equals(ssoUser.getStatus())){
        	throw new BadCredentialsException("用户已被禁用！");
		}

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("system_user"));

        return new UsernamePasswordAuthenticationToken(ssoUser, password, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
