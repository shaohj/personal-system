/**
 * 给当前选中的节点添加兄弟节点
 * @param treeId
 * @returns {Boolean}
 */
function addSiblingNode(treeId){
	var $tree = $("#"+treeId);
	if(!$tree){
		return false; //tree不存在
	}
	$tree = $tree.fancytree("getTree");
	
	var node = $tree.getActiveNode();
	var newNode = {title: "新增数据",icon:false};
	var newSibling = node.appendSibling(newNode);
		newSibling.data.parentId = node.data.parentId;	//设置父节点ID
		newSibling.data.level = node.data.level; //设置节点层数
		
	$tree.activateKey(newSibling.key);
}

/**
 * 给当前选中的节点添加子节点
 * @param treeId
 * @param codeIsTreeId true:treeId使用node.data.code作为主键;false:treeId使用node.data.id作为主键
 * @returns {Boolean}
 */
function addChildNode(treeId, codeIsTreeId){
	var $tree = $("#"+treeId);
	if(!$tree){
		return false; //tree不存在
	}
	$tree = $tree.fancytree("getTree");
	
	var node = $tree.getActiveNode();
	var newNode = {title: "新增数据",icon:false};
	var newChild = node.addChildren(newNode);
		newChild.data.parentId = codeIsTreeId ? node.data.code : node.data.id;	//设置父节点ID
		newChild.data.level = Number(node.data.level) + 1; //设置节点层数
	
	node.setExpanded(true); //展开
	$tree.activateKey(newChild.key);
}

/**
 * 删除当前选中的节点
 * @param treeId
 */
function removeActivateNode(treeId){
	var $tree = $("#"+treeId);
	if(!$tree){
		return false; //tree不存在
	}
	$tree = $tree.fancytree("getTree");
	$tree.getActiveNode().remove();
}

/**
 * 获取当前选中节点的data
 * @param treeId
 * @returns {Array}
 */
function getAllSelectNodeData(treeId){
	var arr = new Array();
	var tree = $("#"+treeId).fancytree("getTree"),
	selNodes = tree.getSelectedNodes();
	selNodes.forEach(function(node) {
		recursionAddOption(treeId,node,arr);
	});
	
	return arr;
}

function recursionAddOption(treeId,node,arr){
	if(node.data){
		if(arr.indexOf(node.data) < 0){
			arr.push(node.data); //添加当前值
		}
	}
	
	var parent = node.parent;
	if(parent && parent.title != "root"){ //递归
		recursionAddOption(treeId,parent,arr);
	}
}
/**
 * 遍历树:根据发现需求激活的一个node.data.active=true元素
 * @param treeId
 */
function singleDataActiveKey(treeId){
	var childNode = $("#"+treeId).fancytree("getRootNode").children;
	var key ;
	for(var i=0;i<childNode.length;i++){
		key = recursionSingleDataActiveKey(childNode[i]);
		if(key && "" != key){
			break;
		}
	}
	return key;
}

/**
 * 递归遍历树:根据发现需求激活的一个node.data.active=true元素
 * @param node
 */
function recursionSingleDataActiveKey(node){
	var key = "",
		nodeChild = node.children,
		nodeData = node.data;
	
	if(nodeData.active && nodeData.active == true){
		key = node.key;
	}else if(nodeChild && nodeChild.length>0){
		for(var i=0;i<nodeChild.length;i++){
			key = recursionSingleDataActiveKey(nodeChild[i]);
			if(key && "" != key){
				break;
			}
		}
	}
	return key;
}

/**
 * 遍历树:获取所有层级Text
 * @param treeId
 */
function getTreeName(treeId){
	var node = $("#"+treeId).fancytree("getTree").getActiveNode();
	var nodeText = "";
	if(node.data.name && "" != node.data.name){
		nodeText = node.data.name;
	}
	nodeText = recursionGetTreeName(node,nodeText);
	return nodeText;
}

/**
 * 递归遍历树:根据发现需求激活的一个node.data.active=true元素
 * @param node
 */
function recursionGetTreeName(node,nodeText){
	var parentNode = node.parent;
	
	if(parentNode && "" != parentNode && parentNode != null && parentNode != "null"){
		if(parentNode.data.name && "" != parentNode.data.name){
			nodeText = parentNode.data.name + "/"+nodeText;
		}
		nodeText = recursionGetTreeName(parentNode,nodeText);
	}
	return nodeText;
}