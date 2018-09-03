package com.psys.hobb.common.tree.fancytree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.psys.hobb.common.tree.bean.ITreeNode;
import org.springframework.util.StringUtils;

/**
 * FancyTree工具类
 * @author ShaoHanJie
 *
 */
public class FancyTreeUtil {
	
	/**
	 * 根据upKey计算生成下一个key,key格式为1_2_3,每小节不限长度
	 * @param upKey 上一个key
	 * @return upKey后缀加1的数值
	 */
	public static String generateKey(String upKey){
		if(StringUtils.isEmpty(upKey)){
			return "1";
		}
		
		/* 分别找到前缀和后缀,将后缀加1后与前缀拼接即可 */
		String prefix = "";
		String suffix = upKey;
		
		if(upKey.lastIndexOf("_") > 0){
			prefix = upKey.substring(0,upKey.lastIndexOf("_")+1);
			suffix = upKey.substring(upKey.lastIndexOf("_")+1);
		}
		
		if("".equals(suffix)){
			suffix = "1";
		}else{
			suffix = String.valueOf(Integer.parseInt(suffix) + 1);
		}
		
		return prefix + suffix;
	}

	/**
	 * 遍历所有节点,并给所有数据设置好key
	 * 
	 * @param nodes
	 */
	public static void initTreeKeyByRecursive(List<FancyTreeNode> nodes) {
		String key = null;
		for (FancyTreeNode obj : nodes) {
			key = generateKey(key);
			recursiveSetNodeSortKey(obj, key);
		}
	}
	
	/**
	 * 递归设置排序key
	 * 
	 * @param node
	 * @param key
	 */
	public static void recursiveSetNodeSortKey(FancyTreeNode node, String key) {
		node.setKey(key);

		if (node.getChildren() != null && node.getChildren().size() > 0) {
			key = key + "_";
			for (FancyTreeNode child : node.getChildren()) {
				key = generateKey(key);
				recursiveSetNodeSortKey(child, key);
			}
		}
	}
	
	/**
	 * 遍历当前数据及子节点,转换为FancyTreeNode对象
	 * @Title: getFancyTree 
	 * @param source 
	 * @param adapter
	 * @param lv 当前层级
	 * @param exMaxLv 展开的最大层级,为0表示不限级
	 * @param activeId 激活的节点ID
	 * @return
	 */
	public static <T> FancyTreeNode getFancyTree(ITreeNode<T> source,
			Function<T, FancyTreeNodeData> adapter, 
			int lv, int exMaxLv, String activeId){
		FancyTreeNode node = new FancyTreeNode();
		FancyTreeNodeData data = adapter.apply(source.getRealNode());
		
		data.setLevel(lv);

		if(!StringUtils.isEmpty(activeId) && activeId.equals(data.getId())){
			data.setActive(true);
		}
		
		if(data.isSelected()){
			node.setSelected(true);
		}
		
		node.setData(data);
		
		if(source.getChildren() != null  && source.getChildren().size() >0){
			if(exMaxLv == 0 || lv < exMaxLv){
				node.setExpanded(true); //设置展开子节点
			}
			
			lv++;
			
			List<FancyTreeNode> children = new ArrayList<FancyTreeNode>();
			for(ITreeNode<T> child: source.getITreeNodeChildren()){
				children.add(getFancyTree(child, adapter, lv, exMaxLv, activeId));
			}
			
			node.setChildren(children);
		}
		
		return node;
	}
	
}
