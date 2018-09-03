package com.psys.hobb.sec.service.sec.impl;

import java.util.List;

import com.psys.hobb.common.sys.util.AssertUtil;
import com.psys.hobb.common.sys.util.constant.UiPathConstants;
import com.psys.hobb.sec.dao.sec.TbCodeRepo;
import com.psys.hobb.sec.model.sec.TbCode;
import com.psys.hobb.sec.service.sec.TbCodeServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TbCodeServImpl implements TbCodeServ {

	private @Autowired
	TbCodeRepo tbCodeRepo;
	
	@Override
	public List<TbCode> getChildList(String code) {
		return tbCodeRepo.findListByParentId(code);
	}

	@Override
	public TbCode getRootCode() {
		List<TbCode> rootList = tbCodeRepo.findListByParentId(UiPathConstants.ROOT_CODE_PARENT_CODE);
		AssertUtil.notEmpty(rootList, "code根节点未配置或未配置正确,请联系管理员配置！");
		return rootList.stream().findFirst().get(); //已做验证,数据一定不为null
	}

}
