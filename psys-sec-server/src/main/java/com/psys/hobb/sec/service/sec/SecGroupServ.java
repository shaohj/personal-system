package com.psys.hobb.sec.service.sec;

import com.psys.hobb.sec.model.sec.SecGroup;

import java.util.List;

/**
 * 组织接口
 * 编  号：<br/>
 * 名  称：SecGroupServ<br/>
 * 描  述：<br/>
 * 完成日期：2017年12月17日 下午3:26:26<br/>
 * 编码作者：shaohj<br/>
 */
public interface SecGroupServ {

	/**
	 * 删除组织.
	 *  若资源没有子组织且组织未被用户使用,则不能删除该资源
	 *  删除组织
	 * @Title: deleteByCode 
	 * @param code
	 * @return
	 */
	public boolean deleteByCode(String code);
	
	public List<SecGroup> getChildList(String code) ;
	
	/**
	 * 获取根节点
	 * @Title: getRootCode 
	 * @return
	 */
	public SecGroup getRootCode();

	public String getFullGroupName(SecGroup groupTree, String groupCode);

}
