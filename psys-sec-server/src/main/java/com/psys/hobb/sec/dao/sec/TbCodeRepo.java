package com.psys.hobb.sec.dao.sec;

import java.util.List;

import com.psys.hobb.sec.model.sec.TbCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TbCodeRepo extends JpaRepository<TbCode, Integer>{
	
	@Modifying
	@Transactional
	@Query("update TbCode set enabledFlag='N' where (isSysRes is null or isSysRes <> 'Y') and  id = ?1")
	public int delById(Integer id);
	
	@Query("select count(id) from TbCode where enabledFlag='Y' and isSysRes = 'Y' and  id = ?1")
	public int findIsSystemResource(Integer id);

	@Query("from TbCode where enabledFlag='Y' and code = ?1")
	public TbCode findByCode(String code);

	@Query("from TbCode where enabledFlag='Y' and codeType = ?1")
	public TbCode findByCodeType(String codeType);
	
	@Query("from TbCode where enabledFlag='Y' and parentId = ?1")
	public List<TbCode> findListByParentId(String parentId);
	
	@Query("from TbCode where enabledFlag='Y' and parentId = ?1 and name = ?2")
	public List<TbCode> findListByParentIdAndName(String parentId, String name);
	
	@Query(value = "call show_code_child_lst(?1)", nativeQuery = true)
	public List<TbCode> procShowCodeChildLst(String rootCode);
	
}
