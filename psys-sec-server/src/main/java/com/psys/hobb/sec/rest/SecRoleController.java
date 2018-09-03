package com.psys.hobb.sec.rest;

import com.psys.hobb.common.sys.bean.ExistResponse;
import com.psys.hobb.common.sys.bean.ServiceResponse;
import com.psys.hobb.common.sys.exception.SysRuntimeException;
import com.psys.hobb.common.sys.util.constant.SysExceptionEnum;
import com.psys.hobb.common.tree.fancytree.FancyTreeNode;
import com.psys.hobb.common.tree.fancytree.FancyTreeNodeData;
import com.psys.hobb.common.tree.fancytree.FancyTreeUtil;
import com.psys.hobb.common.tree.util.TreeUtil;
import com.psys.hobb.sec.dao.sec.SecResourceRepo;
import com.psys.hobb.sec.dao.sec.SecRoleRepo;
import com.psys.hobb.sec.model.sec.SecResource;
import com.psys.hobb.sec.model.sec.SecRole;
import com.psys.hobb.sec.service.sec.SecRoleServ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 角色controller
 * 编  号：<br/>
 * 名  称：SecRoleController<br/>
 * 描  述：<br/>
 * 完成日期：2017年12月17日 下午9:19:53<br/>
 * 编码作者：shaohj<br/>
 */
@Controller
@RequestMapping(value = "sys/role", produces="application/json;charset=UTF-8")
public class SecRoleController {
	
	private static final Logger logger = LoggerFactory.getLogger(SecRoleController.class);

	private @Autowired SecRoleServ secRoleServ;
	
	private @Autowired SecRoleRepo secRoleRepo;
	
	private @Autowired SecResourceRepo secResourceRepo;
	
	/**
	 * 新增或修改角色
	 * @Title: saveOrUpdateRole 
	 * @param role
	 * @return
	 */
	@PostMapping(value={"saveOrUpdate"})
	@ResponseBody
	public SecRole saveOrUpdateRole(@RequestBody SecRole role){
		return secRoleServ.saveOrUpdateRole(role);
	}
	
	/**
	 * 删除角色(根据codes)
	 * @Title: delByCode s
	 * @param codes
	 * @return
	 */
	@DeleteMapping(value={"del/delByCodes/{codes}"})
	@ResponseBody
	public ServiceResponse<Boolean> delByCodes(@PathVariable List<String> codes){
		SysExceptionEnum senum = SysExceptionEnum.SUCCESS;
		List<String> result = new ArrayList<String>(); 

		for(String code: codes){
			try {
				secRoleServ.deleteByCode(code);
			} catch (IllegalArgumentException | SysRuntimeException e) {
				logger.error("{}", e);
				senum = SysExceptionEnum.PART_SUCCESS;
				result.add(e.getMessage()); 
			} catch (DataAccessException e) {
				logger.error("{}", e);
				senum = SysExceptionEnum.PART_SUCCESS;
				String errorMsg = null != e.getCause().getCause() ? 
	    				e.getCause().getCause().toString() : e.getMessage();
				result.add(errorMsg); 
			} 
		}
		
		return new ServiceResponse<Boolean>(senum, result.stream().collect(Collectors.joining(",")), true);
	}
	
	/**
	 * 查询角色(根据id)
	 * @Title: getById 
	 * @param id
	 * @return
	 */
	@GetMapping(value={"query/getById/{id}"})
	@ResponseBody
	public SecRole getById(@PathVariable Integer id){
		return secRoleRepo.findById(id).get();
	}
	
	/**
	 * 查询角色(根据code)
	 * @Title: getByCode 
	 * @param code
	 * @return
	 */
	@GetMapping(value={"query/getByCode/{code}"})
	@ResponseBody
	public SecRole getById(@PathVariable String code){
		return secRoleRepo.findByRoleCode(code);
	}

	/**
	 * 分页查询角色数据主页
	 * @Title: toList
	 * @return
	 */
	@RequestMapping(path="/query/pageByName")
	@ResponseBody
	public Page<SecRole> pageByName(@RequestParam(defaultValue="") String roleName, @PageableDefault(page = 0, size = 10) Pageable page){
		Page<SecRole> pageResult = secRoleRepo.findPageByName(roleName, page);
		return pageResult;
	}

	/**
	 * 获取fancytree数据格式的所有数据字典数据
	 * @Title: listTreeAll
	 * @param roleCode
	 * @return
	 */
	@GetMapping(value={"query/resource/listTreeAll"})
	@ResponseBody
	public List<FancyTreeNode> listTreeAll(@RequestParam(required=false) final String roleCode) {
		List<SecResource> allCodeList = secResourceRepo.findAllAndOptionCode();
		
		//SecResource没有根节点,手动模拟一个节点
		SecResource rootResource = new SecResource(null, ROOT_RESOURCE_PARENT_ID,"模拟根节点", "-");
		SecResource codeTree = TreeUtil.buildByRecursive(allCodeList, rootResource);
		
		List<FancyTreeNode> nodes = new ArrayList<FancyTreeNode>();
		
		final List<SecResource> roleResourceList = new ArrayList<>();
		/* 如果有role权限,则给role所具有的权限设置复选框 */
		if(!StringUtils.isEmpty(roleCode)){
			roleResourceList.addAll(secResourceRepo.findListByRoleCode(roleCode));
		}
		
		nodes.add(FancyTreeUtil.getFancyTree(codeTree,
				resTreeCode -> {
					//生成FancyTreeNodeData节点数据,将角色拥有的资源设置为选中
					String operationCode = null;
					if(!CollectionUtils.isEmpty(resTreeCode.getSecOperations())){
						operationCode = resTreeCode.getSecOperations().get(0).getOperationCode();
					}
					FancyTreeNodeData node = new FancyTreeNodeData(resTreeCode.getResourceCode(), resTreeCode.getParentId(), resTreeCode.getName(), operationCode);
					
					//修改时,设置节点是否选中
					if(!StringUtils.isEmpty(roleCode)){
						if(!CollectionUtils.isEmpty(roleResourceList)){
							long authCount = roleResourceList.stream().filter(res -> res.getResourceCode().equals(node.getId())).count();
							
							if(authCount > 0){
								node.setSelected(true);
							}
						}
					}
					return node;
				},
				1, 0, null));
		
		FancyTreeUtil.initTreeKeyByRecursive(nodes); //设置排序key
		
		return nodes.get(0).getChildren();
	}
	
	/**
	 * 判断同级节点名称是否存在
	 * @return ExistResponse
	 */
	@RequestMapping(value={"query/existName"})
	@ResponseBody
	public ExistResponse existNameByParentId(@RequestParam(value="param") String name, @RequestParam(required=false) String originalName){
		SecRole role = secRoleRepo.findByName(name);

		if(null != role ){
			if(null != originalName && originalName.equalsIgnoreCase(role.getName())){
				//数据字典名存在,且等于原有数据字典名
				return ExistResponse.getExistSuccess();
			}else{
				//数据字典存在,且不等于原有数据字典
				return ExistResponse.getDefaultExist(); 
			}
		}
		return ExistResponse.getDefaultSuccess();
	}
	
}
