package com.psys.hobb.sec.rest;

import com.psys.hobb.common.sys.bean.ExistResponse;
import com.psys.hobb.common.tree.fancytree.FancyTreeNode;
import com.psys.hobb.common.tree.fancytree.FancyTreeNodeData;
import com.psys.hobb.common.tree.fancytree.FancyTreeUtil;
import com.psys.hobb.common.tree.util.TreeUtil;
import com.psys.hobb.sec.dao.sec.SecResourceTouristRepo;
import com.psys.hobb.sec.model.sec.SecResourceTourist;
import com.psys.hobb.sec.service.sec.SecResourceTouristServ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.*;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * 游客资源管理Controller
 * 编  号：
 * 名  称：SecResourceTouristController
 * 描  述：
 * 完成日期：2018/7/14 12:11
 * 编码作者：SHJ
 */
@Controller
@RequestMapping(value = "sys/resourcetourist", produces="application/json;charset=UTF-8")
public class SecResourceTouristController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SecResourceTouristController.class);

	@Autowired
	private SecResourceTouristRepo secResourceTouristRepo;

	@Autowired
	private SecResourceTouristServ secResourceTouristServ;

	@Autowired
	private RedisTemplate<String, SecResourceTourist> resourceRedisTemplate;

	/**
	 * 新增或修改资源
	 * @Title: saveOrUpdateCode 
	 * @param resource
	 * @return
	 */
	@PostMapping(value={"saveOrUpdate"})
	@ResponseBody
	public SecResourceTourist saveOrUpdateResource(@RequestBody SecResourceTourist resource){
		if(StringUtils.isEmpty(resource.getResourceId())){
			resource.setResourceId(null);
		}
		if(StringUtils.isEmpty(resource.getUrl())){
			resource.setUrl(null);
		}
		return secResourceTouristServ.saveOrUpdateResource(resource);
	}
	
	/**
	 * 删除资源(根据Code)
	 * @Title: delByCode 
	 * @param code
	 * @return
	 */
	@DeleteMapping(value={"del/delByCode/{code}"})
	@ResponseBody
	public boolean delByCode(@PathVariable String code){
		return secResourceTouristServ.deleteByCode(code);
	}
	
	/**
	 * 查询资源(根据id)
	 * @Title: getById 
	 * @param id
	 * @return
	 */
	@GetMapping(value={"query/getById/{id}"})
	@ResponseBody
	public SecResourceTourist getById(@PathVariable Integer id){
		return secResourceTouristRepo.findById(id).get();
	}
	
	/**
	 * 查询资源(根据code)
	 * @Title: getByCode 
	 * @param code
	 * @return
	 */
	@GetMapping(value={"query/getByCode/{code}"})
	@ResponseBody
	public SecResourceTourist getByCode(@PathVariable String code){
		return secResourceTouristRepo.findByResourceCode(code);
	}
	
	/**
	 * 获取fancytree数据格式的所有资源数据
	 * @Title: listTreeAll
	 * @return
	 */
	@GetMapping(value={"query/listTreeAll"})
	@ResponseBody
	public List<FancyTreeNode> listTreeAll() {
		List<SecResourceTourist> allCodeList = secResourceTouristRepo.findAll();

		//SecResource没有根节点,手动模拟一个节点
		SecResourceTourist rootResource = new SecResourceTourist(null, ROOT_RESOURCE_PARENT_ID,"模拟根节点", "-");
		SecResourceTourist codeTree = TreeUtil.buildByRecursive(allCodeList, rootResource);

		List<FancyTreeNode> nodes = new ArrayList<FancyTreeNode>();

		nodes.add(FancyTreeUtil.getFancyTree(codeTree,
				resTreeCode -> new FancyTreeNodeData(resTreeCode.getResourceCode(), resTreeCode.getParentId(), resTreeCode.getName()),
				1, 0, null));

		FancyTreeUtil.initTreeKeyByRecursive(nodes); //设置排序key
		return nodes.get(0).getChildren();
	}

	/**
	 * 判断同级节点名称是否存在
	 * @return
	 */
	@RequestMapping(value={"query/existNameByParentId"})
	@ResponseBody
	public ExistResponse existNameByParentId(@RequestParam(value="param") String name,
			@RequestParam String parentId, @RequestParam(required=false) String originalName){
		List<SecResourceTourist> resourceList = secResourceTouristRepo.findListByParentIdAndName(parentId, name);

		if(null != resourceList && !resourceList.isEmpty()){
			if(resourceList.size() > 1){
				//数据不正常
				return ExistResponse.getDefaultErrorExist();
			}
			SecResourceTourist code = resourceList.get(0);
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

	@GetMapping(path="query/getTouristMenu")
	@ResponseBody
	public SecResourceTourist getUserMenuByCache(){
		Map<String, Object> resultMap = new HashMap<>();

		//查询用户的资源信息,不做redis缓存处理,在具体调用层进行缓存处理
		List<SecResourceTourist> allResourceList = secResourceTouristRepo.findAll();

		allResourceList = allResourceList.stream().collect(
				collectingAndThen(
						toCollection(() -> new TreeSet<>(comparingInt(SecResourceTourist::getResourceId))), ArrayList<SecResourceTourist>::new)
		);

		SecResourceTourist rootResource = new SecResourceTourist(null, ROOT_RESOURCE_PARENT_ID,"模拟根节点", "-");
		SecResourceTourist userResource = TreeUtil.buildByRecursive(allResourceList, rootResource);

		return userResource;
	}

}
