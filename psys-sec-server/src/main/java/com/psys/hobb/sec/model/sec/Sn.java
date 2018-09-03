package com.psys.hobb.sec.model.sec;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.psys.hobb.common.time.util.CustomLocalDateTimeSerializer;
import com.psys.hobb.sec.model.BaseModel;

import java.time.LocalDateTime;

@Entity
@Table(name="sn")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Sn extends BaseModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String type;

	private String expression;
	
	private Integer sn;

	public Sn() {
		
	}
	
	public Sn(String type, String expression, Integer sn) {
		super();
		this.type = type;
		this.expression = expression;
		this.sn = sn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

}