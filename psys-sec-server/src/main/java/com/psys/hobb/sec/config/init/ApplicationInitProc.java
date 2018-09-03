package com.psys.hobb.sec.config.init;

import java.util.List;

import com.psys.hobb.common.sys.util.constant.UiPathConstants;
import com.psys.hobb.sec.model.sec.TbCode;
import com.psys.hobb.sec.service.sec.impl.CodeCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

/**
 * spring boot启动成功后,初始化某些数据
 * 编  号：<br/>
 * 名  称：ApplicationInitProc<br/>
 * 描  述：<br/>
 * 完成日期：2017年12月23日 下午8:08:47<br/>
 * 编码作者：shaohj<br/>
 */
@Component
public class ApplicationInitProc implements CommandLineRunner{
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationInitProc.class);
	
	private @Autowired CodeCacheService codeCacheService;
	
	@Override
	public void run(String... arg0) throws Exception {
		/** 1.加载并缓存数据字典 */
		codeCacheService.refreshRootCode();
		
		List<TbCode> rootCodeList = CodeCacheService.getCodesByCodeType(UiPathConstants.CODE_ROOT_CODE);
		logger.debug("rootCodeList=" + JSON.toJSONString(rootCodeList));
	}

}
