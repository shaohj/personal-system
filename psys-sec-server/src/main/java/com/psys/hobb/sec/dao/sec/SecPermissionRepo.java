package com.psys.hobb.sec.dao.sec;

import com.psys.hobb.sec.model.sec.SecPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SecPermissionRepo extends JpaRepository<SecPermission, Integer>{

//	@Transactional
//	@Modifying
//	@Query("update SecResource r "
//			+ " join r.secOperations o "
//			+ " join o.secPermissions p "
//			+ " set p.address = r.url "
//			+ " where r.resourceId = ?1")
	
	
//	@Transactional
//	@Modifying
//	@Query("update SecPermission p set p.address = ?2 where p.permissionId in "
//			+ " (select m.tid from "
//			+ " 	(select p.permissionId as tid from SecResource r left join r.secOperations o left join o.secPermissions p where r.resourceId = ?1) m"
//			+ " )")
	
	@Transactional
	@Modifying
	@Query(value = "update sec_resource r "
			+ " left join sec_operation o on r.resource_code = o.resource_code "
			+ " left join sec_permission p on p.operation_code = o.operation_code "
			+ " set p.name = ?2, p.address = ifnull(?3, null) "
			+ " where r.resource_id = ?1", nativeQuery = true)
	public int updatePermissionAddress(Integer resourceId, String name, String address);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE p FROM sec_resource r"
			+ " LEFT JOIN sec_operation o ON r.resource_code = o.resource_code "
			+ " LEFT JOIN sec_permission p ON p.operation_code = o.operation_code "
			+ " WHERE r.resource_code = ?1", nativeQuery = true)
	public int deleteByResourceCode(String resourceCode);
	
}
