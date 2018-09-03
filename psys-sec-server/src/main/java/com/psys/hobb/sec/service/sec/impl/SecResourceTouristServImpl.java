package com.psys.hobb.sec.service.sec.impl;

import com.psys.hobb.common.sys.util.AssertUtil;
import com.psys.hobb.sec.dao.sec.*;
import com.psys.hobb.sec.model.sec.SecResourceTourist;
import com.psys.hobb.sec.service.sec.SecResourceTouristServ;
import com.psys.hobb.sec.service.sec.SnServ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.*;

@Service
public class SecResourceTouristServImpl implements SecResourceTouristServ {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SecResourceTouristServImpl.class);

	@Autowired
	private SecResourceTouristRepo secResourceTouristRepo;
	
	private @Autowired SnServ snServ;
	
	@Override
	@Transactional
	public SecResourceTourist saveOrUpdateResource(SecResourceTourist secResourceTourist) {
		Integer resourceId = secResourceTourist.getResourceId();
		
		if(null == resourceId || 0 == resourceId){
			secResourceTourist.setResourceId(null);
			secResourceTourist.setResourceCode(snServ.generateCode(TOURIST_RESOURCE_SN_TYPE, true));
		}
		secResourceTourist.setEnabledFlag(ENABLED_FLAG);
		secResourceTouristRepo.save(secResourceTourist);
		
		return secResourceTourist;
	}

	@Override
	@Transactional
	public boolean deleteByCode(String resourceCode) {
		SecResourceTourist resource = secResourceTouristRepo.findByResourceCode(resourceCode);
		AssertUtil.notNull(resource, RESOURCE_HAS_DELETE_EXCEPTION_MSG);
		AssertUtil.isTrue(!SYSTEM_RESOURCE_YES.equalsIgnoreCase(resource.getIsSysRes()), DELETE_SYSTEM_RESOURCE_MSG);
		//DELETE_SYSTEM_RESOURCE_MSG
		/* 校验数据是否能被删除 */
		int countChild = secResourceTouristRepo.countByParentId(resource.getResourceCode());
		AssertUtil.isTrue(countChild == 0, HAS_CHILD_RESOURCE_EXCEPTION_MSG);

		/* 删除操作 */
		return secResourceTouristRepo.delById(resource.getResourceId()) > 0;
	}
	
}
