package com.psys.hobb.sec.model.sec;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@Entity
@Table(name="sec_operation")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class SecOperation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "operation_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer operationId;

	@Column(name = "operation_code")
	private String operationCode;
	
	private String name;

	private String remark;

	//bi-directional many-to-one association to SecResource
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@Cascade({})
	@JoinColumn(name="resource_code", referencedColumnName="resource_code")
	private SecResource secResource;

	//bi-directional many-to-one association to SecPermission
	@JsonIgnore
	@OneToMany(mappedBy="secOperation", fetch = FetchType.LAZY)
	@Cascade({})
	private List<SecPermission> secPermissions;

	//bi-directional many-to-many association to SecRole
	@JsonIgnore
	@ManyToMany(mappedBy = "secOperations", fetch = FetchType.LAZY)
	@Cascade({})
	private List<SecRole> secRoles;

	public SecOperation() {
		
	}
	
	public SecOperation(Integer operationId, String operationCode, String name, String remark, SecResource secResource) {
		this.operationId = operationId;
		this.operationCode = operationCode;
		this.name = name;
		this.remark = remark;
		this.secResource = secResource;
	}

	public SecOperation(String operationCode){
		this.operationCode = operationCode;
	}

	public Integer getOperationId() {
		return operationId;
	}

	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
	}

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public SecResource getSecResource() {
		return this.secResource;
	}

	public void setSecResource(SecResource secResource) {
		this.secResource = secResource;
	}

	public List<SecPermission> getSecPermissions() {
		return this.secPermissions;
	}

	public void setSecPermissions(List<SecPermission> secPermissions) {
		this.secPermissions = secPermissions;
	}

	public SecPermission addSecPermission(SecPermission secPermission) {
		getSecPermissions().add(secPermission);
		secPermission.setSecOperation(this);

		return secPermission;
	}

	public SecPermission removeSecPermission(SecPermission secPermission) {
		getSecPermissions().remove(secPermission);
		secPermission.setSecOperation(null);

		return secPermission;
	}

	public List<SecRole> getSecRoles() {
		return this.secRoles;
	}

	public void setSecRoles(List<SecRole> secRoles) {
		this.secRoles = secRoles;
	}

}