package com.psys.hobb.sec.dao.auth;

import com.psys.hobb.sec.model.auth.OauthAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthAccessTokenRepo extends JpaRepository<OauthAccessToken, Integer>{

	@Query("select userName from OauthAccessToken where tokenId = ?1 ")
	public String findByTokenId(String tokenId);
	
}
