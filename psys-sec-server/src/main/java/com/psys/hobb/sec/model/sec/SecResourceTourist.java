package com.psys.hobb.sec.model.sec;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.psys.hobb.common.tree.bean.ITreeNode;
import com.psys.hobb.sec.model.BaseModel;
import org.hibernate.annotations.Cascade;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="sec_resource_tourist")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler", "fieldHandler"})
public class SecResourceTourist extends BaseModel implements  ITreeNode<SecResourceTourist> {

	@Id
    @Column(name = "resource_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer resourceId;

    @Column(name = "resource_code")
	private String resourceCode;

	private String name;

    @Column(name = "parent_id")
	private String parentId;

	private String url;

	private String remark;


	private int sort;

	private String icon;

    @Column(name = "new_window")
	private String newWindow;

	private String target;

	@Column(name = "is_sys_res", updatable = false)
	private String isSysRes;

	@JsonSerialize
	@Transient
	private List<SecResourceTourist> children;

	public SecResourceTourist() {

	}

	public SecResourceTourist(Integer resourceId, String resourceCode, String name, String parentId){
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

	public void setChildren(List<SecResourceTourist> children) {
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
	public List<SecResourceTourist> getChildren() {
		return children;
	}

	@Override
	public List<ITreeNode<SecResourceTourist>> getITreeNodeChildren() {
		if(CollectionUtils.isEmpty(children)){
			return null;
		}
		return children.stream().map(resource -> (ITreeNode<SecResourceTourist>) resource).collect(Collectors.toList());
	}

	@Override
	public boolean addChild(SecResourceTourist treeNode) {
		if(null == children){
			children = new ArrayList<>();
		}
		return children.add(treeNode);
	}

	@Override
	public SecResourceTourist getRealNode() {
		return this;
	}

}