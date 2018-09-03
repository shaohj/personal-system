package com.psys.hobb.sec.service.sec.impl;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.*;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.psys.hobb.common.sso.bean.SsoUser;
import com.psys.hobb.common.sys.util.AssertUtil;
import com.psys.hobb.common.sys.util.constant.SysRedisConstants;
import com.psys.hobb.sec.dao.auth.OauthAccessTokenRepo;
import com.psys.hobb.sec.dao.sec.SecRoleRepo;
import com.psys.hobb.sec.dao.sec.SecUserRepo;
import com.psys.hobb.sec.model.sec.SecRole;
import com.psys.hobb.sec.model.sec.SecUser;
import com.psys.hobb.sec.service.sec.SecUserServ;
import com.psys.hobb.sec.service.sec.SnServ;
import com.psys.hobb.sec.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecUserServImpl implements SecUserServ {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SecUserServImpl.class);

	private @Autowired SecUserRepo userRepo;
	
	private @Autowired SecRoleRepo roleRepo;
	
	private @Autowired SnServ snServ;

	private @Autowired OauthAccessTokenRepo oauthAccessTokenRepo;

	private @Autowired RedisTemplate<String, SsoUser> ssoUserRedisTemplate;

	@Override
	public SecUser saveOrUpdateUser(SecUser user) {
		AssertUtil.notNull(user.getSecGroup(), "用户组织不能为空");
		user.getSecGroup().setGroupId(0); //主键用的code,这里id没用,赋值便于保存数据
		if(null == user.getUserId()){
			user.setUserId(null);
			user.setUserCode(snServ.generateCode(USER_SN_TYPE, true));
			user.setStatus(CODE_USER_STATE_NORMAL);
		} else {
			SecUser dbUser = userRepo.findByUserCode(user.getUserCode());
			//修改时不需要更改用户的状态
			user.setStatus(dbUser.getStatus()); 
			
			//修改时,不需要更新用户的角色
			List<SecRole> secRoles = roleRepo.findRoleByUserCode(user.getUserCode());
			user.setSecRoles(secRoles);
		}
		user.setEnabledFlag(ENABLED_FLAG);
		userRepo.save(user);
		return user;
	}

	@Transactional
	@Override
	public SecUser saveOrUpdateUserRole(SecUser user) {
		AssertUtil.notNull(user.getUserId(), "用户id不能为空");
		
		SecUser dbUser = userRepo.findById(user.getUserId()).get();
		AssertUtil.notEmptyStr(user, "userId为" + user.getUserId() + "的用户不存在！");

		//使用级联更新
		List<SecRole> secRoles = roleRepo.findByRoleCodes(user.getUserRoleCodes());
		dbUser.setSecRoles(secRoles); //加了事务注解,持久化状态,会同步至数据库中
		
		return dbUser;
	}

	@Override
	public SsoUser getSsoUser(String tokenValue) {
		String ssoUserRedisKey = SysRedisConstants.SEC_LOGIN_USER_KEY + tokenValue;
		SsoUser user = ssoUserRedisTemplate.opsForValue().get(ssoUserRedisKey);
		if(null == user){
			user = getSsoUserAndValidToken(tokenValue);
		}
		return user;
	}

	@Override
	public SsoUser getSsoUserAndValidToken(String tokenValue) {
		//根据access_token查询到登录用户id,再查询用户信息。登录信息需要先实时查询token是否存在,再缓存到redis中,access_token为key,如果只需查询用户信息,可以不用检验token是否有效
		String tokenId = SecurityUtils.extractTokenKey(tokenValue);
		String userJsonStr = oauthAccessTokenRepo.findByTokenId(tokenId);
		AssertUtil.notNull(userJsonStr, USER_NOT_LOGIN_EXCEPTION_MSG);
		JSON userJson = JSON.parseObject(userJsonStr);
		SsoUser user = JSON.toJavaObject(userJson, SsoUser.class);

		String ssoUserRedisKey = SysRedisConstants.SEC_LOGIN_USER_KEY + tokenValue;
		ssoUserRedisTemplate.opsForValue().set(ssoUserRedisKey, user, SysRedisConstants.LOGIN_USER_EXPIRATION, SysRedisConstants.LOGIN_USER_EXPIRATION_TIMEUNIT);
		return user;
	}

}
