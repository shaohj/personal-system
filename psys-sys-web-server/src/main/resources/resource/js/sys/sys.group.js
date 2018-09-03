var _listTreeAllGroupUrl = secContext + "/sys/group/query/listTreeAll?access_token="+userToken,
	_saveOrUpdateGroupUrl = secContext + "/sys/group/saveOrUpdate?access_token="+userToken,
	_delByCodeUrl = secContext + "/sys/group/del/delByCode",
	_getByCodeUrl = secContext + "/sys/group/query/getByCode";

var _hasDel = false;

function loadGroupList(treeId){
	ajaxGet(_listTreeAllGroupUrl, function (treeData){
		var $tree = $("#"+treeId);
		
		$tree.fancytree({
			source: treeData,
			loadChildren: function(event, data) {
				$tree.fancytree("getTree").activateKey("1"); //加载完成后,默认激活第一个数据节点
			},
			loadError: function(event, data) {
				optMsgDialog("loadTree","fail");
			},
			activate: function(event, data){  //节点激活事件
				toggleDomElementDisplay("editGroupForm",true);
				var node = data.node,
					nodeData = node.data;
				if(nodeData.id && nodeData.id != ""){
					getGroupByCode(nodeData.id);
				}else{
					initAddGroupData(treeId);
				}
			},
			deactivate: function(event, data) { //节点失去激活事件
				var node = data.node,
					key = node.key,
					nodeData = node.data;
				
				if(_hasDel == false){ //数据未被删除
					if(!nodeData.id || nodeData.id == ""){
						Showbo.Msg.confirm("您放弃保存[新增数据}吗?",function(flag){
							if(flag=="yes"){
								node.remove();
							}else{
								$tree.fancytree("getTree").activateKey(key);
							}
						});
					}
				}else{
					_hasDel = false;
				}
			},
		});
	})
}

/** 同步增加或修改组织 */
function ajaxSubAddOrModGroup(treeId){
	var $thisForm = $("#editGroupForm");
	
	var requestUrl = appendUrlRandom(_saveOrUpdateGroupUrl);
	var data = {
			groupId : $thisForm.find("#groupId").val(),
			groupCode : $thisForm.find("#groupCode").val(),
			name : $thisForm.find("#name").val(),
			parentId : $thisForm.find("#parentId").val(),
			remark : $thisForm.find("#remark").val()
		}
	
	var opt = $thisForm.find("#groupId").val() == "" ? "add" : "mod";
	ajaxPost(requestUrl, data, function(respData){
		optMsgDialog("add","success");
		updateGroupTreeItem(treeId, respData);
		updateGroupFormData(respData);
	}, function (){
		optMsgDialog(opt, "fail");
	});
}

/** 删除组织数据 */
function delGroup(treeId, groupCode){
	var requestUrl = _delByCodeUrl + "/" + groupCode + "?access_token="+userToken;
	ajaxDelete(requestUrl, {}, function(respData){
		toggleDomElementDisplay("editGroupForm");
		_hasDel = true;
		removeActivateNode(treeId);
		
		optMsgDialog("del","success");
	}, function(serviceResp){
		notifyDialog(serviceResp.errorMsg);
	});
}

/**
 * 编辑组织数据
 */
function getGroupByCode(groupCode){
	var requestUrl = _getByCodeUrl + "/" + groupCode + "?access_token="+userToken;
	
	ajaxGet(requestUrl, function (respData){
		updateGroupFormData(respData);
	});
}

/**
 * tree添加同级节点
 * @param treeId
 */
function addGroupSiblingNode(treeId){
	var node = $("#"+treeId).fancytree("getActiveNode");
	if(node){
		var nodeData = node.data;
		if(!nodeData.id || nodeData.id == ""){
			notifyDialog(FORBID_EDIT_DIFFERENT_ADD_DATA);
		}else if(nodeData.parentId == "-1"){
			notifyDialog("根组织不能添加同级节点！"); //根据业务功能判断,根节点只能有一个
		}else{
			addSiblingNode(treeId);
		}
	}else{
		notifyDialog("请选择节点！");
	}
}

/**
 * tree添加子节点
 * @param treeId
 */
function addGroupChildNode(treeId){
	var node = $("#"+treeId).fancytree("getActiveNode");
	if(node){
		var nodeData = node.data;
		if(!nodeData.id || nodeData.id == ""){
			notifyDialog(FORBID_EDIT_DIFFERENT_ADD_DATA);
		}else if(nodeData.level >= groupMaxLevel ){
			notifyDialog("该层级节点不能再添加子节点！");
		}else{
			addChildNode(treeId);
		}
	}else{
		notifyDialog("请选择节点！");
	}
}

/**
 * tree删除节点
 * @param treeId
 */
function delGroupNode(treeId){
	var node = $("#"+treeId).fancytree("getActiveNode");
	if(node){
		var nodeData = node.data;
		if(!nodeData.id || nodeData.id == ""){
			Showbo.Msg.confirm(CONFIRM_DELETE_SELECT_ITEM_MSG,function(flag){
				if(flag=="yes"){
					_hasDel = true;
					toggleDomElementDisplay("editGroupForm");
					node.remove();
				}else{
					$tree.fancytree("getTree").activateKey(key);
				}
			});
		} else if(nodeData.parentId == "-1"){
            notifyDialog("不能删除根组织！");
        } else{
			if(node.children && node.children.length > 0){
				notifyDialog(HAS_CHILDNODE_MSG);
			}else{
				//放弃正在编辑的资源
				Showbo.Msg.confirm(CONFIRM_DELETE_SELECT_ITEM_MSG,function(flag){
					if(flag=="yes"){
						delGroup(treeId,nodeData.id);
					}
				});
			}
		}
	}else{
		notifyDialog("请选择节点！");
	}
}

/** 表单显示为新增数据 */
function initAddGroupData(treeId){
	var $thisForm = $("#editGroupForm");
	
	$thisForm.find("#resetEditGroupForm").click(); //重置验证样式
	
	$thisForm.find("#originalName").val("");
	
	var $tree = $("#"+treeId).fancytree("getTree");
	var node = $tree.getActiveNode();
	
	$thisForm.find("#groupId").val("");
	$thisForm.find("#groupCode").val("");
	$thisForm.find("#parentId").val(node.data.parentId);
	$thisForm.find("#name").val("新增数据");
	$thisForm.find("#remark").val("");
}

/** 更新当前编辑的树节点 */
function updateGroupTreeItem(treeId,data){
	var $tree = $("#"+treeId).fancytree("getTree");
	var node = $tree.getActiveNode();
	
	node.tooltip = data.name;
	node.setTitle(data.name);
	node.data.id = data.groupCode;
	node.data.parentId = data.parentId;
	node.data.name = data.name;
}

/** 更新组织编辑表单 */
function updateGroupFormData(data){
	var $thisForm = $("#editGroupForm");
	
	$thisForm.find("#resetEditGroupForm").click(); //重置验证样式
	
	$thisForm.find("#originalName").val(data.name);
	
	$thisForm.find("#groupId").val(data.groupId);
	$thisForm.find("#groupCode").val(data.groupCode);
	$thisForm.find("#parentId").val(data.parentId);
	$thisForm.find("#name").val(data.name);
	$thisForm.find("#remark").val(data.remark);
}

/** 重置表单 */
function clearShowEditGroupEvent(){
	var $thisForm = $("#editGroupForm");
	
	$thisForm.find("#name").val("");
	$thisForm.find("#remark").val("");
}