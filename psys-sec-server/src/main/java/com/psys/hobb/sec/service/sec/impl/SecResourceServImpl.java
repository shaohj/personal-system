package com.psys.hobb.sec.service.sec.impl;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.*;

import java.util.List;

import com.psys.hobb.common.sys.util.AssertUtil;
import com.psys.hobb.sec.dao.sec.SecOperationRepo;
import com.psys.hobb.sec.dao.sec.SecPermissionRepo;
import com.psys.hobb.sec.dao.sec.SecResourceRepo;
import com.psys.hobb.sec.dao.sec.SecUserRepo;
import com.psys.hobb.sec.model.sec.SecOperation;
import com.psys.hobb.sec.model.sec.SecPermission;
import com.psys.hobb.sec.model.sec.SecResource;
import com.psys.hobb.sec.model.sec.SecUser;
import com.psys.hobb.sec.service.sec.SecResourceServ;
import com.psys.hobb.sec.service.sec.SnServ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class SecResourceServImpl implements SecResourceServ {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SecResourceServImpl.class);
	
	private @Autowired SecResourceRepo resourceRepo;
	
	private @Autowired SecOperationRepo operationRepo;
	
	private @Autowired SecPermissionRepo permissionRepo;
	
	private @Autowired SecUserRepo userRepo;
	
	private @Autowired SnServ snServ;
	
	@Override
	@Transactional
	public SecResource saveOrUpdateResource(SecResource secResource) {
		Integer resourceId = secResource.getResourceId();
		
		if(null == resourceId || 0 == resourceId){
			secResource.setResourceId(null);
			secResource.setResourceCode(snServ.generateCode(RESOURCE_SN_TYPE, true));
		}
		secResource.setEnabledFlag(ENABLED_FLAG);
		resourceRepo.save(secResource);
		
		if(StringUtils.isEmpty(resourceId)){
			//新增
			String operationCode = snServ.generateCode(OPERATION_SN_TYPE, true);
			String permissionCode = snServ.generateCode(PERMISSION_SN_TYPE, true);
			
			SecOperation secOperation = new SecOperation(null, operationCode, "查询", "", secResource);
			operationRepo.save(secOperation);
			
			SecPermission secPermission = new SecPermission(null, permissionCode, secResource.getName() + "权限", secResource.getUrl(),
					"", secOperation);
			permissionRepo.save(secPermission);
		} else {
			//修改
			//此处使用本地sql,因为使用了事务,所以需要将事务数据传参,否则数据将不会相等
			permissionRepo.updatePermissionAddress(resourceId, secResource.getName() + "权限", secResource.getUrl()); 
		}
		return secResource;
	}

	@Override
	@Transactional
	public boolean deleteByCode(String resourceCode) {
		SecResource resource = resourceRepo.findByResourceCode(resourceCode);
		AssertUtil.notNull(resource, RESOURCE_HAS_DELETE_EXCEPTION_MSG);
		AssertUtil.isTrue(!SYSTEM_RESOURCE_YES.equalsIgnoreCase(resource.getIsSysRes()), DELETE_SYSTEM_RESOURCE_MSG);
		//DELETE_SYSTEM_RESOURCE_MSG
		/* 校验数据是否能被删除 */
		int countChild = resourceRepo.countByParentId(resource.getResourceCode());
		AssertUtil.isTrue(countChild == 0, HAS_CHILD_RESOURCE_EXCEPTION_MSG);
		int useCount = resourceRepo.countByRoleUsing(resource.getResourceCode());
		AssertUtil.isTrue(useCount == 0, RESOURCE_USING_EXCEPTION_MSG);
		
		/* 删除资源的操作及操作的权限 */
		permissionRepo.deleteByResourceCode(resource.getResourceCode());
		operationRepo.deleteByResourceCode(resource.getResourceCode());
		
		/* 删除操作 */
		return resourceRepo.delById(resource.getResourceId()) > 0;
	}
	
	@Override
	public List<SecResource> findListByUserIdAndParentId(Integer userId, String parentId) {
		AssertUtil.notEmptyStr(userId, "userId参数不能为空！");
		
		if(StringUtils.isEmpty(parentId)){
			parentId = null; //null处理,便于使用ifnull函数
		}
		SecUser user = userRepo.findById(userId).get();
		AssertUtil.notNull(user, "parentId参数不能为空！");
		if(ADMIN_USER_YES.equals(user.getIsAdmin())){
			return resourceRepo.findListByParentId(parentId);
		} else {
			return resourceRepo.findListByUserIdAndParentId(userId, parentId);
		}
	}

}
