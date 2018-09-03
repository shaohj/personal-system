package com.psys.hobb.sec.model.sec;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="sec_permission")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class SecPermission implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "permission_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer permissionId;

	@Column(name = "permission_code")
	private String permissionCode;
	
	private String name;

	private String address;

	private String remark;

	//bi-directional many-to-one association to SecOperation
	@ManyToOne
	@Cascade({})
	@JoinColumn(name="operation_code", referencedColumnName="operation_code")
	private SecOperation secOperation;

	public SecPermission() {
		
	}
	
	public SecPermission(Integer permissionId, String permissionCode, String name, String address, String remark, SecOperation secOperation) {
		this.permissionId = permissionId;
		this.permissionCode = permissionCode;
		this.name = name;
		this.address = address;
		this.remark = remark;
		this.secOperation = secOperation;
	}

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public SecOperation getSecOperation() {
		return secOperation;
	}

	public void setSecOperation(SecOperation secOperation) {
		this.secOperation = secOperation;
	}

}