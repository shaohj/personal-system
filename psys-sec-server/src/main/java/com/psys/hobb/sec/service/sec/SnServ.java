package com.psys.hobb.sec.service.sec;

/**
 * 序列号接口
 * 编  号：<br/>
 * 名  称：SnServ<br/>
 * 描  述：<br/>
 * 完成日期：2017年11月22日 下午9:50:54<br/>
 * 编码作者：shaohj<br/>
 */
public interface SnServ {

	/**
	 * 生成code
	 * @Title: generateCode 
	 * @param type 序列号type
	 * @param isFulshSn 序号是否增长.true:是;false:否
	 * @return
	 */
	public String generateCode(String type, boolean isFulshSn) ;

}
