package com.psys.hobb.sec.utils;

import com.psys.hobb.common.sys.util.AssertUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.USER_NOT_LOGIN_EXCEPTION_MSG;

public class SecurityUtils {

    public static String getTokenWithValid(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AssertUtil.notNull(auth, USER_NOT_LOGIN_EXCEPTION_MSG);
        OAuth2AuthenticationDetails detail = (OAuth2AuthenticationDetails)auth.getDetails();
        AssertUtil.notNull(auth, USER_NOT_LOGIN_EXCEPTION_MSG);
        String tokenValue = detail.getTokenValue();
        return  tokenValue;
    }

    /**
     * JdbcTokenStore的源码，将access_token转为token_id,由于原来的是protected，因此作本次处理
     * @param value :
     * @return
     * @author SHJ
     * @since 2018/6/23 18:01
     */
    public static String extractTokenKey(String value) {
        if (value == null) {
            return null;
        } else {
            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException var5) {
                throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
            }

            try {
                byte[] bytes = digest.digest(value.getBytes("UTF-8"));
                return String.format("%032x", new BigInteger(1, bytes));
            } catch (UnsupportedEncodingException var4) {
                throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
            }
        }
    }

}
