package com.psys.hobb.common.tree.fancytree;

import java.io.Serializable;


/**
 * FancyTree节点数据
 * 编  号：<br/>
 * 名  称：FancyTreeNodeData<br/>
 * 描  述：<br/>
 * 完成日期：2017年12月9日 下午9:53:43<br/>
 * 编码作者：shaohj<br/>
 */
public class FancyTreeNodeData implements Serializable{
	
	private static final long serialVersionUID = 4970197817380390456L;

	/** 数据ID */
	private String id;
	
	/** 父节点数据ID */
	private String parentId;
	
	/** code-相当于key */
	private String code;
	
	/** 数据名称-相当于value */
	private String name;
	
	/** FancyTree节点层级  */
	private int level;
	
	/** 是否激活该FancyTree节点 */
	private boolean active;
	
	/** 是否选择该节点,树有复选框、单选框时使用 */
	private boolean selected;

	public FancyTreeNodeData(){
		
	}
	
	public FancyTreeNodeData(String id, String parentId, String name) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
	}

	public FancyTreeNodeData(String id, String parentId, String name, String code) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.code = code;
	}

	public FancyTreeNodeData(String id, String parentId, String code, String name, boolean active) {
		this.id = id;
		this.parentId = parentId;
		this.code = code;
		this.name = name;
		this.active = active;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
