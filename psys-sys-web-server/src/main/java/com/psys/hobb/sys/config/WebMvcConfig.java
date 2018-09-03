package com.psys.hobb.sys.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.GzipResourceResolver;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.info("# WebMvcConfig addResourceHandlers ...");
        //如果打算将静态文件放在resources下面，则需要启用下面一段代码，如果将静态文件放在webapp下面，则什么也不用配置，直接可以访问
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/")
                .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES).cachePrivate())
                .resourceChain(true)
                .addResolver(new GzipResourceResolver());
        registry.addResourceHandler("/resource/**").addResourceLocations("classpath:/resource/")
                .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES).cachePrivate())
                .resourceChain(true)
                .addResolver(new GzipResourceResolver());
    }

}
