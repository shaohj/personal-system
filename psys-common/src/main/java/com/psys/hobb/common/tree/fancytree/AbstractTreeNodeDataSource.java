package com.psys.hobb.common.tree.fancytree;

import java.util.List;

/**
 * 继承该抽象类,可将其数据生成FancyTreeNodeData
 * @author ShaoHanJie
 *
 */
public abstract class AbstractTreeNodeDataSource {

	protected List<AbstractTreeNodeDataSource> fancyTreeChildrens;
	
	/**
	 * 生成一个FancyTreeNodeData
	 * @return
	 */
	public abstract FancyTreeNodeData parseToFancyTreeNodeData();
	
	/**
	 * 列出所有孩子数据
	 * @return
	 */
	public abstract List<AbstractTreeNodeDataSource> listTreeChildren();
	
}
