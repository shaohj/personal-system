package com.psys.hobb.sec.rest;

import com.psys.hobb.common.sys.bean.ExistResponse;
import com.psys.hobb.common.tree.fancytree.FancyTreeNode;
import com.psys.hobb.common.tree.fancytree.FancyTreeNodeData;
import com.psys.hobb.common.tree.fancytree.FancyTreeUtil;
import com.psys.hobb.common.tree.util.TreeUtil;
import com.psys.hobb.sec.dao.sec.SecGroupRepo;
import com.psys.hobb.sec.model.sec.SecGroup;
import com.psys.hobb.sec.service.sec.SecGroupServ;
import com.psys.hobb.sec.service.sec.SnServ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 组织Controller
 * 编  号：<br/>
 * 名  称：SecGroupController<br/>
 * 描  述：<br/>
 * 完成日期：2017年12月17日 下午3:25:29<br/>
 * 编码作者：shaohj<br/>
 */
@Controller
@RequestMapping(value = "sys/group", produces="application/json;charset=UTF-8")
public class SecGroupController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SecGroupController.class);

	private @Autowired SecGroupRepo secGroupRepo;
	
	private @Autowired SecGroupServ secGroupServ;
	
	private @Autowired SnServ snServ;
	
	/**
	 * 新增或修改组织
	 * @Title: saveOrUpdateGroup 
	 * @param group
	 * @return
	 */
	@PostMapping(value={"saveOrUpdate"})
	@ResponseBody
	public SecGroup saveOrUpdateGroup(@RequestBody SecGroup group){
		if(null == group.getGroupId() || 0 == group.getGroupId()){
			group.setGroupId(null);
			group.setGroupCode(snServ.generateCode(GROUP_SN_TYPE, true));
		}
		
		group.setEnabledFlag(ENABLED_FLAG);
		
		return secGroupRepo.save(group);
	}
	
	/**
	 * 删除组织(根据Code)
	 * @Title: delByCode 
	 * @param code
	 * @return
	 */
	@DeleteMapping(value={"del/delByCode/{code}"})
	@ResponseBody
	public boolean delByCode(@PathVariable String code){
		return secGroupServ.deleteByCode(code);
	}
	
	/**
	 * 查询组织(根据id)
	 * @Title: getById 
	 * @param id
	 * @return
	 */
	@GetMapping(value={"query/getById/{id}"})
	@ResponseBody
	public SecGroup getById(@PathVariable Integer id){
		return secGroupRepo.findById(id).get();
	}
	
	/**
	 * 查询组织(根据code)
	 * @Title: getByCode 
	 * @param code
	 * @return
	 */
	@GetMapping(value={"query/getByCode/{code}"})
	@ResponseBody
	public SecGroup getByCode(@PathVariable String code){
		return secGroupRepo.findByCode(code);
	}
	
	/**
	 * 获取fancytree数据格式的所有组织数据
	 * @Title: listTreeAll 
	 * @param activeCode
	 * @return
	 */
	@GetMapping(value={"query/listTreeAll"})
	@ResponseBody
	public List<FancyTreeNode> listTreeAll(@RequestParam(required=false) String activeCode) {
		List<SecGroup> allGroupList = secGroupRepo.findAllByEnabled();

		SecGroup groupTree = TreeUtil.buildByRecursiveByHasRootTree(allGroupList,
				treeNode -> ROOT_GROUP_PARENT_CODE.equals(treeNode.getTreeParentId()));

		List<FancyTreeNode> nodes = new ArrayList<FancyTreeNode>();

		nodes.add(FancyTreeUtil.getFancyTree(groupTree,
				groupTreeCode -> new FancyTreeNodeData(groupTreeCode.getGroupCode(), groupTreeCode.getParentId(), groupTreeCode.getName()),
				1, 0, activeCode));

		FancyTreeUtil.initTreeKeyByRecursive(nodes); //设置排序key
		return nodes;
	}
	
	/**
	 * 判断同级节点名称是否存在
	 * @return
	 */
	@RequestMapping(value={"query/existNameByParentId"})
	@ResponseBody
	public ExistResponse existNameByParentId(@RequestParam(value="param") String name,
			@RequestParam String parentId, @RequestParam(required=false) String originalName){
		List<SecGroup> groupList = secGroupRepo.findListByParentIdAndName(parentId, name);

		if(null != groupList && !groupList.isEmpty()){
			if(groupList.size() > 1){
				//数据不正常
				return ExistResponse.getDefaultErrorExist();
			}
			SecGroup code = groupList.get(0);
			if(code != null && name.equalsIgnoreCase(code.getName())){
				if(null != originalName && originalName.equalsIgnoreCase(code.getName())){
					//数据字典名存在,且等于原有数据字典名
					return ExistResponse.getExistSuccess(); 
				}else{
					//数据字典存在,且不等于原有数据字典
					return ExistResponse.getDefaultExist(); 
				}
			}
		}
		return ExistResponse.getDefaultSuccess();
	}
	
}
