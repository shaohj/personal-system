package com.psys.hobb.sec.model.sec;

import java.io.Serializable;
import javax.persistence.*;

import com.psys.hobb.common.time.util.CustomLocalDateTimeSerializer;
import com.psys.hobb.sec.model.BaseModel;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.psys.hobb.common.sys.util.constant.UiPathConstants;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="sec_role")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class SecRole extends BaseModel {

	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roleId;

	@Column(name = "role_code")
	private String roleCode;
	
	private String name;

	private String remark;

	//bi-directional many-to-many association to SecGroup
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@Cascade({})
	@JoinTable(
		name="sec_group_role"
		, joinColumns={
			@JoinColumn(name="role_code")
			}
		, inverseJoinColumns={
			@JoinColumn(name="group_code")
			}
		)
	private List<SecGroup> secGroups;

	//bi-directional many-to-many association to SecOperation
	@JsonIgnore
	@ManyToMany
	@Cascade({})
	@JoinTable(
		name="sec_role_operation"
		, joinColumns={
			@JoinColumn(name="role_code", referencedColumnName = "role_code")
			}
		, inverseJoinColumns={
			@JoinColumn(name="operation_code", referencedColumnName = "operation_code")
			}
		)
	private List<SecOperation> secOperations;

	//bi-directional many-to-many association to SecUser
	@JsonIgnore
	@ManyToMany(mappedBy="secRoles")
	@Cascade({})
	private List<SecUser> secUsers;
	
	/** 角色授权时,查询的角色是否被选中 */
	@Transient
	private String isChecked = "false";
	
	/** 角色新增或修改时,临时存储其对应操作权限列表 */
	@Transient
	private String operationIds;

	public SecRole() {
		
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<SecGroup> getSecGroups() {
		return secGroups;
	}

	public void setSecGroups(List<SecGroup> secGroups) {
		this.secGroups = secGroups;
	}

	public List<SecOperation> getSecOperations() {
		return secOperations;
	}

	public void setSecOperations(List<SecOperation> secOperations) {
		this.secOperations = secOperations;
	}

	public List<SecUser> getSecUsers() {
		return secUsers;
	}

	public void setSecUsers(List<SecUser> secUsers) {
		this.secUsers = secUsers;
	}
	
	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public String getOperationIds() {
		return operationIds;
	}

	public void setOperationIds(String operationIds) {
		this.operationIds = operationIds;
	}
	
}