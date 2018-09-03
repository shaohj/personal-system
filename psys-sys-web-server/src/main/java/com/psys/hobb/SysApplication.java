package com.psys.hobb;

import com.psys.hobb.sys.config.RestWebMvcConfigurer;
import org.apache.catalina.filters.RequestDumperFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.*;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.web.client.RestTemplate;

/**
 * 编  号：
 * 名  称：SysApplication
 * 描  述：
 * 完成日期：2018/6/23 12:22
 * 编码作者：SHJ
 */
@SpringBootApplication
@EnableOAuth2Sso //这里有自定义继承OAuth2SsoDefaultConfiguration类,在其顺序前,为了解决csrf问题,目前尚未找到更好的办法
@EnableDiscoveryClient
@EnableFeignClients
@Import(RestWebMvcConfigurer.class)
@EnableHystrix
public class SysApplication  {

    public static void main(String[] args) {
        SpringApplication.run(SysApplication.class, args);
    }

    @Bean
    @Primary
    @LoadBalanced //启用负载均衡
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    //@Bean //Oauth2的RestTemplate,默认不要使用这个
    //@LoadBalanced //启用负载均衡
    //OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext, OAuth2ProtectedResourceDetails details) {
    //    return new OAuth2RestTemplate(details, oauth2ClientContext);
    //}

    @Profile("!cloud")
    @Bean
    RequestDumperFilter requestDumperFilter() {
        return new RequestDumperFilter();
    }

}
