	/** 您确认删除所选记录吗？ */
var CONFIRM_DELETE_SELECT_ITEM_MSG = "您确认删除所选记录吗？",
	/** 您确认移除所选记录吗？ */
	CONFIRM_REMOVE_SELECT_ITEM_MSG = "您确认移除所选记录吗？",
	/** 只能选择一条记录! */	
	FORBID_EDIT_MULTITERM_DATA = "只能选择一条记录！",
	/** 一次只能编辑一项记录! */	
	FORBID_EDIT_DIFFERENT_ADD_DATA = "一次只能编辑一项记录！",
	/** 请选择要操作的记录 */	
	SELECT_OPTION_ITEM_MSG = "请选择要操作的记录！",
	/** 请选择要删除的记录 */	
	SELECT_DELETE_ITEM_MSG = "请选择要删除的记录！",
	/** 该记录已使用，不能删除 */
	ITEM_USING_MSG = "该记录已使用，不能删除！",
	/** 该记录名称已使用，不能添加 */
	ITEM_NAME_USING_AND_NOT_ADD_MSG = "该记录名称已使用，不能添加！",
	/** 该记录已添加，不能添加 */
	DATA_CANNOT_BE_REPEATED = "该记录已添加，不能添加！",
	/** 该记录已添加版本，不能添加 */
	DATA_CANNOT_BE_REPEATED_ITEM = "该记录已添加版本，不能添加！",
	/** 该节点下存在子节点，请逐级删除 */
	HAS_CHILDNODE_MSG = "该节点下存在子节点，请逐级删除！";

/**
 * 常规操作通用对话框
 * @param opt 操作代码
 * @param result 操作结果
 * @param autoHide 是否自动消失.true：自动消失;false为点击后消失
 * @param showTime 显示时间,不传会有默认时间配置
 */
function optMsgDialog(opt, result, autoHide, showTime){
	var optMsg = "", //操作名称
		resultMsg = "", //操作结果
		hide = true, //自动消失
		time = 2000;
	if(autoHide){
		hide=autoHide;
	}
	if(showTime){
		time=showTime;
	}
	toastr.options = {
			  "closeButton": false,
			  "debug": false,
			  "positionClass": "toast-top-right",
			  "onclick": null,
			  "showDuration": "300",
			  "hideDuration": "1000",
			  "timeOut": time,
			  "extendedTimeOut": "1000",
			  "showEasing": "swing",
			  "hideEasing": "linear",
			  "showMethod": "fadeIn",
			  "hideMethod": "fadeOut"
			}
	switch(opt){
		case "enableUser":optMsg = "启用";break;
		case "disableUser":optMsg = "禁用";break;
		case "grantRole":optMsg = "角色分配";break;
		case "loadTree":optMsg = "加载树资源";break;
		case "initPwd":optMsg = "初始化密码";break;
		case "add":optMsg = "新增";break;
		case "opt":optMsg = "操作";break;
		case "save":optMsg = "保存";break;
		case "del":optMsg = "删除";break;
		case "mod":optMsg = "修改";break;
		case "get":optMsg = "查询";break;
		case "post":optMsg = "post请求";break;
		case "down":optMsg = "下载";break;
		case "upload":optMsg = "上传";break;
		case "copy":optMsg = "复制";break;
		case "remove":optMsg = "移除";break;
		case "start":optMsg = "启动";break;
		case "stop":optMsg = "停止";break;
		case "vote":optMsg = "投票";break;
		default:optMsg=opt;break;
	}
	
	switch(result){
		case "success":resultMsg = "成功！"; successDialog(optMsg+resultMsg,hide,time); break;
		case "fail":resultMsg = "失败！"; failDialog(optMsg+resultMsg,hide,time); break;
		case "warning":warningDialog(optMsg,hide,time); break;
		case "notify": notifyDialog(optMsg,hide,time); break;
	}
}

/**
 * 通用ajax请求失败处理
 * @param status
 */
function ajaxErrorDialog(status){
	if(status == "timeout"){
		warningDialog("连接服务器超时！");
	}else if(status == "error"){
		warningDialog("连接服务器失败！");
	}
}

function successDialog(msg,hide,time){
	toastr.success(msg);
}

function failDialog(msg,hide,time){
	toastr.error(msg);
}

function notifyDialog(msg,hide,time){
	toastr.info(msg);
}

function warningDialog(msg,hide,time){
	toastr.warning(msg);
}