package com.psys.hobb.sec.config;

import com.psys.hobb.common.sso.bean.SsoUser;
import com.psys.hobb.sec.service.sec.SecUserServ;
import com.psys.hobb.sec.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class UserIDAuditorBean implements AuditorAware<String> {

	private @Autowired SecUserServ secUserServ;

	@Override
	public Optional<String> getCurrentAuditor() {
		String tokenValue = SecurityUtils.getTokenWithValid();
		SsoUser ssoUser = secUserServ.getSsoUser(tokenValue);
		return null == ssoUser ? Optional.of("test") : Optional.of(ssoUser.getUserName());
	}
	
}
