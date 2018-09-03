package com.psys.hobb.sec.service.sec;

import com.psys.hobb.sec.model.sec.SecResource;

import java.util.List;

/**
 * 资源Service
 * 编  号：<br/>
 * 名  称：SecResourceServ<br/>
 * 描  述：<br/>
 * 完成日期：2017年11月25日 下午8:09:59<br/>
 * 编码作者：shaohj<br/>
 */
public interface SecResourceServ {
	
	/**
	 * 新增或修改资源
	 * 新增：新增资源，新增资源查询option，新增资源允许权限
	 * 修改：修改资源,修改资源允许权限
	 * @Title: saveOrUpdateResource 
	 * @param secResource
	 * @return
	 */
	public SecResource saveOrUpdateResource(SecResource secResource);
	
	/**
	 * 删除资源.
	 *  若资源没有子资源且资源未被角色使用,则不能删除该资源
	 *  3个步骤：删除权限，删除操作，删除资源
	 * @Title: deleteByCode 
	 * @param resourceCode
	 * @return
	 */
	public boolean deleteByCode(String resourceCode);

	/**
	 * 获取用户的资源列表,可根据资源的parentId过滤数据
	 *   若用户是管理员用户,则查询所有资源数据;若用户为非管理员用户,则查询用户的资源数据
	 * @Title: findListByUserIdAndParentId 
	 * @param userId
	 * @param parentId 该值为null时,不使用此条件过滤数据
	 * @return
	 */
	public List<SecResource> findListByUserIdAndParentId(Integer userId, String parentId);

	
	
}
