package com.psys.hobb.auth.config.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * 在登录验证时，附带增加额外的数据，如验证码,还可以带用户类型等数据.
 * 编  号：<br/>
 * 名  称：CustomWebAuthenticationDetails<br/>
 * 描  述：<br/>
 * 完成日期：2018年1月24日 下午5:37:02<br/>
 * 编码作者：shj<br/>
 */
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

	private static final long serialVersionUID = 1L;

	private final String validateCode;

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        validateCode = request.getParameter("validateCode");
    }

	public String getValidateCode() {
		return validateCode;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("; validateCode: ").append(this.getValidateCode());
        return sb.toString();
    }

}
