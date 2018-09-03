package com.psys.hobb.sec.model.sec;

import java.io.Serializable;
import javax.persistence.*;

import com.psys.hobb.common.time.util.CustomLocalDateTimeSerializer;
import com.psys.hobb.common.tree.bean.ITreeNode;
import com.psys.hobb.sec.model.BaseModel;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="sec_group")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class SecGroup extends BaseModel implements  ITreeNode<SecGroup> {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "group_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer groupId;

	@Column(name = "group_code")
	private String groupCode;
	
	private String name;

	@Column(name = "parent_id")
	private String parentId;

	private String email;

	private String address;


	private String remark;

	@Column(name = "is_sys_res", updatable = false)
	private String isSysRes;
	
	//bi-directional many-to-many association to SecRole
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="secGroups")
	@Cascade({})
	private List<SecRole> secRoles;

	//bi-directional many-to-one association to SecUser
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="secGroup")
	@Cascade({})
	private List<SecUser> secUsers;
	
	@Transient
	private List<SecGroup> children;

	public SecGroup() {
	}
	
	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsSysRes() {
		return isSysRes;
	}

	public void setIsSysRes(String isSysRes) {
		this.isSysRes = isSysRes;
	}

	public List<SecRole> getSecRoles() {
		return secRoles;
	}

	public void setSecRoles(List<SecRole> secRoles) {
		this.secRoles = secRoles;
	}

	public List<SecUser> getSecUsers() {
		return secUsers;
	}

	public void setSecUsers(List<SecUser> secUsers) {
		this.secUsers = secUsers;
	}

	public SecUser addSecUser(SecUser secUser) {
		getSecUsers().add(secUser);
		secUser.setSecGroup(this);

		return secUser;
	}

	public SecUser removeSecUser(SecUser secUser) {
		getSecUsers().remove(secUser);
		secUser.setSecGroup(null);

		return secUser;
	}
	
	public void setChildren(List<SecGroup> children) {
		this.children = children;
	}

	@Override
	public String getTreeId() {
		return groupCode;
	}

	@Override
	public String getTreeParentId() {
		return parentId;
	}

	@Override
	public List<SecGroup> getChildren() {
		return children;
	}

	@Override
	public List<ITreeNode<SecGroup>> getITreeNodeChildren() {
		if(CollectionUtils.isEmpty(children)){
			return null;
		}
		return children.stream().map(code -> (ITreeNode<SecGroup>) code).collect(Collectors.toList());
	}

	@Override
	public boolean addChild(SecGroup treeNode) {
		if(null == children){
			children = new ArrayList<>();
		}
		return children.add(treeNode);
	}

	@Override
	public SecGroup getRealNode() {
		return this;
	}

}