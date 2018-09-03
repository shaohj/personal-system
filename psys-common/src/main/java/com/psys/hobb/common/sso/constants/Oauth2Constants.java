package com.psys.hobb.common.sso.constants;

import java.util.Arrays;
import java.util.List;

/**
 * OAuth2常量
 * 编  号：
 * 名  称：Oauth2Constants
 * 描  述：
 * 完成日期：2018/7/14 14:58
 * 编码作者：SHJ
 */
public class Oauth2Constants {

    /** OAuth2 Feign不拦截请求的Url */
    public static final List<String> EXCLUDE_URLS = Arrays.asList("/sec-server/sys/resourcetourist/query/getTouristMenu");

}
