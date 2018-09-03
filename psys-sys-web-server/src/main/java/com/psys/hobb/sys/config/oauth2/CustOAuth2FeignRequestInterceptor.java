package com.psys.hobb.sys.config.oauth2;

import com.psys.hobb.common.sso.constants.Oauth2Constants;
import feign.RequestTemplate;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

/**
 * 自定义拦截器,对特定的url不进行Oauth2拦截
 * 编  号：
 * 名  称：CustOAuth2FeignRequestInterceptor
 * 描  述：
 * 完成日期：2018/7/14 14:52
 * 编码作者：SHJ
 */
public class CustOAuth2FeignRequestInterceptor extends OAuth2FeignRequestInterceptor {

    public CustOAuth2FeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails resource) {
        super(oAuth2ClientContext, resource);
    }

    public void apply(RequestTemplate template) {
        final String reqUrl = template.url();
        long count = Oauth2Constants.EXCLUDE_URLS.stream().filter(url -> url.equalsIgnoreCase(reqUrl)).count();

        if(0 == count){
            super.apply(template);
        }
    }

}
