package com.psys.hobb.sec.dao.sec;

import java.util.Collection;
import java.util.List;

import com.psys.hobb.sec.model.sec.SecRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SecRoleRepo extends JpaRepository<SecRole, Integer>{

	public SecRole findByRoleCode(String roleCode);

	@Query(value = "from SecRole r where r.roleCode in (?1)")
	public List<SecRole> findByRoleCodes(Collection<String> roleCodes);

	public SecRole findByName(String name);

	@Query(value = "from SecRole r where r.name like %?1%",
			countQuery = "select count(r) from SecRole r where r.name like %?1%")
	public Page<SecRole> findPageByName(String roleName, Pageable page);

	@Query("select r from SecRole r"
			+ " left join r.secUsers u"
			+ " where u.userCode = ?1 ")
	public List<SecRole> findRoleByUserCode(String userCode);

	/**
	 * 计算角色被用户使用的数量
	 * @Title: countRoleByUserUsing
	 * @param roleCode
	 * @return
	 */
	@Query("select count(u.userCode) from SecRole r "
			+ " left join r.secUsers u"
			+ " where r.roleCode = ?1 ")
	public int countRoleByUserUsing(String roleCode);

}
