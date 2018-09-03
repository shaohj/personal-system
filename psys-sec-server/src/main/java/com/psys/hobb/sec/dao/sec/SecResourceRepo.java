package com.psys.hobb.sec.dao.sec;

import java.util.List;

import com.psys.hobb.sec.model.sec.SecResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SecResourceRepo extends JpaRepository<SecResource, Integer>{

	public SecResource findByResourceCode(String resourceCode);
	
	/**
	 * 删除资源
	 * @Title: deleteById 
	 * @param resourceId
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("delete from SecResource where (isSysRes is null or isSysRes <> 'Y') and resourceId = ?1")
	public int delById(Integer resourceId);

	/**
	 * 根据ParentId和Name获取单个数据
	 * @Title: findByParentIdAndName 
	 * @param parentId
	 * @param name
	 * @return
	 */
	public List<SecResource> findListByParentIdAndName(String parentId, String name);
	
	@Query("select parentId from SecResource where url = ifnull(?1, 'null')")
	public String findParentIdByUrl(String url);
	
	/**
	 * 获取用户的资源列表,可根据资源的parentId过滤数据
	 * @Title: findListByUserIdAndParentId 
	 * @param userId 
	 * @param parentId 该值为null时,不使用此条件过滤数据
	 * @return
	 */
	@Query("select new SecResource(res.resourceId, res.resourceCode, res.name, res.parentId, res.url, "
			+ " res.remark, res.sort, res.icon, res.newWindow, res.target,"
			+ " o.operationCode)"
			+ " from SecUser u"
			+ " left join u.secRoles ro"
			+ " left join ro.secOperations o"
			+ " left join o.secResource res"
			+ " left join o.secPermissions p"
			+ " where u.userId = ?1 and ifnull(p.address, '') = ifnull(res.url, '') and res.parentId = ifnull(?2, res.parentId)"
			+ " order by res.sort asc")
	public List<SecResource> findListByUserIdAndParentId(Integer userId, String parentId);
	
	/**
	 * 查询其所有资源列表,带上资源的操作权限
	 * @Title: findListByRoleCode 
	 * @return
	 */
	@Query("select distinct new SecResource(res.resourceId, res.resourceCode, res.name, res.parentId, res.url, "
			+ " res.remark, res.sort, res.icon, res.newWindow, res.target,"
			+ " o.operationCode)"
			+ " from SecResource res"
			+ " left join res.secOperations o"
			+ " left join o.secRoles r"
			+ " order by res.sort asc")
	public List<SecResource> findAllAndOptionCode();
	
	/**
	 * 根据角色code查询其资源列表
	 * @Title: findListByRoleCode 
	 * @param roleCode 角色code
	 * @return 
	 */
	@Query("select res "
			+ " from SecResource res"
			+ " left join res.secOperations o"
			+ " left join o.secRoles r"
			+ " where r.roleCode = ?1"
			+ " order by res.sort asc")
	public List<SecResource> findListByRoleCode(String roleCode);
	
	/**
	 * 获取父节点的所有子菜单
	 * @Title: findByParentId 
	 * @param parentId
	 * @return
	 */
	@Query("from SecResource where parentId = ifnull(?1, parentId) order by sort asc")
	public List<SecResource> findListByParentId(String parentId);
	
	/**
	 * 计算资源的子节点数量
	 * @Title: countByParentId 
	 * @param parentId
	 * @return
	 */
	@Query("select count(resourceId) from SecResource where parentId = ?1 ")
	public int countByParentId(String parentId);
	
	/**
	 * 计算资源被角色使用的数量
	 * @Title: countByRoleUsingByHql 
	 * @return
	 */
	@Query("select count(ro.roleId) from SecResource r "
			+ " left join r.secOperations o"
			+ " left join o.secRoles ro"
			+ " where o.secResource.resourceCode = ?1 ")
	public int countByRoleUsing(String resourceCode);
	
}
