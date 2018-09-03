package com.psys.hobb.sec.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.psys.hobb.common.time.util.CustomLocalDateTimeSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="oauth_access_token")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class OauthAccessToken implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "token_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String tokenId;

	@Column(name = "user_name")
	private String userName;

	public OauthAccessToken() {
		
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}