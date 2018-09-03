var _toGrantRoleIndexUrl = contextPath + "/sys/user/role/to/grant",
	_listTreeAllGroupUrl = secContext + "/sys/group/query/listTreeAll?access_token="+userToken,
	_saveOrUpdateUserUrl = secContext + "/sys/user/saveOrUpdate?access_token="+userToken,
	_saveOrUpdateUserRoleUrl = secContext + "/sys/user/role/saveOrUpdate?access_token="+userToken,
	_getUserById = secContext + "/sys/user/query/getById",
	_enableUserUrl = secContext + "/sys/user/enable/userIds",
	_disableUserUrl = secContext + "/sys/user/disable/userIds";

(function($){
	
	/** 初始化创建用户对话框插件 */
	$.fn.initAddUserDialog = function(option){
		var $self = $(this);
		
		var $mngDialog = $("#user_mng_dialog_div"); //管理对话框的IDV
		var $thisDialog = $mngDialog.children("#add_user_dialog"); //本次操作的对话框对象
		var $thisForm = $thisDialog.find("#add_user_form"); //本次操作的form对象
		
		$thisDialog.dialog({
			bgiframe: true, resizable: false, width:650, modal: true, closeOnEscape:false, 
			open:function(event,ui){$(".ui-dialog-titlebar-close").hide();},
			autoOpen: false,
			overlay: {backgroundColor: '#000', opacity: 0.5, background: "black"},
			buttons:[{
				text:"保存", icons:{primary:"ui-icon-folder-collapsed"}, click:function(){
					$thisForm.find("#submitAddUserFrom").click(); 
				}
			},{
				text:"取消", icons:{primary:"ui-icon-closethick"}, click:function(){
					$(this).dialog("close");
				}
			}],
		});
		
		$self.click(function(){
			resetAddUserDialog();//阻止浏览器自动填充表单
			loadGroup("addGroupTree", "add_user_dialog", "addGroup");
		})
		
	};
	
	//初始化修改用户对话框插件
	$.fn.initModUserDialog = function(option){
		var $self = $(this);
		
		var $mngDialog = $("#user_mng_dialog_div"); //管理对话框的IDV
		var $thisDialog = $mngDialog.children("#mod_user_dialog"); //本次操作的对话框对象
		var $thisForm = $thisDialog.find("#mod_user_form"); //本次操作的form对象
		
		$thisDialog.dialog({
			bgiframe: true, resizable: false, width:600, modal: true, closeOnEscape:false, 
			open:function(event,ui){$(".ui-dialog-titlebar-close").hide();},
			autoOpen: false,
			overlay: {backgroundColor: '#000', opacity: 0.5, background: "black"},
			buttons:[{
				text:"保存",icons:{primary:"ui-icon-folder-collapsed"},click:function(){
					$thisForm.find("#submitModUserFrom").click(); 
				}
			},{
				text:"取消",icons:{primary:"ui-icon-closethick"},click:function(){
					$(this).dialog("close");
					resetModUserDialog();
				}
			}],
		});
		
		var $group = $thisDialog.find("#modGroupTree");
		$self.click(function(){
			var $thisTable = $("#datatable");
			var $checkBoxsByChecked = $thisTable.find(":checkbox[name='ck_userid']:checked");
			
			if($checkBoxsByChecked.length==0){
				notifyDialog(SELECT_OPTION_ITEM_MSG);
			}else if($checkBoxsByChecked.length>1){
				notifyDialog(FORBID_EDIT_MULTITERM_DATA);
			}else{
				resetModUserDialog();
				
				var userVal = $checkBoxsByChecked.val();
				var userId = userVal.split("|")[0];
				
				var requestUrl = _getUserById + "/" + userId + "?access_token="+userToken;
				
				ajaxGet(requestUrl, function (respData){
					$thisDialog.find("#userId").val(respData.userId);
					$thisDialog.find("#userCode").val(respData.userCode);
					$thisDialog.find("[name='userName']").val(respData.userName);
					$thisDialog.find("#originalName").val(respData.userName); //原有用户名
					$thisDialog.find("[name='password']").val(respData.password);
					$thisDialog.find("[name='realName']").val(respData.realName);
					$thisDialog.find("#msex_0"+respData.sex).prop("checked",true);
                    $thisDialog.find("#msex_0"+respData.sex).parents('div').addClass("checked");
					
					$thisDialog.find("[name='birthday']").val(respData.birthday);
					$thisDialog.find("[name='mobilePhone']").val(respData.mobile);
					$thisDialog.find("[name='email']").val(respData.email);
					
					loadGroup("modGroupTree","mod_user_dialog","modGroup", respData.secGroup.groupCode);
				});
			}
		})
		
	};
	
	//初始化用户授权对话框插件
	$.fn.initGrantUserRoleDialog = function(option){
		var $self = $(this);
		
		var $mngDialog = $("#user_mng_dialog_div"); //管理对话框的IDV
		var $thisDialog = $mngDialog.children("#grant_userRole_dialog"); //本次操作的对话框对象
		
		$thisDialog.dialog({
			bgiframe: true, resizable: false, width:600, modal: true, closeOnEscape:false, 
			open:function(event,ui){$(".ui-dialog-titlebar-close").hide();},
			autoOpen: false,
			overlay: {backgroundColor: '#000', opacity: 0.5, background: "black"},
			buttons:[{
				text:"保存",icons:{primary:"ui-icon-folder-collapsed"}, click:function(){
					$thisDialog.find("#submitGrantUserRoleFrom").click();
				}
			},{
				text:"取消",icons:{primary:"ui-icon-closethick"}, click:function(){
					$(this).dialog("close");
				}
			}],
		});
		
		$self.click(function(){
			var $thisTable = $("#datatable");
			var $checkBoxsByChecked = $thisTable.find(":checkbox[name='ck_userid']:checked");
			
			if($checkBoxsByChecked.length==0){
				notifyDialog(SELECT_OPTION_ITEM_MSG);
			}else if($checkBoxsByChecked.length>1){
				notifyDialog(FORBID_EDIT_MULTITERM_DATA);
			}else{
				var userVal = $checkBoxsByChecked.val();
				var userId = userVal.split("|")[0];
				
				var requestUrl = _toGrantRoleIndexUrl + "/" + userId;
				
				ajaxLoad($thisDialog, requestUrl, function(){
					$thisDialog.find("#grantUserId").val(userId); //加载好页面后设置当前操作的用户ID
					$thisDialog.dialog("open");
				});
			}
		})
		
	};
	
	//加载角色资源树结构
	var loadGroup = function(treeId, dialogId, selectId, groupCode){
		var $thisDialog = $("#"+dialogId);
		var $select = $thisDialog.find("#"+selectId);
		var $selectOption = $select.find("option:selected");
		var $tree = $("#"+treeId);
		
		var url = _listTreeAllGroupUrl;
		if(groupCode && groupCode != ""){
			url += "&activeCode="+groupCode;
		}
		
		ajaxGet(url, function(treeData){
			$tree.fancytree({
				selectMode: 2,
				source: treeData,
				loadChildren: function(event, data) {
					var $fancytree = $tree.fancytree("getTree");
					var key = ""; //默认激活节点key
					
					if(groupCode && "" != groupCode){
						key = singleDataActiveKey(treeId);
					}
					
					if(!key || "" == key){
						key = "1"; 
					}
					
					$fancytree.activateKey(key);
					
					$thisDialog.dialog("open");
				},
				loadError: function(event, data) {
					optMsgDialog("loadTree","fail");
				},
				activate: function(event, data){  //节点激活事件
					var node = data.node,
						nodeData = node.data;
					if(nodeData.id && nodeData.id != ""){
						var temp = nodeData.name;
						$select.empty();
						$select.append("<option selected  value='"+nodeData.id+"' style='display:none;'>"+temp+"</option>");
						$select.attr("title",temp);
						$thisDialog.find("#"+treeId).css("display","none");
					}
				},
			});
		});
	}
	
})(jQuery);

/** 异步增加或修改用户事件 */
function ajaxSubAddOrModUser(dialogId, formId, treeId){
	var $thisDialog = $("#" + dialogId); //本次操作的对话框对象
	var $thisForm = $thisDialog.find("#" + formId); //本次操作的form对象
	
	var userId = $thisForm.find("#userId").val();
	var opt = userId == undefined || userId == "" ? "add" : "mod";
	var isAdd = opt == "add" ? true : false;
	var groupUiCtrl = isAdd ? "addGroup" : "modGroup";
	var password;
	if(userId){
		password = $thisForm.find("[name='password']").val(); //新增时将密码加密
	} else { 
		password = hex_md5($thisForm.find("[name='password']").val()); //修改时不加密密码
	}
	
	var obj = {
		userId : isAdd ? null : $thisForm.find("#userId").val(),
		userCode : isAdd ? null : $thisForm.find("#userCode").val(),
		userName : $thisForm.find("[name='userName']").val(),
		password : password,
		realName : $thisForm.find("[name='realName']").val(),
		sex : $thisForm.find("input[name='sex']:checked").val(),
		mobile : $thisForm.find("[name='mobilePhone']").val(),
		email : $thisForm.find("[name='email']").val(),
		secGroup : {
	    	groupCode: $thisForm.find("#" + groupUiCtrl).val()
	    }
	};
	
	var	birthday = $thisForm.find("[name='birthday']").val();
	if(birthday != ""){
		obj.birthday = birthday;
	}
	
	var requestUrl = appendUrlRandom(_saveOrUpdateUserUrl);
	ajaxPost(requestUrl, obj, function(respData){
		$thisDialog.dialog("close");
		searchForPage();
		optMsgDialog(opt, "success");
	}, function (){
		optMsgDialog(opt, "fail");
	});
}

/** 操作用户列表
 *  opt:"del":删除用户;"enableUser":启用用户;"disableUser":"禁用用户"
 *  
 */
function comfirmOptionUserList(opt) {
	var curOpt = "";
	if(opt == "del"){
		curOpt = "删除";
	}else if(opt == "enableUser"){
		curOpt = "启用";
	}else if(opt == "disableUser"){
		curOpt = "禁用";
	}else{
		return ;
	}
	
	var $thisTable = $("#datatable");
	var $checkBoxsByChecked = $thisTable.find(":checkbox[name='ck_userid']:checked");
	
	if($checkBoxsByChecked.length==0){
		notifyDialog(SELECT_OPTION_ITEM_MSG);
	}else{
		var incluseAdmin = false; //包含管理员用户
		
		var enableArr = new Array(); 
		var disabledArr = new Array(); 
		
		var tUidArr = new Array(); 
		var tUnameArr = new Array();
		var i=0;
		$checkBoxsByChecked.each(function(){
			var tVal = $(this).val();
			tUidArr.push(tVal.split("|")[0]);
			tUnameArr.push(tVal.split("|")[1]);
			if(tVal.split("|")[2] == "Y"){
				incluseAdmin = true;
				return;
			}
			if(tVal.split("|")[3] == "user_state_normal"){
				enableArr.push(tVal.split("|")[1]); //已启用的用户
			}else{
				disabledArr.push(tVal.split("|")[1]); //已禁用的用户,只有两种情况
			}
			
			i++;
		})
		
		if(incluseAdmin == true){
			notifyDialog("admin是内置管理员,不能"+curOpt+"！");
		}else {
			if(opt == "enableUser" && enableArr.length>0){
				notifyDialog("该用户已启用！");
			}else if(opt == "disableUser" && disabledArr.length>0){
				notifyDialog("该用户已禁用！");
			}else{
				Showbo.Msg.confirm("确认"+curOpt+"所选记录吗?",function(flag){
					if(flag=="yes"){
						optionUserList(opt, tUidArr.join(","));
					}
				});
			}
		}
	}	
}

function optionUserList(opt, userIds){
	var requestUrl = "";
	if(opt == "enableUser"){
		requestUrl = _enableUserUrl + "/" + userIds  + "?access_token="+userToken;;
	}else if(opt == "disableUser"){
		requestUrl = _disableUserUrl + "/" + userIds  + "?access_token="+userToken;;
	}else{
		return ;
	}
	
	var requestUrl = appendUrlRandom(requestUrl);
	ajaxPost(requestUrl, {}, function(respData){
		searchForPage();
		optMsgDialog(opt,"success",true);
	});
}


/** 异步提交用户授权事件 */
function ajaxSubGrantUserRole(){
	var $thisDialog = $("#grant_userRole_dialog"); //本次操作的对话框对象
	var $thisForm = $thisDialog.find("#grant_userRole_form"); //本次操作的form对象
	
	var userId = $thisForm.find("#grantUserId").val();
	var $checkBoxsByChecked = $thisForm.find("input[type='checkbox'][name='roleItem'][checked='checked']");
	
	var roleCodes = new Array();
	$checkBoxsByChecked.each(function(){
		roleCodes.push($(this).val());
	})
	
	var jsonData = "";
	if(roleCodes.length>0){
		jsonData = {
			userId:userId,
			userRoleCodes:roleCodes
		};
	}else{
		jsonData = {
			userId:userId
		};
	}
	
	var requestUrl = appendUrlRandom(_saveOrUpdateUserRoleUrl);
	ajaxPost(requestUrl, jsonData, function(respData){
		$thisDialog.dialog("close");
		searchForPage();
		optMsgDialog("grantRole","success");
	}, function (){
		optMsgDialog("grantRole", "fail");
	});
	
}

/** 重置增加对话框内容 */
function resetAddUserDialog(){
	var $thisForm = $("#add_user_form");
	$thisForm.find("#resetAddUserFrom").click(); 
	
	$thisForm.find("[name='userName']").val("");
	$thisForm.find("[name='password']").val("");
	$thisForm.find("[name='realName']").val("");
	$thisForm.find("[name='birthday']").val("");
	$thisForm.find("[name='mobilePhone']").val("");
	$thisForm.find("[name='email']").val("");
	
	$thisForm.find("#addGroupTree").css("display","none");
	try{
		$thisForm.find("#addGroupTree").fancytree("destroy");
	}catch(e){
		console.log("init sure no cache = "+e);
	}
};	

/** 重置修改对话框内容 */
function resetModUserDialog(){
	var $thisForm = $("#mod_user_form");
	$thisForm.find("#resetModUserFrom").click(); 
	
	$thisForm.find("#userName").val("");
	$thisForm.find("#password").val("");
	$thisForm.find("#realName").val("");
	$thisForm.find("#birthday").val("");
	$thisForm.find("#mobilePhone").val("");
	$thisForm.find("#email").val("");

    $thisForm.find("input[name='sex']").prop("checked",false);
    $thisForm.find("input[name='sex']").parents('div').removeClass("checked");
	
	$thisForm.find("#modGroupTree").css("display","none");
	try{
		$thisForm.find("#modGroupTree").fancytree("destroy");
	}catch(e){
		console.log("init sure no cache = "+e);
	}
};	

/** 初始化密码 */
function initPwd(){
	var $thisForm = $("#add_user_form");
	$thisForm.find("[name='password']").val("123456"); //初始化密码为123456
	$thisForm.find("[name='password']").blur();
	optMsgDialog("initPwd","success");
}

function initEncryptionPwd(){
	var $thisForm = $("#mod_user_form");
	$thisForm.find("[name='password']").val(hex_md5("123456")); //初始化密码为123456
	$thisForm.find("[name='password']").blur();
	optMsgDialog("initPwd","success");
}
