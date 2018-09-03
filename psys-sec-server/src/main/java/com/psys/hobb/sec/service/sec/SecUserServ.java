package com.psys.hobb.sec.service.sec;

import com.psys.hobb.common.sso.bean.SsoUser;
import com.psys.hobb.sec.model.sec.SecUser;

/**
 * 用户Service接口
 * 编  号：<br/>
 * 名  称：SecUserServ<br/>
 * 描  述：<br/>
 * 完成日期：2017年12月23日 下午11:29:32<br/>
 * 编码作者：shaohj<br/>
 */
public interface SecUserServ {

	/**
	 * 新增或修改用户
	 * @Title: saveOrUpdateUser 
	 * @param user
	 * @return
	 */
	public SecUser saveOrUpdateUser(SecUser user);
	
	/**
	 * 新增或修改用户角色
	 * @Title: saveOrUpdateUserRole 
	 * @param user
	 * @return
	 */
	public SecUser saveOrUpdateUserRole(SecUser user);

	/**
	 * 根据tokenValue查询当前登录用户信息,使用了redis缓存,审计更新修改人查看当前用户要用
	 * @param tokenValue
	 * @return
	 */
	public SsoUser getSsoUser(String tokenValue);

	/**
	 * 验证token是否有效,若有效再根据tokenValue查询当前登录用户信息
	 * @param tokenValue
	 * @return
	 */
	public SsoUser getSsoUserAndValidToken(String tokenValue);
	
}
