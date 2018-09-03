package com.psys.hobb.sec.model.sec;

import java.io.Serializable;

import javax.persistence.*;

import com.psys.hobb.common.time.util.CustomLocalDateTimeSerializer;
import com.psys.hobb.common.tree.bean.ITreeNode;
import com.psys.hobb.sec.model.BaseModel;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="tb_code")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class TbCode extends BaseModel implements ITreeNode<TbCode> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String code;

	@Column(name = "parent_id")
	private String parentId;
	
	private String type;

	@Column(name = "code_type")
	private String codeType;
	
	
	private String name;
	
	private String value;
	
	private Integer no;

	@Column(name = "is_leaf")
	private String isLeaf;
	
	private String remark;

	@Column(name = "is_sys_res", updatable = false)
	private String isSysRes;

	@Transient
	private List<TbCode> children;
	
	public TbCode(){
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
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

	public void setChildren(List<TbCode> children) {
		this.children = children;
	}

	@Override
	public String getTreeId() {
		return code;
	}

	@Override
	public String getTreeParentId() {
		return parentId;
	}

	@Override
	public List<TbCode> getChildren() {
		return children;
	}

	@Override
	public List<ITreeNode<TbCode>> getITreeNodeChildren() {
		if(CollectionUtils.isEmpty(children)){
			return null;
		}
		return children.stream().map(code -> (ITreeNode<TbCode>) code).collect(Collectors.toList());
	}

	@Override
	public boolean addChild(TbCode treeNode) {
		if(null == children){
			children = new ArrayList<>();
		}
		return children.add(treeNode);
	}

	@Override
	public TbCode getRealNode() {
		return this;
	}

}