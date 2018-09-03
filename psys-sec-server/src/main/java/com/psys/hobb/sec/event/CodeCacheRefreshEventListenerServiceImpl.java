package com.psys.hobb.sec.event;

import java.util.Date;

import com.psys.hobb.sec.service.sec.impl.CodeCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

@Service
public class CodeCacheRefreshEventListenerServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(CodeCacheRefreshEventListenerServiceImpl.class);

	private @Autowired
	CodeCacheService codeCacheService;
	
	@Subscribe
	public void onEvent(Date date) {
		try {
			logger.info("Successful receive event[throwCodeCacheRefreshEvent]");
			codeCacheService.refreshRootCode();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Failed to receive event[throwCodeCacheRefreshEvent]" + e);
		}
	}

	@Subscribe
	public void onEvent(DeadEvent de) {
		logger.error("Failed to receive events:" + de.getEvent());
		System.out.println("发布了错误的事件:" + de.getEvent());
	}
	
}
