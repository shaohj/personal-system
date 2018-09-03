package com.psys.hobb.sec.service.sec.impl;


import static com.psys.hobb.common.sys.util.constant.UiPathConstants.*;

import java.util.List;

import com.psys.hobb.common.sys.util.AssertUtil;
import com.psys.hobb.common.tree.util.TreeUtil;
import com.psys.hobb.sec.dao.sec.TbCodeRepo;
import com.psys.hobb.sec.model.sec.TbCode;
import com.psys.hobb.sec.service.sec.TbCodeServ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 数据字典缓存service
 * 编  号：<br/>
 * 名  称：CodeCacheUtilService<br/>
 * 描  述：<br/>
 * 完成日期：2017年12月23日 下午10:19:28<br/>
 * 编码作者：shaohj<br/>
 */
@Service
public class CodeCacheService {

	private static final Logger logger = LoggerFactory.getLogger(CodeCacheService.class);
	
	private @Autowired TbCodeRepo codeRepo;
	
	private @Autowired TbCodeServ codeServ;
	
	/** 存储数据字典的根节点 */
	private static TbCode rootCode;
	
	public void refreshRootCode(){
		/** 1.重新加载并缓存数据字典 */
		TbCode tempRootCode = codeServ.getRootCode();
		
		List<TbCode> allCodeList = codeRepo.procShowCodeChildLst(tempRootCode.getCode());
		
		TbCode codeTree = TreeUtil.buildByRecursiveByHasRootTree(allCodeList,
				treeNode -> ROOT_CODE_PARENT_CODE.equals(treeNode.getTreeParentId()));
		
		rootCode = codeTree;
	}
	
	public static List<TbCode> getCodesByCodeType(String codeType) {
		AssertUtil.notNull(rootCode, "数据字典缓存根节点为空，请联系管理员检查数据");
		AssertUtil.notEmptyStr(codeType, "codeType不能为空");
		
		return recursiveFindByCodeType(rootCode, codeType);
	}
	
	/**
	 * 递归从根节点寻找数据
	 * @Title: recursiveFindCode 
	 * @param tbCode
	 * @param codeType
	 * @return
	 */
	private static List<TbCode> recursiveFindByCodeType(final TbCode tbCode, final String codeType){
		if(null == tbCode || null == tbCode.getChildren()){
			return null;
		}
		
		if(codeType.equals(tbCode.getCodeType())){
			return tbCode.getChildren();
		} else {
			for(TbCode tempTbCode : tbCode.getChildren()){
				List<TbCode> findChildren = recursiveFindByCodeType(tempTbCode, codeType);
				if(null != findChildren){
					return findChildren;
				}
			}
		}
		return null;
	}
	
	/**
	 * 递归从根节点打印节点数据
	 * @Title: recursiveFindCode 
	 * @param tbCode
	 * @return
	 */
	public static void printData(final TbCode tbCode){
		if(null == tbCode){
			return;
		}
		logger.debug("\n\t tbCode=" + tbCode.getCode() + "," + tbCode.getParentId() + "," + tbCode.getCodeType() + "," + tbCode.getName());
		
		if(CollectionUtils.isEmpty(tbCode.getChildren())){
			return;
		}
		for(TbCode tempTbCode : tbCode.getChildren()){
			printData(tempTbCode);
		}
	}

	public static TbCode getRootCode() {
		return rootCode;
	}

}
