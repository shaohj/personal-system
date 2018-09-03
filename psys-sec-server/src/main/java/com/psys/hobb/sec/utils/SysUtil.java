package com.psys.hobb.sec.utils;

import java.util.List;

import com.psys.hobb.common.sys.util.AssertUtil;
import com.psys.hobb.sec.model.sec.SecResource;
import org.springframework.util.CollectionUtils;

public class SysUtil {

	public static SecResource getResourceByResourceCode(SecResource rootResource, String resourceCode) {
		AssertUtil.notNull(rootResource, "资源为空，请联系管理员检查数据");
		AssertUtil.notEmptyStr(resourceCode, "resourceCode不能为空");
		
		return recursiveFindResByResourceCode(rootResource, resourceCode);
	}
	
	public static List<SecResource> getResourceChildsByParentId(SecResource rootResource, String parentId) {
		AssertUtil.notNull(rootResource, "资源为空，请联系管理员检查数据");
		AssertUtil.notEmptyStr(parentId, "parentId不能为空");
		
		return recursiveFindResChildsByParentId(rootResource, parentId);
	}
	
	/**
	 * 递归从根节点寻找数据
	 * @Title: recursiveFindCode 
	 * @param rootResource
	 * @param resourceCode
	 * @return
	 */
	private static SecResource recursiveFindResByResourceCode(final SecResource rootResource, final String resourceCode){
		if(null == rootResource){
			return null;
		}
		
		if(resourceCode.equals(rootResource.getResourceCode())){
			return rootResource;
		} else {
			if(!CollectionUtils.isEmpty(rootResource.getChildren())){
				for(SecResource tempRes : rootResource.getChildren()){
					SecResource findResource = recursiveFindResByResourceCode(tempRes, resourceCode);
					if(null != findResource){
						return findResource;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 递归从根节点寻找数据
	 * @Title: recursiveFindCode 
	 * @param rootResource
	 * @param parentId
	 * @return
	 */
	private static List<SecResource> recursiveFindResChildsByParentId(final SecResource rootResource, final String parentId){
		if(null == rootResource || null == rootResource.getChildren()){
			return null;
		}
		
		if(parentId.equals(rootResource.getResourceCode())){
			return rootResource.getChildren();
		} else {
			for(SecResource tempRes : rootResource.getChildren()){
				List<SecResource> findChildren = recursiveFindResChildsByParentId(tempRes, parentId);
				if(null != findChildren){
					return findChildren;
				}
			}
		}
		return null;
	}
	
}
