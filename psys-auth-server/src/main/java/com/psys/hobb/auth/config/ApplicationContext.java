package com.psys.hobb.auth.config;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

/**
 * 将version版本号写入application中，给css,js引用时用
 * 编  号：<br/>
 * 名  称：ApplicationContext<br/>
 * 描  述：<br/>
 * 完成日期：2018年3月5日 上午11:11:21<br/>
 * 编码作者：shj<br/>
 */
@Component
public class ApplicationContext implements ServletContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationContext.class);
    
    /**
     * 
     * <p>初始化到Application作用域当中</p> 
     * @param context 
     * @see ServletContextAware#setServletContext(ServletContext)
     */
    @Override
    public void setServletContext(ServletContext context) {
    	logger.info("ApplicationContext.setServletContext...");
        String contextPath = context.getContextPath();
        context.setAttribute("ctx", contextPath);
    }

}
