package com.psys.hobb.common.sys.util.constant;

import java.time.format.DateTimeFormatter;

/**
 * 系统管理常量配置
 * 编  号：<br/>
 * 名  称：UiPathConstants<br/>
 * 描  述：<br/>
 * 完成日期：2017年11月28日 下午10:01:40<br/>
 * 编码作者：shaohj<br/>
 */
public class UiPathConstants {
	
	public static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
	
	/* 系统管理url配置 start */
	public static final String ROOT_MENU_LOAD_URL = "center/menu/rootMenu";

	public static final String CHILD_MENU_LOAD_URL = "center/menu/childMenu";

	public static final String SYS_ROLE_INDEX_URL = "/sys/role/roleList.ftl";
	
	public static final String SYS_ROLE_INDEX_TABLE_URL = "/sys/role/roleTable";
	
	public static final String SYS_USER_INDEX_URL = "/sys/user/userList.ftl";
	
	public static final String SYS_USER_INDEX_TABLE_URL = "/sys/user/userTable";
	
	public static final String SYS_USER_ROLE_GRANT_URL = "/sys/user/grantUserRoleDialog";
	
	public static final String SYS_CODE_INDEX_URL = "/sys/code/codeList.ftl";
	
	public static final String SYS_RESOURCE_INDEX_URL = "/sys/resource/resourceList.ftl";

	public static final String SYS_RESOURCE_TOURIST_INDEX_URL = "/sys/restourist/resourceTouristList.ftl";

	public static final String SYS_GROUP_INDEX_URL = "/sys/group/groupList.ftl";
	/* 系统管理url配置 end */
	
	/* 博客管理url配置 start */
	public static final String BLOG_CENTER_INDEX_URL = "/blog/center/blogCenterList.ftl";
	
	public static final String BLOG_CENTER_TABLE_URL = "/blog/center/blogCenterTable";
	
	public static final String BLOG_GROUP_INDEX_URL = "/blog/group/blogGroupList.ftl";
	
	public static final String BLOG_GROUP_TABLE_URL = "/blog/group/blogGroupTable";
	/* 博客系统url配置 end */

	/** 顶层菜单父节点ID */
	public static final String ROOT_RESOURCE_PARENT_ID = "-1";
	
	/** 顶层Code父节点ID */
	public static final String ROOT_CODE_PARENT_CODE = "-1";

	/** 顶层Group父节点ID */
	public static final String ROOT_GROUP_PARENT_CODE = "-1";
	
	/** 是管理员用户 */
	public static final String ADMIN_USER_YES = "Y";

	/** 是系统内置资源 */
	public static final String SYSTEM_RESOURCE_YES = "Y";

	/** 删除标识.Y为未删除 */
	public static final String ENABLED_FLAG = "Y";
	
	/* 系统管理sn_code常量 start */
	public static final String TB_CODE_SN_TYPE = "tb_code_sn";
	
	public static final String USER_SN_TYPE = "user_sn";
	public static final String GROUP_SN_TYPE = "group_sn";
	public static final String ROLE_SN_TYPE = "role_sn";
	public static final String OPERATION_SN_TYPE = "operation_sn";
	public static final String PERMISSION_SN_TYPE = "permission_sn";
	public static final String RESOURCE_SN_TYPE = "menu_sn";
	public static final String TOURIST_RESOURCE_SN_TYPE = "menu_tour_sn";
	/* 系统管理sn_code常量 end */
	
	/* 系统管理异常提示信息 start */
	/** 用户未登录 */
	public static final String USER_NOT_LOGIN_EXCEPTION_MSG = "用户未登录";

	/** 含有子数据,不能删除 */
	public static final String HAS_CHILD_RESOURCE_EXCEPTION_MSG = "含有子数据,不能删除";

	/** 数据被使用,不能被删除 */
	public static final String RESOURCE_USING_EXCEPTION_MSG = "数据被使用,不能被删除";
	
	/** 数据被使用,不能被删除 */
	public static final String RESOURCE_HAS_DELETE_EXCEPTION_MSG = "资源不存在，或已被删除";

	/** 系统内置资源，不能被删除 */
	public static final String DELETE_SYSTEM_RESOURCE_MSG = "系统内置资源，不能被删除";

	/** 系统内置资源，不能被删除 */
	public static final String CALL_SERVICE_NO_RESPONSE_MSG = "调用接口无返回结果";

	public static final String CALL_SERVICE_RESPONSE_EMPTY_DATE_MSG = "调用接口返回result数据为空";
	/* 系统管理异常提示信息 end */
	
	/* 系统管理数据字典常量 start */
	public static final String CODE_ROOT_CODE = "root_code";
	
	public static final String CODE_USER_STATE = "user_state";
	
	public static final String CODE_USER_STATE_NORMAL = "user_state_normal";
	
	public static final String CODE_USER_STATE_DISABLED = "user_state_disabled";

	public static final String LOGIN_SUCCESS_URL = "login_success_url";
	/* 系统管理数据字典常量 end */
	
}
