package com.psys.hobb.sec.service.sec;

import com.psys.hobb.sec.model.sec.SecResourceTourist;

import java.util.List;

/**
 * 游客资源Service
 * 编  号：
 * 名  称：SecResourceTouristServ
 * 描  述：
 * 完成日期：2018/7/14 12:04
 * 编码作者：SHJ
 */
public interface SecResourceTouristServ {
	
	/**
	 * 新增或修改资源
	 * 新增：新增资源，新增资源查询option，新增资源允许权限
	 * 修改：修改资源,修改资源允许权限
	 * @Title: saveOrUpdateResource 
	 * @param secResourceTourist
	 * @return
	 */
	public SecResourceTourist saveOrUpdateResource(SecResourceTourist secResourceTourist);
	
	/**
	 * 删除资源.
	 *  若资源有子资源,则不能删除该资源
	 * @Title: deleteByCode
	 * @param resourceCode
	 * @return
	 */
	public boolean deleteByCode(String resourceCode);

}
