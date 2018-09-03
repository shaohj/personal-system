package com.psys.hobb.sec.dao.sec;

import com.psys.hobb.sec.model.sec.SecResourceTourist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SecResourceTouristRepo extends JpaRepository<SecResourceTourist, Integer>{

	public SecResourceTourist findByResourceCode(String resourceCode);
	
	/**
	 * 删除资源
	 * @Title: deleteById 
	 * @param resourceId
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("delete from SecResourceTourist where (isSysRes is null or isSysRes <> 'Y') and resourceId = ?1")
	public int delById(Integer resourceId);

	/**
	 * 根据ParentId和Name获取单个数据
	 * @Title: findByParentIdAndName 
	 * @param parentId
	 * @param name
	 * @return
	 */
	public List<SecResourceTourist> findListByParentIdAndName(String parentId, String name);
	
	@Query("select parentId from SecResourceTourist where url = ifnull(?1, 'null')")
	public String findParentIdByUrl(String url);
	

	/**
	 * 获取父节点的所有子菜单
	 * @Title: findByParentId 
	 * @param parentId
	 * @return
	 */
	@Query("from SecResource where parentId = ifnull(?1, parentId) order by sort asc")
	public List<SecResourceTourist> findListByParentId(String parentId);
	
	/**
	 * 计算资源的子节点数量
	 * @Title: countByParentId 
	 * @param parentId
	 * @return
	 */
	@Query("select count(resourceId) from SecResource where parentId = ?1 ")
	public int countByParentId(String parentId);
	
}
