package com.psys.hobb.sec.event;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.EventBus;

@Service
public class CodeCacheRefreshEvent {

	private @Autowired CodeCacheRefreshEventListenerServiceImpl codeCacheRefreshEventListenerServiceImpl;
	
	/**
	 * 抛出修改群组事件
	 * TODO
	 * return : void
	 */
	public void throwCodeCacheRefreshEvent() {
		EventBus eb = new EventBus();
		eb.register(codeCacheRefreshEventListenerServiceImpl);
		eb.post(new Date());
	}
	
}
