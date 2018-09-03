package com.psys.hobb.sec.service.sec;

import java.util.List;

import com.psys.hobb.sec.model.sec.TbCode;

/**
 * 序列号接口
 * 编  号：<br/>
 * 名  称：SnServ<br/>
 * 描  述：<br/>
 * 完成日期：2017年11月22日 下午9:50:54<br/>
 * 编码作者：shaohj<br/>
 */
public interface TbCodeServ {

	public List<TbCode> getChildList(String code) ;
	
	/**
	 * 获取根节点
	 * @Title: getRootCode 
	 * @return
	 */
	public TbCode getRootCode();

}
