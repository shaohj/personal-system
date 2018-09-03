package com.psys.hobb.sec.rest;

import com.psys.hobb.common.sso.bean.SsoUser;
import com.psys.hobb.common.sys.util.constant.SysRedisConstants;
import com.psys.hobb.redis.service.IRedisCommonService;
import com.psys.hobb.sec.dao.sec.SecUserRepo;
import com.psys.hobb.sec.service.sec.SecUserServ;
import com.psys.hobb.sec.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.psys.hobb.common.sys.util.constant.SysRedisConstants.SEC_USER_MENU_KEY;

@Controller
@RequestMapping(value = "sys", produces="application/json;charset=UTF-8")
public class SecApplicationController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SecApplicationController.class);

	private @Autowired SecUserRepo userRepo;

	private @Autowired IRedisCommonService redisCommonService;

	private @Autowired SecUserServ secUserServ;

	@GetMapping("removeUserRedisCache")
	@ResponseBody
	public void removeUserRedisCache(){
		String tokenValue = SecurityUtils.getTokenWithValid(); //获取access_token
		SsoUser user = secUserServ.getSsoUser(tokenValue); //获取对应的用户信息
		String ssoUserRedisKey = SysRedisConstants.SEC_LOGIN_USER_KEY + tokenValue;
		String userResourceRedisKey = SEC_USER_MENU_KEY + user.getUserId();
		redisCommonService.delete(ssoUserRedisKey);
		redisCommonService.delete(userResourceRedisKey);
	}


	
}
