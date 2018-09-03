package com.psys.hobb.sec.model.sec;

import java.io.Serializable;

import com.psys.hobb.common.time.util.CustomLocalDateTimeDeSerializer;
import com.psys.hobb.common.time.util.CustomLocalDateTimeSerializer;
import com.psys.hobb.sec.model.BaseModel;
import com.psys.hobb.sec.service.sec.impl.CodeCacheService;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.CODE_USER_STATE;

@Entity
@Table(name="sec_user")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class SecUser extends BaseModel {
	
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	@Column(name = "user_code")
	private String userCode;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "real_name")
	private String realName;

	private String password;

	private String email;

	
	private String mobile;

	private String status;

	private String sex;

	@JsonDeserialize(using = CustomLocalDateTimeDeSerializer.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	private LocalDateTime birthday;

	@Column(name = "is_admin", updatable = false)
	private String isAdmin;

	//bi-directional many-to-one association to SecGroup
	@ManyToOne(fetch = FetchType.LAZY)
	@Cascade({})
	@JoinColumn(name="group_code", referencedColumnName="group_code")
	private SecGroup secGroup;

	//bi-directional many-to-many association to SecRole
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@Cascade({})
	@JoinTable(
		name="sec_user_role"
		, joinColumns={
			@JoinColumn(name="user_code", referencedColumnName="user_code")
			}
		, inverseJoinColumns={
			@JoinColumn(name="role_code", referencedColumnName="role_code")
			}
		)
	private List<SecRole> secRoles;
	
	/** 查询列表显示的角色名*/
	@Transient
	private String roleNames;
	
	/** 查询列表显示的组织名,可能是全组织名 */
	@Transient
	private String groupNames;
	
	/** 角色分配时,临时存储其对应角色code列表 */
	@Transient
	private List<String> userRoleCodes;

	public SecUser(){
		
	}
	
	public SecUser(Integer userId) {
		super();
		this.userId = userId;
	}

	public SecUser(Integer userId, String userCode, String userName, String realName, String email, String mobile,
			String status, String sex, LocalDateTime birthday, String isAdmin, String enabledFlag, String roleNames) {
		super();
		this.userId = userId;
		this.userCode = userCode;
		this.userName = userName;
		this.realName = realName;
		this.email = email;
		this.mobile = mobile;
		this.status = status;
		this.sex = sex;
		this.birthday = birthday;
		this.isAdmin = isAdmin;
		this.enabledFlag = enabledFlag;
		this.roleNames = roleNames;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public LocalDateTime getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDateTime birthday) {
		this.birthday = birthday;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public SecGroup getSecGroup() {
		return secGroup;
	}

	public void setSecGroup(SecGroup secGroup) {
		this.secGroup = secGroup;
	}

	public List<SecRole> getSecRoles() {
		return secRoles;
	}

	public void setSecRoles(List<SecRole> secRoles) {
		this.secRoles = secRoles;
	}
	
	public String getShowStatus(){
		List<TbCode> userStateList = CodeCacheService.getCodesByCodeType(CODE_USER_STATE);

		if(!CollectionUtils.isEmpty(userStateList)){
			return userStateList.stream()
					.filter(code -> status.equals(code.getCodeType()))
					.map(TbCode::getName).findFirst().orElse("");
		}
		return "";
	}

	public List<String> getUserRoleCodes() {
		return userRoleCodes;
	}

	public void setUserRoleCodes(List<String> userRoleCodes) {
		this.userRoleCodes = userRoleCodes;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getGroupNames() {
		return groupNames;
	}

	public void setGroupNames(String groupNames) {
		this.groupNames = groupNames;
	}
	
}