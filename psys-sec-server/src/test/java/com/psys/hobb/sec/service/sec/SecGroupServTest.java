package com.psys.hobb.sec.service.sec;

import java.util.List;
import java.util.Optional;

import com.alibaba.fastjson.JSON;
import com.psys.hobb.common.tree.util.TreeUtil;
import com.psys.hobb.sec.common.BaseApplicationTest;
import com.psys.hobb.sec.dao.sec.SecGroupRepo;
import com.psys.hobb.sec.model.sec.SecGroup;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.ROOT_GROUP_PARENT_CODE;

public class SecGroupServTest extends BaseApplicationTest {

	private static final Logger logger = LoggerFactory.getLogger(SecGroupServTest.class);
	
	private @Autowired SecGroupServ secGroupServ;

	private @Autowired SecGroupRepo secGroupRepo;

	@Test
	public void getChildListTest(){
		List<SecGroup> childList = secGroupServ.getChildList("G-20170101-001");
		
		Assert.assertNotNull(childList);
		
		logger.info("childList...");
		childList.stream().forEach(child -> logger.info(child.getName()));
	}

	@Test
	public void findByIdTest(){
		int id = 1;
		Optional<SecGroup> optional = secGroupRepo.findById(id);

		optional.ifPresent(secGroup -> logger.info("secGroup={}", secGroup.getName()));
		if(!optional.isPresent()){
			logger.info("id为{}的数据不存在={}", id);
		}
	}

	@Test
	public void getFullGroupNameTest(){
		List<SecGroup> allGroupList = secGroupRepo.findAllByEnabled();

		SecGroup groupTree = TreeUtil.buildByRecursiveByHasRootTree(allGroupList,
				treeNode -> ROOT_GROUP_PARENT_CODE.equals(treeNode.getTreeParentId()));

		String groupCode = "G-20180715-001";
		String fullName = secGroupServ.getFullGroupName(groupTree, groupCode);

		logger.info("groupCode{}的数据fullName={}", groupCode, fullName);
	}

}
