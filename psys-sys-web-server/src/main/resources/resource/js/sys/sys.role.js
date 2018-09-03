var _listTreeAllRoleResourceUrl = secContext + "/sys/role/query/resource/listTreeAll?access_token="+userToken,
	_saveOrUpdateRoleUrl = secContext + "/sys/role/saveOrUpdate?access_token="+userToken,
	_delByCodesUrl = secContext + "/sys/role/del/delByCodes",
	_getByIdUrl = secContext + "/sys/role/query/getById";

(function($){

	$.fn.initAddRoleDialog = function(option){
		var $self = $(this);
		
		var $mngDialog = $("#role_mng_dialog_div"); //管理对话框的IDV
		var $thisDialog = $mngDialog.children("#add_role_dialog"); //本次操作的对话框对象
		var $thisForm = $("#add_role_form"); //本次操作的form对象
		
		$thisDialog.dialog({
			bgiframe: true, resizable: false, width:600, modal: true, closeOnEscape:false,
			open:function(event,ui){$(".ui-dialog-titlebar-close").hide();},
			autoOpen: false,
			overlay: {backgroundColor: '#000', opacity: 0.5, background: "black"},
			buttons:[{
				text:"保存", icons:{primary:"ui-icon-folder-collapsed"}, click:function(){
					$thisForm.find("#submitAddRoleFrom").click(); 
				}
			},{
				text:"取消", icons:{primary:"ui-icon-closethick"}, click:function(){
					$(this).dialog("close");
					resetAddRoleDialog();
				}
			}],
		});
		
		$self.click(function(){
			loadRoleResourceList("addResTree", "add_role_dialog", "");
		})
		
	};
	
	$.fn.initModRoleDialog = function(option){
		var $self = $(this);
		
		var $mngDialog = $("#role_mng_dialog_div"); //管理对话框的IDV
		
		var $thisDialog = $mngDialog.children("#mod_role_dialog"); //本次操作的对话框对象
		var $thisForm = $("#mod_role_form"); //本次操作的form对象
		
		$thisDialog.dialog({
			bgiframe: true, resizable: false, width:600, modal: true, closeOnEscape:false,
			open:function(event,ui){$(".ui-dialog-titlebar-close").hide();},
			autoOpen: false,
			overlay: {backgroundColor: '#000', opacity: 0.5, background: "black"},
			buttons:[{
				text:"保存", icons:{primary:"ui-icon-folder-collapsed"}, click:function(){
					$thisForm.find("#submitModRoleFrom").click(); 
				}
			},{
				text:"取消", icons:{primary:"ui-icon-closethick"}, click:function(){
					$(this).dialog("close");
					resetModRoleDialog();
				}
			}],
		});
		
		$self.click(function(){
			var $thisTable = $("#datatable");
			var $checkBoxsByChecked = $thisTable.find(":checkbox[name='ck_roleId']:checked");
			
			if($checkBoxsByChecked.length==0){
				notifyDialog(SELECT_OPTION_ITEM_MSG);
			}else if($checkBoxsByChecked.length>1){
				notifyDialog(FORBID_EDIT_MULTITERM_DATA);
			}else{
				var checkVal = $checkBoxsByChecked.val();
				var checkId = checkVal.split("|")[0];
				
				var requestUrl = _getByIdUrl+"/"+checkId + "?access_token="+userToken;;
				
				ajaxGet(requestUrl, function (respData){
					$thisForm.find("#roleId").val(respData.roleId);
					$thisForm.find("#roleCode").val(respData.roleCode);
					$thisForm.find("#role_name").val(respData.name);
					$thisForm.find("#originalName").val(respData.name); //设置原有的名称
					$thisForm.find("#role_desc").val(respData.remark);
					
					loadRoleResourceList("modResTree", "mod_role_dialog", respData.roleCode);
				});
			}
		})
		
	};
	
})(jQuery);

//验证通过后执行的提交事件
function ajaxAddAndModRole(opt, treeId){
	var isAdd = opt == "add" ? true : false;
	
	var dialogId = isAdd ? "add_role_dialog" : "mod_role_dialog",
		formId = isAdd ? "add_role_form" : "mod_role_form";
	
	var $thisDialog = $("#" + dialogId); //本次操作的对话框对象
	var $thisForm = $("#" + formId); //本次操作的form对象
			
	var optArr = getAllSelectNodeData(treeId);
	var allOpt = "";
	for(var i=0;i<optArr.length;i++){
		if(optArr[i].code && optArr[i].code != ""){
			allOpt += optArr[i].code+",";
		}
	}
	
	var data = {
		roleId : isAdd ? null : $thisForm.find("#roleId").val(),
		roleCode : isAdd ? null : $thisForm.find("#roleCode").val(),
		name : $thisForm.find("#role_name").val(),
		remark : $thisForm.find("#role_desc").val(),
		operationIds:allOpt
	}
	
	var opt = isAdd ? "add" : "mod";
	
	var requestUrl = appendUrlRandom(_saveOrUpdateRoleUrl);
	ajaxPost(requestUrl, data, function(respData){
		$thisDialog.dialog("close");
		if(isAdd){
			resetAddRoleDialog();
		} else {
			resetModRoleDialog();
		}
		searchForPage();
		optMsgDialog(opt,"success");
	});
}

//删除角色
function deleteRoleList(roleIds){
	var requestUrl = _delByCodesUrl + "/" + roleIds + "?access_token="+userToken;;
	ajaxDelete(requestUrl, {}, function(respData){
		searchForPage();
		optMsgDialog("del","success");
	}, function(serviceResp){
		if(serviceResp.errorNO = '10'){
			searchForPage();
			notifyDialog(ITEM_USING_MSG); //记录被使用
		} else{
			optMsgDialog("del","fail");
		}
	});
}

//加载角色资源树结构
function loadRoleResourceList(treeId, dialogId, roleCode){
	var url = _listTreeAllRoleResourceUrl;
	if(roleCode){
		url += "&roleCode="+roleCode;
	}

	ajaxGet(url, function (treeData){
		var $tree = $("#"+treeId);
		
		$tree.fancytree({
			checkbox: true, selectMode: 3, source: treeData,
			loadChildren: function(event, data) {
				$("#"+dialogId).dialog("open");
			},
			loadError: function(event, data) {
				optMsgDialog("loadTree","fail");
			},
			click: function(event, data){  //节点激活事件
				var node = data.node,
					select = node.selected,
					clicktTarget = event.originalEvent.target.className; //获取check事件源class,为check时,不触发事件
				if(clicktTarget != "fancytree-checkbox"){
					if(select == true){
						node.setSelected(false);
					}else{
						node.setSelected(true);
					}
				}
			},
		});
	});
}

function comfirmDeleteRoleList() {  
	var $thisTable = $("#datatable");
	var $checkBoxsByChecked = $thisTable.find(":checkbox[name='ck_roleId']:checked");
	
	if($checkBoxsByChecked.length==0){
		notifyDialog(SELECT_DELETE_ITEM_MSG);
		return;
	}else{
		var roleCodeArr=[]; 
		var i=0;
		$checkBoxsByChecked.each(function(){
			var tVal = $(this).val();
			roleCodeArr[i] = tVal.split("|")[1];
			i++;
		})
	}
	
	Showbo.Msg.confirm(CONFIRM_DELETE_SELECT_ITEM_MSG,function(flag){
		if(flag=="yes"){
			deleteRoleList(roleCodeArr.join());
		}
	});
	
}

//重置增加对话框内容
function resetAddRoleDialog(){
	var $thisForm = $("#add_role_form");
	$thisForm.find("#resetModRoleFrom").click(); 
	$thisForm.find("#role_name").val("");
	$thisForm.find("#role_desc").val("");
	$thisForm.find("#result_set").empty(); //清除DIV内容
	
	$thisForm.find("#addResTree").fancytree("destroy");
};	

//重置修改对话框内容
function resetModRoleDialog(){
	var $thisForm = $("#mod_role_form");
	$thisForm.find("#resetModRoleFrom").click(); 
	$thisForm.find("#role_name").val("");
	$thisForm.find("#role_desc").val("");
	$thisForm.find("#result_set").empty(); //清除DIV内容
	
	$thisForm.find("#modResTree").fancytree("destroy");
};	
