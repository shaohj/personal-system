package com.psys.hobb.common.tree.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 树规范
 * 编  号：<br/>
 * 名  称：ITreeNode<br/>
 * 描  述：<br/>
 * 完成日期：2017年12月9日 下午8:02:13<br/>
 * 编码作者：shaohj<br/>
 * @param <T> 真实数据对象
 */
public interface ITreeNode<T> {

	/**
	 * 获取树的当前节点ID
	 * @Title: getTreeId
	 * @return
	 */
	@JsonIgnore
	String getTreeId();
	
	/**
	 * 获取树的当前父节点Id
	 * @Title: getTreeParentId 
	 * @return
	 */
	@JsonIgnore
	String getTreeParentId();

	/**
	 * 获取树的所有孩子节点
	 * @Title: getChildren 
	 * @return
	 */
	@JsonIgnore
	List<T> getChildren(); // ps: List<T extends ITreeNode>
	
	/**
	 * 获取树的所有ITreeNode孩子节点
	 * @Title: getITreeNodeChildren 
	 * @return
	 */
	@JsonIgnore
	List<ITreeNode<T>> getITreeNodeChildren(); 
	
	/**
	 * 给当前节点添加孩子节点
	 * @Title: addChild 
	 * @param treeNode
	 * @return
	 */
	boolean addChild(T treeNode);
	
	/**
	 * 获取真实的数据对象,addChild调用入参时需要
	 * @Title: getRealNode 
	 * @return
	 */
	@JsonIgnore
	T getRealNode();

}
