package com.psys.hobb.auth.config.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.cors.CorsUtils;

/**
 * Spring Boot中使用Spring Security进行安全控制
 *   http://blog.didispace.com/springbootsecurity/
 * 编  号：<br/>
 * 名  称：WebSecurityConfig<br/>
 * 描  述：<br/>
 * 完成日期：2018年1月9日 上午10:02:00<br/>
 * 编码作者：shj<br/>
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(-20)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecAuthenticationProvider provider;//自定义验证

	@Autowired
	private LoginSuccessHandler loginSuccessHandler;//自定义登录成功处理

	@Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
			.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/j_spring_secutity_check")
				.defaultSuccessUrl("/index")
				.usernameParameter("username")
				.passwordParameter("password")
				.failureUrl("/login?failed=true")
				.permitAll()
				.successHandler(loginSuccessHandler)
				.authenticationDetailsSource(authenticationDetailsSource)
		.and()
			.requestMatchers()
				/*  配置项目可以接受哪些路径的请求 */
				.antMatchers("/", "/index", "/myindex", "/loginout", "/login", "/j_spring_secutity_check", "/user/**")
				.antMatchers( "/oauth/authorize", "/oauth/confirm_access")
		.and()
	    	.authorizeRequests()
				/** 配置项目请求对应的角色权限 */
	        	.antMatchers("/", "/login**", "/login/**", "/loginout", "/user/oauth/revokeToken").permitAll()
	        	.antMatchers("/error", "/error/**").permitAll()
				.anyRequest().authenticated()
		.and()
        	.logout()
	        	.logoutUrl("/loginout")
	        	.logoutSuccessUrl("/login")
	        	.permitAll()
	        	.clearAuthentication(true)
	        	.invalidateHttpSession(true)
        .and()
        	.csrf().disable()
		.sessionManagement();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }

}