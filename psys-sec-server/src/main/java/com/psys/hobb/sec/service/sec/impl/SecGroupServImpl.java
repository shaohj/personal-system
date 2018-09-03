package com.psys.hobb.sec.service.sec.impl;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.psys.hobb.common.sys.util.AssertUtil;
import com.psys.hobb.sec.dao.sec.SecGroupRepo;
import com.psys.hobb.sec.dao.sec.SecUserRepo;
import com.psys.hobb.sec.model.sec.SecGroup;
import com.psys.hobb.sec.service.sec.SecGroupServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class SecGroupServImpl implements SecGroupServ {

	private @Autowired SecGroupRepo secGroupRepo;
	
	private @Autowired SecUserRepo secUserRepo;

	@Override
	@Transactional
	public boolean deleteByCode(String code) {
		SecGroup group = secGroupRepo.findByCode(code);
		AssertUtil.notNull(group, RESOURCE_HAS_DELETE_EXCEPTION_MSG);
		AssertUtil.isTrue(!SYSTEM_RESOURCE_YES.equalsIgnoreCase(group.getIsSysRes()), DELETE_SYSTEM_RESOURCE_MSG);
		
		/* 校验数据是否能被删除 */
		int countChild = secGroupRepo.countByParentId(code);
		AssertUtil.isTrue(countChild == 0, HAS_CHILD_RESOURCE_EXCEPTION_MSG);
		int useCount = secUserRepo.countByGroupCode(code);
		AssertUtil.isTrue(useCount == 0, RESOURCE_USING_EXCEPTION_MSG);
		
		/* 删除资源的操作及操作的权限 */
		return secGroupRepo.delById(group.getGroupId()) > 0;
	}
	
	@Override
	public List<SecGroup> getChildList(String code) {
		return secGroupRepo.findListByParentId(code);
	}

	@Override
	public SecGroup getRootCode() {
		List<SecGroup> rootList = secGroupRepo.findListByParentId(ROOT_GROUP_PARENT_CODE);
		AssertUtil.notEmpty(rootList, "group根节点未配置或未配置正确,请联系管理员配置！");
		return rootList.stream().findFirst().get(); //已做验证,数据一定不为null
	}

	@Override
	public String getFullGroupName(SecGroup groupTree, String groupCode) {
		if(null == groupTree || StringUtils.isEmpty(groupCode)){
			return "";
		}
		List<String> groupNames = new ArrayList<>();
		recursiveFindByCode(groupTree, groupCode, groupNames);
		Collections.reverse(groupNames);  //实现list集合逆序排列
		return groupNames.stream().collect(Collectors.joining("/"));
	}

	/**
	 * 递归从根节点寻找数据
	 * @param groupTree
	 * @param groupCode
	 * @param groupNames
	 * @return
	 */
	private static boolean recursiveFindByCode(final SecGroup groupTree, final String groupCode, final List<String> groupNames){
		if(groupCode.equalsIgnoreCase(groupTree.getGroupCode())){
			groupNames.add(groupTree.getName());
			return true;
		}

		if(CollectionUtils.isEmpty(groupTree.getChildren())){
			return false;
		}

		/** 如果groupCode对应的节点为该节点的子节点或孙子等节点,则设置为true */
		boolean ifFind = false;
		for(SecGroup tempSecGroup : groupTree.getChildren()){
			boolean flag = null == tempSecGroup? false : recursiveFindByCode(tempSecGroup, groupCode, groupNames);
			if(flag){
				ifFind = true;
				break;
			}
		}

		if(ifFind){
			groupNames.add(groupTree.getName());
		}
		return ifFind;
	}

}
