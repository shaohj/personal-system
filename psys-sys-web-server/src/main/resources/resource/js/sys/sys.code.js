var _listTreeAllCodeUrl = secContext + "/sys/code/query/listTreeAll?access_token="+userToken,
	_saveOrUpdateCodeUrl = secContext + "/sys/code/saveOrUpdate?access_token="+userToken,
	_delByIdUrl = secContext + "/sys/code/del/delById",
	_getByIdUrl = secContext + "/sys/code/query/getById";

var _hasDel = false;

function loadCodeList(treeId){
	ajaxGet(_listTreeAllCodeUrl, function (treeData){
		var $tree = $("#"+treeId);
		$tree.fancytree({
			source: treeData,
			loadChildren: function(event, data) {
				$tree.fancytree("getTree").activateKey("1"); //加载完成后,默认激活第一个数据节点
			},
			loadError: function(event, data) {
				optMsgDialog("loadTree", "fail");
			},
			activate: function(event, data){  //节点激活事件
				toggleDomElementDisplay("editCodeForm",true);
				var node = data.node,
					nodeData = node.data;
				if(nodeData.id && nodeData.id != ""){
					getCodeById(nodeData.id); //显示修改节点信息
				}else{
					initAddCodeData(treeId); //显示新增节点信息
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

/** 同步增加或修改数据字典 */
function ajaxSubAddOrModCode(treeId){
	var $thisForm = $("#editCodeForm");
	
	var requestUrl = appendUrlRandom(_saveOrUpdateCodeUrl);
	var data = {
			id : $thisForm.find("#codeId").val(),
			code : $thisForm.find("#code").val(),
			parentId : $thisForm.find("#parentId").val(),
			codeType : $thisForm.find("#codeType").val(),
			name : $thisForm.find("#name").val(),
			value : $thisForm.find("#value").val(),
			no : $thisForm.find("#no").val(),
			remark : $thisForm.find("#remark").val(),
			type : "system"
		}
	
	var opt = $thisForm.find("#codeId").val() == "" ? "add" : "mod";
	ajaxPost(requestUrl, data, function(respData){
		optMsgDialog(opt, "success");
		updateCodeTreeItem(treeId, respData);
		updateCodeFormData(respData);
	}, function (){
		optMsgDialog(opt, "fail");
	});
}

/** 删除数据字典数据 */
function delCode(treeId, codeId){
	//同步删除资源
	var requestUrl = _delByIdUrl + "/" + codeId + "?access_token="+userToken;
	ajaxDelete(requestUrl, {}, function(respData){
		toggleDomElementDisplay("editCodeForm");
		_hasDel = true;
		removeActivateNode(treeId);
		
		optMsgDialog("del","success");
	}, function(serviceResp){
		notifyDialog(serviceResp.errorMsg);
	});
}

/**
 * 编辑数据字典数据
 */
function getCodeById(codeId){
	var requestUrl = _getByIdUrl + "/" + codeId + "?access_token="+userToken;
	
	ajaxGet(requestUrl, function (respData){
		updateCodeFormData(respData);
	});
}

/**
 * tree添加同级节点
 * @param treeId
 */
function addCodeSiblingNode(treeId){
	var node = $("#"+treeId).fancytree("getActiveNode");
	if(node){
		var nodeData = node.data;
		if(!nodeData.id || nodeData.id == ""){
			notifyDialog(FORBID_EDIT_DIFFERENT_ADD_DATA);
		}else if(nodeData.parentId == "-1"){
            notifyDialog("系统编码不能添加同级节点！");
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
function addCodeChildNode(treeId){
	var node = $("#"+treeId).fancytree("getActiveNode");
	if(node){
		var nodeData = node.data;
		if(!nodeData.id || nodeData.id == ""){
			notifyDialog(FORBID_EDIT_DIFFERENT_ADD_DATA);
		}else if(nodeData.level >= codeMaxLevel ){
			notifyDialog("该层级节点不能再添加子节点！");
		}else{
			addChildNode(treeId, true);
		}
	}else{
		notifyDialog("请选择节点！");
	}
}

/**
 * tree删除节点
 * @param treeId
 */
function delCodeNode(treeId){
	var node = $("#"+treeId).fancytree("getActiveNode");
	if(node){
		var nodeData = node.data;
		if(!nodeData.id || nodeData.id == ""){
			Showbo.Msg.confirm(CONFIRM_DELETE_SELECT_ITEM_MSG,function(flag){
				if(flag=="yes"){
					_hasDel = true;
					toggleDomElementDisplay("editCodeForm");
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
						delCode(treeId, nodeData.id);
					}
				});
			}
		}
	}else{
		notifyDialog("请选择节点！");
	}
}

/** 表单显示为新增数据 */
function initAddCodeData(treeId){
	var $thisForm = $("#editCodeForm");
	
	$thisForm.find("#resetEditCodeForm").click(); //重置验证样式
	
	var $tree = $("#"+treeId).fancytree("getTree");
	var node = $tree.getActiveNode();
	
	$thisForm.find("#originalCodeType").val("");
	$thisForm.find("#originalName").val("");
	
	$thisForm.find("#codeId").val(""); 
	$thisForm.find("#code").val("");
	$thisForm.find("#parentId").val(node.data.parentId);
	$thisForm.find("#codeType").val("");
	$thisForm.find("#name").val("新增数据");
	$thisForm.find("#value").val("");
	$thisForm.find("#no").val("1");
	$thisForm.find("#remark").val("");
}

/** 更新code编辑表单 */
function updateCodeFormData(data){
	var $thisForm = $("#editCodeForm");
	
	$thisForm.find("#resetEditCodeForm").click();  //重置验证样式
	
	$thisForm.find("#originalCodeType").val(data.codeType);
	$thisForm.find("#originalName").val(data.name);
	
	$thisForm.find("#codeId").val(data.id);
	$thisForm.find("#code").val(data.code);
	$thisForm.find("#parentId").val(data.parentId);
	$thisForm.find("#codeType").val(data.codeType);
	$thisForm.find("#name").val(data.name);
	$thisForm.find("#value").val(data.value);
	$thisForm.find("#no").val(data.no);
	$thisForm.find("#remark").val(data.remark);
}

/** 重置表单 */
function clearShowEditCodeEvent(){
	var $thisForm = $("#editCodeForm");
	
	$thisForm.find("#codeType").val("");
	$thisForm.find("#name").val("");
	$thisForm.find("#value").val("");
	$thisForm.find("#no").val("1");
	$thisForm.find("#remark").val("");
}

/** 更新当前编辑的树节点 */
function updateCodeTreeItem(treeId, data){
	var $tree = $("#"+treeId).fancytree("getTree");
	var node = $tree.getActiveNode();
	
	node.tooltip = data.name;
	node.setTitle(data.name);
	node.data.id = data.id;
	node.data.parentId = data.parentId;
	node.data.name = data.name;
	node.data.code = data.code;
}