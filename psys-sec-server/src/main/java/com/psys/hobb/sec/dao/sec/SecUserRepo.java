package com.psys.hobb.sec.dao.sec;

import java.util.Collection;
import java.util.List;

import com.psys.hobb.sec.model.sec.SecUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SecUserRepo extends JpaRepository<SecUser, Integer>{
	
	@Transactional
	@Modifying
	@Query("update SecUser set status = ?2 where (isAdmin is null or isAdmin <> 'Y') and userId in (?1) ")
	public int updateUsersStatus(Collection<Integer> userIds, String status);
	
	public SecUser findByUserCode(String userCode);
	
	public SecUser findByUserName(String userName);
	
	public SecUser findByUserNameAndPassword(String userName, String password);
	
//	@Query(value = "select new SecUser(u.user_id, u.user_code, u.user_name, u.real_name, u.email, u.mobile, "
//			+ " u.status, u.sex, u.birthday, u.is_admin, u.enabled_flag, u.roleNames)"
//			+ " from sec_user_view u "
//			+ " where u.user_name like %?1% or u.real_name like %?1% or u.group_code = ?2 order by ?#{#pageable}" , 
//			countQuery = "select count(*) from sec_user_view where u.user_name like %?1% or real_name like %?1% or u.group_code = ?2",
//			nativeQuery = true)
//	public Page<SecUser> findPageByName(String userName, String groupCode, Pageable page);
	
	@Query(value = "select * from sec_user_view "
			+ " where group_code = ifnull(:groupCode, group_code) and status in (:statuss) and"
			+ "  (user_name like %:userName% or real_name like %:userName%)"
			+ " order by ?#{#pageable}" , 
			countQuery = "select count(*) from sec_user_view "
					+ " where group_code = ifnull(:groupCode, group_code) and status in (:statuss) and"
					+ "  (user_name like %:userName% or real_name like %:userName%)",
			nativeQuery = true)
	public Page<Object[]> findPageByName(
            @Param("userName") String userName,
            @Param("groupCode") String groupCode,
            @Param("statuss") List<String> statuss, Pageable page);
	
	@Query("select count(u.userId) from SecUser u"
			+ " left join u.secGroup g"
			+ " where g.groupCode = ?1")
	public int countByGroupCode(String groupCode);
	
}
