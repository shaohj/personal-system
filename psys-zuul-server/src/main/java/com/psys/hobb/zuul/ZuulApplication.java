package com.psys.hobb.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * zuul路由网关服务器
 * 编  号：
 * 名  称：ZuulApplication
 * 描  述：
 * 完成日期：2018/7/15 20:54
 * 编码作者：SHJ
 */
@SpringBootApplication
@EnableZuulProxy
@EnableResourceServer
@EnableEurekaClient
public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

}