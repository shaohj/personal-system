package com.psys.hobb.sec.service.sec;

import com.psys.hobb.sec.model.sec.SecRole;

/**
 * 角色Service接口
 * 编  号：<br/>
 * 名  称：SecRoleServ<br/>
 * 描  述：<br/>
 * 完成日期：2017年12月17日 下午9:17:54<br/>
 * 编码作者：shaohj<br/>
 */
public interface SecRoleServ {

	/**
	 * 新增或修改角色
	 * @Title: saveOrUpdateResource 
	 * @param role
	 * @return
	 */
	public SecRole saveOrUpdateRole(SecRole role);
	
	/**
	 * 删除角色
	 * @Title: deleteByCode 
	 * @param roleCode
	 * @return
	 */
	public boolean deleteByCode(String roleCode);
	
}
