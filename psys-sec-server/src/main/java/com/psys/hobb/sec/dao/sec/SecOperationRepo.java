package com.psys.hobb.sec.dao.sec;

import java.util.Collection;
import java.util.List;

import com.psys.hobb.sec.model.sec.SecOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SecOperationRepo extends JpaRepository<SecOperation, Integer>{
	
	@Query(value = "from SecOperation o where o.operationCode in (?1)")
	public List<SecOperation> findByOperationCodes(Collection<String> operationCodes);

	/**
	 * 删除资源的操作及操作对应的权限
	 * @Title: deleteByResourceCode 
	 * @param resourceCode
	 * @return
	 */
	@Transactional
	@Modifying
	@Query(value = "DELETE o FROM sec_resource r"
			+ " LEFT JOIN sec_operation o ON r.resource_code = o.resource_code "
			+ " WHERE r.resource_code = ?1", nativeQuery = true)
	public int deleteByResourceCode(String resourceCode);
	
}
