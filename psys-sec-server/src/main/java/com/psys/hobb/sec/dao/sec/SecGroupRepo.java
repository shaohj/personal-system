package com.psys.hobb.sec.dao.sec;

import java.util.List;

import com.psys.hobb.sec.model.sec.SecGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SecGroupRepo extends JpaRepository<SecGroup, Integer>{

	@Modifying
	@Transactional
	@Query("update SecGroup set enabledFlag='N' where (isSysRes is null or isSysRes <> 'Y') and  id = ?1")
	public int delById(Integer id);
	
	@Query("from SecGroup where enabledFlag='Y' and groupCode = ?1")
	public SecGroup findByCode(String code);
	
	@Query("from SecGroup where enabledFlag='Y'")
	public List<SecGroup> findAllByEnabled();
	
	@Query("from SecGroup where enabledFlag='Y' and parentId = ?1")
	public List<SecGroup> findListByParentId(String parentId);
	
	@Query("from SecGroup where enabledFlag='Y' and parentId = ?1 and name = ?2")
	public List<SecGroup> findListByParentIdAndName(String parentId, String name);
	
	@Query(value = "call show_group_fullname_lst(?1)", nativeQuery = true)
	public String procShowGroupFullNameLst(String code);
	
	@Query(value = "select count(groupId) from SecGroup where enabledFlag='Y' and parentId = ?1")
	public int countByParentId(String parentId);
	
}
