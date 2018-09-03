package com.psys.hobb.sec.rest;

import com.psys.hobb.common.sys.bean.ExistResponse;
import com.psys.hobb.common.sys.util.AssertUtil;
import com.psys.hobb.common.tree.fancytree.FancyTreeNode;
import com.psys.hobb.common.tree.fancytree.FancyTreeNodeData;
import com.psys.hobb.common.tree.fancytree.FancyTreeUtil;
import com.psys.hobb.common.tree.util.TreeUtil;
import com.psys.hobb.sec.dao.sec.TbCodeRepo;
import com.psys.hobb.sec.event.CodeCacheRefreshEvent;
import com.psys.hobb.sec.model.sec.TbCode;
import com.psys.hobb.sec.service.sec.SnServ;
import com.psys.hobb.sec.service.sec.TbCodeServ;
import com.psys.hobb.sec.service.sec.impl.CodeCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典Controller
 * 编  号：<br/>
 * 名  称：TbCodeController<br/>
 * 描  述：<br/>
 * 完成日期：2017年12月12日 下午8:21:54<br/>
 * 编码作者：shaohj<br/>
 */
@Controller
@RequestMapping(value = "sys/code", produces="application/json;charset=UTF-8")
public class TbCodeController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TbCodeController.class);

	private @Autowired TbCodeRepo codeRepo;
	
	private @Autowired TbCodeServ codeServ;
	
	private @Autowired SnServ snServ;

	private @Autowired CodeCacheRefreshEvent codeCacheRefreshEvent;
	
	/**
	 * 新增或修改数据字典
	 * @Title: saveOrUpdateCode 
	 * @param code
	 * @return
	 */
	@PostMapping(value={"saveOrUpdate"})
	@ResponseBody
	public TbCode saveOrUpdateCode(@RequestBody TbCode code){
		if(null == code.getId() || 0 == code.getId()){
			code.setId(null);
			code.setCode(snServ.generateCode(TB_CODE_SN_TYPE, true));
		}
		
		code.setEnabledFlag(ENABLED_FLAG);
		
		code = codeRepo.save(code);
		codeCacheRefreshEvent.throwCodeCacheRefreshEvent();
		return code;
	}
	
	/**
	 * 删除数据字典(根据ID)
	 * @Title: delCode 
	 * @param id
	 * @return
	 */
	@DeleteMapping(value={"del/delById/{id}"})
	@ResponseBody
	public boolean delById(@PathVariable Integer id){
		int countNum = codeRepo.findIsSystemResource(id);
		AssertUtil.isTrue(countNum == 0, DELETE_SYSTEM_RESOURCE_MSG);
		boolean isDel = codeRepo.delById(id) > 0;
		codeCacheRefreshEvent.throwCodeCacheRefreshEvent();
		return isDel;
	}
	
	/**
	 * 查询数据字典(根据id)
	 * @Title: getById 
	 * @param id
	 * @return
	 */
	@GetMapping(value={"query/getById/{id}"})
	@ResponseBody
	public TbCode getById(@PathVariable Integer id){
		return codeRepo.findById(id).get();
	}
	
	/**
	 * 查询数据字典(根据code)
	 * @Title: getByCode 
	 * @param code
	 * @return
	 */
	@GetMapping(value={"query/getByCode/{code}"})
	@ResponseBody
	public TbCode getByCode(@PathVariable String code){
		return codeRepo.findByCode(code);
	}

	/**
	 * 查询数据字典(根据CodeType)
	 * @Title: getByCode
	 * @param CodeType
	 * @return
	 */
	@GetMapping(value={"query/getByCodeType/{CodeType}"})
	@ResponseBody
	public TbCode getByCodeType(@PathVariable String CodeType){
		return codeRepo.findByCodeType(CodeType);
	}

	/**
	 * 获取fancytree数据格式的所有数据字典数据
	 * @Title: listTreeAll
	 * @return List<FancyTreeNode>
	 */
	@GetMapping(value={"query/listTreeAll"})
	@ResponseBody
	public List<FancyTreeNode> listTreeAll() {
		TbCode rootCode = codeServ.getRootCode();
		
		List<TbCode> allCodeList = codeRepo.procShowCodeChildLst(rootCode.getCode());
		
		TbCode codeTree = TreeUtil.buildByRecursiveByHasRootTree(allCodeList,
				treeNode -> ROOT_CODE_PARENT_CODE.equals(treeNode.getTreeParentId()));
		
		List<FancyTreeNode> nodes = new ArrayList<FancyTreeNode>();
		
		nodes.add(FancyTreeUtil.getFancyTree(codeTree, 
				treeCode -> new FancyTreeNodeData(String.valueOf(treeCode.getId()), treeCode.getParentId(),
						treeCode.getName(), treeCode.getCode()),
				1, 0, null));
		
		FancyTreeUtil.initTreeKeyByRecursive(nodes); //设置排序key
		return nodes;
	}
	
	@RequestMapping(value={"query/existCodeType"})
	@ResponseBody
	public ExistResponse existRole(@RequestParam String param, @RequestParam(required=false) String originalCodeType){
		TbCode code = codeRepo.findByCodeType(param);
		
		if(code != null && param.equalsIgnoreCase(code.getCodeType())){
			if(null != originalCodeType && originalCodeType.equalsIgnoreCase(code.getCodeType())){
				//code存在,且等于原有用户名
				return ExistResponse.getExistSuccess(); 
			}else{
				//code存在,且不等于原有用户名
				return ExistResponse.getDefaultExist(); 
			}
		}
		
		return ExistResponse.getDefaultSuccess();
	}
	
	/**
	 * 判断同级节点名称是否存在
	 * @return
	 */
	@RequestMapping(value={"query/existNameByParentId"})
	@ResponseBody
	public ExistResponse existNameByParentId(@RequestParam(value="param") String name,
			@RequestParam String parentId, @RequestParam(required=false) String originalName){
		List<TbCode> codeList = codeRepo.findListByParentIdAndName(parentId, name);
		
		if(null != codeList && !codeList.isEmpty()){
			if(codeList.size() > 1){
				//数据不正常
				return ExistResponse.getDefaultErrorExist(); 
			}
			TbCode code = codeList.get(0);
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

	@RequestMapping(path="/query/cache/getCodesByCodeType")
	@ResponseBody
	public List<TbCode> getCodesByCodeType(String codeType){
		List<TbCode> userStateList = CodeCacheService.getCodesByCodeType(codeType);
		return userStateList;
	}

}
