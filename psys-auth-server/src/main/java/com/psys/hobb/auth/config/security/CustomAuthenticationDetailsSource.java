package com.psys.hobb.auth.config.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * 实现自定义的AuthenticationDetailsSource
 *   该接口用于在Spring Security登录过程中对用户的登录信息的详细信息进行填充，默认实现是WebAuthenticationDetailsSource，
 *   生成上面的默认实现WebAuthenticationDetails。我们编写类实现AuthenticationDetailsSource，
 *   用于生成上面自定义的CustomWebAuthenticationDetails。
 * 编  号：<br/>
 * 名  称：CustomAuthenticationDetailsSource<br/>
 * 描  述：<br/>
 * 完成日期：2018年1月24日 下午5:37:41<br/>
 * 编码作者：shj<br/>
 */
@Component
public class CustomAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new CustomWebAuthenticationDetails(context);
    }

}
