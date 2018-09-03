var _listTreeAllResourceUrl = secContext + "/sys/resourcetourist/query/listTreeAll?access_token="+userToken,
	_saveOrUpdateResourceUrl = secContext + "/sys/resourcetourist/saveOrUpdate?access_token="+userToken,
	_delByCodeUrl = secContext + "/sys/resourcetourist/del/delByCode",
	_getByCodeUrl = secContext + "/sys/resourcetourist/query/getByCode";

var _hasDel = false;

function loadResourceList(treeId){
	ajaxGet(_listTreeAllResourceUrl, function (treeData){
		var $tree = $("#" + treeId);
		
		$tree.fancytree({
			source: treeData,
			loadChildren: function(event, data) {
				$tree.fancytree("getTree").activateKey("1_1"); //加载完成后,默认激活第一个数据节点,没有根节点,这里的key为1_1,后台处理过
			},
			loadError: function(event, data) {
				optMsgDialog("loadTree","fail");
			},
			activate: function(event, data){  //节点激活事件
				toggleDomElementDisplay("editResourceForm", true);
				var node = data.node,
					nodeData = node.data;
				if(nodeData.id && nodeData.id != ""){
					getResourceByCode(nodeData.id);
				}else{
					initAddResourceData(treeId);
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
	});
}

/** 同步增加或修改资源 */
function ajaxSubAddOrModResource(treeId){
	var $thisForm = $("#editResourceForm");
	
	var requestUrl = appendUrlRandom(_saveOrUpdateResourceUrl);
	var data = {
		resourceId : $thisForm.find("#resourceId").val(),
		resourceCode : $thisForm.find("#resourceCode").val(),
		name : $thisForm.find("#name").val(),
		parentId : $thisForm.find("#parentId").val(),
		url : $thisForm.find("#url").val(),
		remark : $thisForm.find("#remark").val(),
		newWindow : $thisForm.find("#newWindow").val(),
		sort : $thisForm.find("#sort").val()
	}
	
	var opt = $thisForm.find("#resourceId").val() == "" ? "add" : "mod";
	ajaxPost(requestUrl, data, function(respData){
		optMsgDialog(opt, "success");
		updateResourceTreeItem(treeId, respData);
		updateResourceFormData(respData);
	}, function (){
		optMsgDialog(opt, "fail");
	});
}

/** 删除资源数据 */
function delResource(treeId, resourceId){
	var requestUrl = _delByCodeUrl + "/" + resourceId + "?access_token="+userToken;
	ajaxDelete(requestUrl, {}, function(respData){
		toggleDomElementDisplay("editResourceForm");
		_hasDel = true;
		removeActivateNode(treeId);
		
		optMsgDialog("del","success");
	}, function(serviceResp){
		notifyDialog(serviceResp.errorMsg);
	});
}

/**
 * 获取并编辑数据
 * @param resourceCode
 */
function getResourceByCode(resourceCode){
	var requestUrl = _getByCodeUrl + "/" + resourceCode + "?access_token="+userToken;
	
	ajaxGet(requestUrl, function (respData){
		updateResourceFormData(respData);
	});
}

/**
 * tree添加同级节点
 * @param treeId
 */
function addResourceSiblingNode(treeId){
	var node = $("#"+treeId).fancytree("getActiveNode");
	if(node){
		var nodeData = node.data;
		if(!nodeData.id || nodeData.id == ""){
			notifyDialog(FORBID_EDIT_DIFFERENT_ADD_DATA);
		}else if(node.parent.children.length >= menuMaxNum){
			notifyDialog("该层级节点不能再添加同级节点！"); //根据业务功能判断,根节点只能有一个
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
function addResourceChildNode(treeId){
	var node = $("#"+treeId).fancytree("getActiveNode");
	if(node){
		var nodeData = node.data;
		if(!nodeData.id || nodeData.id == ""){
			notifyDialog(FORBID_EDIT_DIFFERENT_ADD_DATA);
		}else if(nodeData.level - 1 >= menuMaxLevel ){
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
function delResourceNode(treeId){
	var node = $("#"+treeId).fancytree("getActiveNode");
	if(node){
		var nodeData = node.data;
		if(!nodeData.id || nodeData.id == ""){
			Showbo.Msg.confirm(CONFIRM_DELETE_SELECT_ITEM_MSG,function(flag){
				if(flag=="yes"){
					_hasDel = true;
					toggleDomElementDisplay("editResourceForm");
					node.remove();
				}else{
					$tree.fancytree("getTree").activateKey(key);
				}
			});
		}else{
			if(node.children && node.children.length > 0){
				notifyDialog(HAS_CHILDNODE_MSG);
			}else{
				//放弃正在编辑的资源
				Showbo.Msg.confirm(CONFIRM_DELETE_SELECT_ITEM_MSG,function(flag){
					if(flag=="yes"){
						delResource(treeId,nodeData.id);
					}
				});
			}
		}
	}else{
		notifyDialog("请选择节点！");
	}
}

/** 表单显示为新增数据 */
function initAddResourceData(treeId){
	var $thisForm = $("#editResourceForm");
	
	$thisForm.find("#resetEditResourceForm").click(); //重置验证样式
	
	$thisForm.find("#originalName").val("");
	
	var $tree = $("#"+treeId).fancytree("getTree");
	var node = $tree.getActiveNode();
	
	$thisForm.find("#resourceId").val("");
	$thisForm.find("#resourceCode").val("");
	$thisForm.find("#parentId").val(node.data.parentId);
	$thisForm.find("#name").val("新增数据");
	$thisForm.find("#sort").val("");
	$thisForm.find("#remark").val("");
}

/** 更新当前编辑的树节点 */
function updateResourceTreeItem(treeId,data){
	var $tree = $("#"+treeId).fancytree("getTree");
	var node = $tree.getActiveNode();
	
	node.tooltip = data.name;
	node.setTitle(data.name);
	node.data.id = data.resourceCode;
	node.data.parentId = data.parentId;
	node.data.name = data.name;
}

/** 更新资源编辑表单 */
function updateResourceFormData(data){
	var $thisForm = $("#editResourceForm");
	
	$thisForm.find("#resetEditResourceForm").click(); //重置验证样式
	
	$thisForm.find("#originalName").val(data.name);
	
	$thisForm.find("#resourceId").val(data.resourceId);
	$thisForm.find("#resourceCode").val(data.resourceCode);
	$thisForm.find("#parentId").val(data.parentId);
	$thisForm.find("#url").val(data.url);
	$thisForm.find("#name").val(data.name);
	$thisForm.find("#sort").val(data.sort);
	$thisForm.find("#remark").val(data.remark);
	$thisForm.find("#newWindow").val(data.newWindow);
}

/** 重置表单 */
function clearShowEditResourceEvent(){
	var $thisForm = $("#editResourceForm");
	
	$thisForm.find("#name").val("");
	$thisForm.find("#url").val("");
	$thisForm.find("#sort").val("");
	
	$thisForm.find("#remark").val("");
}