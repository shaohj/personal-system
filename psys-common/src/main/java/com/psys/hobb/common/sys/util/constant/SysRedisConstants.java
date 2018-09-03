package com.psys.hobb.common.sys.util.constant;

import java.util.concurrent.TimeUnit;

public class SysRedisConstants {

    /** 过期时间设置为15分钟 */
    public static final long LOGIN_USER_EXPIRATION = 15;
    public static final TimeUnit LOGIN_USER_EXPIRATION_TIMEUNIT = TimeUnit.MINUTES;

    /** 登录验证码 */
    public static final String AUTH_VERCODE_KEY = "AUTH:VERCODE:";

    /** 登录成功首页的菜单资源 */
    public static final String AUTH_LOGIN_SUCCESS_URL_KEY = "AUTH:LOGIN:SUCCESS:URL";

    /** 登录用户 */
    public final static String SEC_LOGIN_USER_KEY = "SEC:LOGIN:USER:";

    /** 登录用户的菜单资源 */
    public static final String SEC_USER_MENU_KEY = "SEC:USER:MENU:";

    /** 游客的菜单资源 */
    public static final String SEC_TOURIST_MENU_KEY = "SEC:TOURIST:MENU";

}
