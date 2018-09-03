package com.psys.hobb.sec.service.sec.impl;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.*;

import java.util.Arrays;
import java.util.List;

import com.psys.hobb.common.sys.util.AssertUtil;
import com.psys.hobb.sec.dao.sec.SecOperationRepo;
import com.psys.hobb.sec.dao.sec.SecRoleRepo;
import com.psys.hobb.sec.model.sec.SecOperation;
import com.psys.hobb.sec.model.sec.SecRole;
import com.psys.hobb.sec.service.sec.SecRoleServ;
import com.psys.hobb.sec.service.sec.SnServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class SecRoleServImpl implements SecRoleServ {

	private @Autowired
	SecRoleRepo secRoleRepo;
	
	private @Autowired
	SecOperationRepo secOperationRepo;
	
	private @Autowired
	SnServ snServ;

	@Override
	@Transactional
	public SecRole saveOrUpdateRole(SecRole role) {
		if(null == role.getRoleId()){
			role.setRoleId(null);
			role.setRoleCode(snServ.generateCode(ROLE_SN_TYPE, true));
		}
		//注意用持久化的dbRole,如果用role,则新增时role是持久化的,修改时role是非持久化的,导致新增和修改时操作权限保存不上,不知道啥原因
		role.setEnabledFlag(ENABLED_FLAG);
		SecRole dbRole = secRoleRepo.save(role); 
		
		if(!StringUtils.isEmpty(role.getOperationIds())){
			List<String> operationCodes = Arrays.asList(role.getOperationIds().split(","));
			List<SecOperation> secOperations = secOperationRepo.findByOperationCodes(operationCodes);
			dbRole.setSecOperations(secOperations); //级联修改角色的资源操作权限(多对多中间表)和角色
		} else {
			dbRole.setSecOperations(null); //级联删除角色的资源操作权限(多对多中间表)和角色
		}
		
		return dbRole;
	}

	@Override
	@Transactional
	public boolean deleteByCode(String roleCode) {
		AssertUtil.notEmptyStr(roleCode, "roleCode不能为空");
		
		/** 1.验证角色是否被使用 */
		int roleUseCount = secRoleRepo.countRoleByUserUsing(roleCode);
		AssertUtil.isTrue(roleUseCount == 0, roleCode + "的角色已配置了用户，不能被删除！");
		
		/** 2.删除角色的资源操作权限和角色 */
		SecRole secRole = secRoleRepo.findByRoleCode(roleCode);
		secRoleRepo.delete(secRole); //级联删除角色的资源操作权限(多对多中间表)和角色
		return true;
	}

}
