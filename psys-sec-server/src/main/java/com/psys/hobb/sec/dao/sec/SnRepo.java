package com.psys.hobb.sec.dao.sec;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import com.psys.hobb.sec.model.sec.Sn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SnRepo extends JpaRepository<Sn, Integer>{

	@Query("from Sn where enabledFlag='Y' and type = ?1 ")
	public Sn findByType(String type);
	
	@Modifying
	@Transactional
	@Query("update Sn set sn = ?2, lastUpdateTime = ?3 where id = ?1 ")
	public int updateSn(Integer id, Integer sn, LocalDateTime dateTime);

}
