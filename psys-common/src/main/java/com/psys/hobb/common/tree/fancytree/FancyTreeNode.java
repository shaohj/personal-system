package com.psys.hobb.common.tree.fancytree;

import java.io.Serializable;
import java.util.List;

/**
 * fancytree控件节点对象(生成需要的JSON数据)
 * 编  号：<br/>
 * 名  称：FancyTreeNode<br/>
 * 描  述：<br/>
 * 完成日期：2017年12月9日 下午9:53:13<br/>
 * 编码作者：shaohj<br/>
 */
public class FancyTreeNode implements Serializable{

	private static final long serialVersionUID = -5661856456082411488L;

	/** 排序,子节点等有用 */
	private String key;

	/** icon图表 */
	private boolean icon;

	/** 是否展开子节点.true:展开;false:收缩 */
	private boolean expanded;

	/** 是否显示文件夹icon */
	private boolean folder;

	/** 是否隐藏复选框 */
	private boolean hideCheckbox;

	/** 是否延迟加载 */
	private boolean lazy;

	/** 是否显示单选框 */
	private boolean selected;

	/** 是否显示复选框 */
	private boolean checkbox;

	/** 保存的数据 */
	private FancyTreeNodeData data;

	/** 子节点 */
	private List<FancyTreeNode> children;

	/**
	 * 默认构造方法,设置默认值
	 */
	public FancyTreeNode() {
		
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 对应html内容
	 * @return
	 */
	public String getTitle() {
		if(null != data && null != data.getName()){
			return data.getName();
		}
		return "";
	}

	/**
	 * 对应的title内容
	 * @return
	 */
	public String getTooltip() {
		if(null != data && null != data.getName()){
			return data.getName();
		}
		return "";
	}

	public boolean isIcon() {
		return icon;
	}

	public void setIcon(boolean icon) {
		this.icon = icon;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public boolean isFolder() {
		return folder;
	}

	public void setFolder(boolean folder) {
		this.folder = folder;
	}

	public boolean isHideCheckbox() {
		return hideCheckbox;
	}

	public void setHideCheckbox(boolean hideCheckbox) {
		this.hideCheckbox = hideCheckbox;
	}

	public boolean isLazy() {
		return lazy;
	}

	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

	public FancyTreeNodeData getData() {
		return data;
	}

	public void setData(FancyTreeNodeData data) {
		this.data = data;
	}

	public List<FancyTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<FancyTreeNode> children) {
		this.children = children;
	}

}


