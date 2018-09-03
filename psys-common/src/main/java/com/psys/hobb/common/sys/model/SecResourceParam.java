package com.psys.hobb.common.sys.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.psys.hobb.common.tree.bean.ITreeNode;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler", "fieldHandler"})
public class SecResourceParam extends BaseModelParam implements  ITreeNode<SecResourceParam> {

	private Integer resourceId;

	private String resourceCode;

	private String name;

	private String parentId;

	private String url;

	private String remark;


	private int sort;

	private String icon;

	private String newWindow;

	private String target;

	private String isSysRes;

	@JsonSerialize
	private List<SecResourceParam> children;

	public SecResourceParam() {

	}

	public SecResourceParam(Integer resourceId, String resourceCode, String name, String parentId){
		this.resourceId = resourceId;
		this.resourceCode = resourceCode;
		this.name = name;
		this.parentId = parentId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getNewWindow() {
		return newWindow;
	}

	public void setNewWindow(String newWindow) {
		this.newWindow = newWindow;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getIsSysRes() {
		return isSysRes;
	}

	public void setIsSysRes(String isSysRes) {
		this.isSysRes = isSysRes;
	}

	public void setChildren(List<SecResourceParam> children) {
		this.children = children;
	}

	@Override
	public String getTreeId() {
		return resourceCode;
	}

	@Override
	public String getTreeParentId() {
		return parentId;
	}

	@Override
	public List<SecResourceParam> getChildren() {
		return children;
	}

	@Override
	public List<ITreeNode<SecResourceParam>> getITreeNodeChildren() {
		if(CollectionUtils.isEmpty(children)){
			return null;
		}
		return children.stream().map(resource -> (ITreeNode<SecResourceParam>) resource).collect(Collectors.toList());
	}

	@Override
	public boolean addChild(SecResourceParam treeNode) {
		if(null == children){
			children = new ArrayList<>();
		}
		return children.add(treeNode);
	}

	@Override
	public SecResourceParam getRealNode() {
		return this;
	}

}