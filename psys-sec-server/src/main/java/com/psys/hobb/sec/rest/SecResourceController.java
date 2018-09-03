package com.psys.hobb.sec.rest;

import com.psys.hobb.common.sso.bean.SsoUser;
import com.psys.hobb.common.sys.bean.ExistResponse;
import com.psys.hobb.common.sys.util.AssertUtil;
import com.psys.hobb.common.sys.util.constant.SysRedisConstants;
import com.psys.hobb.common.tree.fancytree.FancyTreeNode;
import com.psys.hobb.common.tree.fancytree.FancyTreeNodeData;
import com.psys.hobb.common.tree.fancytree.FancyTreeUtil;
import com.psys.hobb.common.tree.util.TreeUtil;
import com.psys.hobb.sec.dao.sec.SecResourceRepo;
import com.psys.hobb.sec.model.sec.SecResource;
import com.psys.hobb.sec.service.sec.SecResourceServ;
import com.psys.hobb.sec.service.sec.SecUserServ;
import com.psys.hobb.sec.utils.SecurityUtils;
import com.psys.hobb.sec.utils.SysUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.*;
import static com.psys.hobb.common.sys.util.constant.SysRedisConstants.*;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 资源管理Controller
 * 编  号：<br/>
 * 名  称：SecResourceController<br/>
 * 描  述：<br/>
 * 完成日期：2017年12月12日 下午8:22:11<br/>
 * 编码作者：shaohj<br/>
 */
@Controller
@RequestMapping(value = "sys/resource", produces="application/json;charset=UTF-8")
public class SecResourceController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SecResourceController.class);

	@Autowired
	private SecResourceRepo secResourceRepo;

	@Autowired
	private SecResourceServ secResourceServ;

	@Autowired
	private  SecUserServ secUserServ;

	@Autowired
    private RedisTemplate<String, SecResource> resourceRedisTemplate;

	/**
	 * 新增或修改资源
	 * @Title: saveOrUpdateCode 
	 * @param resource
	 * @return
	 */
	@PostMapping(value={"saveOrUpdate"})
	@ResponseBody
	public SecResource saveOrUpdateResource(@RequestBody SecResource resource){
		if(StringUtils.isEmpty(resource.getResourceId())){
			resource.setResourceId(null);
		}
		if(StringUtils.isEmpty(resource.getUrl())){
			resource.setUrl(null);
		}
		return secResourceServ.saveOrUpdateResource(resource);
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
		return secResourceServ.deleteByCode(code);
	}
	
	/**
	 * 查询组织(根据id)
	 * @Title: getById 
	 * @param id
	 * @return
	 */
	@GetMapping(value={"query/getById/{id}"})
	@ResponseBody
	public SecResource getById(@PathVariable Integer id){
		return secResourceRepo.findById(id).get();
	}
	
	/**
	 * 查询数据字典(根据code)
	 * @Title: getByCode 
	 * @param code
	 * @return
	 */
	@GetMapping(value={"query/getByCode/{code}"})
	@ResponseBody
	public SecResource getByCode(@PathVariable String code){
		return secResourceRepo.findByResourceCode(code);
	}
	
	/**
	 * 获取fancytree数据格式的所有资源数据
	 * @Title: listTreeAll
	 * @return
	 */
	@GetMapping(value={"query/listTreeAll"})
	@ResponseBody
	public List<FancyTreeNode> listTreeAll() {
		List<SecResource> allCodeList = secResourceRepo.findAll();

		//SecResource没有根节点,手动模拟一个节点
		SecResource rootResource = new SecResource(null, ROOT_RESOURCE_PARENT_ID,"模拟根节点", "-");
		SecResource codeTree = TreeUtil.buildByRecursive(allCodeList, rootResource);

		List<FancyTreeNode> nodes = new ArrayList<FancyTreeNode>();

		nodes.add(FancyTreeUtil.getFancyTree(codeTree,
				resTreeCode -> new FancyTreeNodeData(resTreeCode.getResourceCode(), resTreeCode.getParentId(), resTreeCode.getName()),
				1, 0, null));

		FancyTreeUtil.initTreeKeyByRecursive(nodes); //设置排序key
		return nodes.get(0).getChildren();
	}

	/**
	 * 获取用户的资源数据,树结构
	 * @Title: listUserResTree
	 * @return
	 */
	@GetMapping(value={"query/listUserResTree"})
	@ResponseBody
	public SecResource listUserResTree(@RequestParam Integer userId) {
		List<SecResource> allResourceList = secResourceServ.findListByUserIdAndParentId(userId, null);

		allResourceList = allResourceList.stream().collect(
				collectingAndThen(
						toCollection(() -> new TreeSet<>(comparingInt(SecResource::getResourceId))), ArrayList<SecResource>::new)
		);

		SecResource rootResource = new SecResource(null, ROOT_RESOURCE_PARENT_ID,"模拟根节点", "-");
		SecResource resTree = TreeUtil.buildByRecursive(allResourceList, rootResource);
		return resTree;
	}
	
	/**
	 * 获取parent下第一个菜单的url
	 * @Title: getSecondMenList 
	 * @param userId
	 * @param parentId
	 * @param request
	 * @return
	 */
	@GetMapping(path="query/firstMenuUrl")
	public @ResponseBody String getFirstMenuUrl(
			@RequestParam Integer userId, @RequestParam String parentId, HttpServletRequest request){
		SecResource resTree = (SecResource) request.getSession().getAttribute(SEC_USER_MENU_KEY);
		SecResource parentMenu = SysUtil.getResourceByResourceCode(resTree, parentId);
		
		List<SecResource> resourceList = parentMenu.getChildren();
		
		AssertUtil.notEmpty(resourceList, "子菜单资源未配置，或未授权，请联系管理员");
		return resourceList.stream().map(SecResource::getUrl).findFirst().orElse("");
	}
	
	/**
	 * 判断同级节点名称是否存在
	 * @return
	 */
	@RequestMapping(value={"query/existNameByParentId"})
	@ResponseBody
	public ExistResponse existNameByParentId(@RequestParam(value="param") String name,
			@RequestParam String parentId, @RequestParam(required=false) String originalName){
		List<SecResource> resourceList = secResourceRepo.findListByParentIdAndName(parentId, name);

		if(null != resourceList && !resourceList.isEmpty()){
			if(resourceList.size() > 1){
				//数据不正常
				return ExistResponse.getDefaultErrorExist();
			}
			SecResource code = resourceList.get(0);
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

	@GetMapping(path="query/getLoginUserMenu")
	@ResponseBody
	public SecResource getLoginUserMenu(){
		/***
		 * 1.获取access_token
		 * 2.根据access_token查询到登录用户id,再查询用户信息。登录信息需要先实时查询token是否存在,再缓存到redis中,access_token为key,如果只需查询用户信息,可以不用检验token是否有效
		 * 3.查询用户的资源信息,查询缓存到redis中，access_token为key
		 */
		String tokenValue = SecurityUtils.getTokenWithValid(); //获取access_token
		SsoUser user = secUserServ.getSsoUserAndValidToken(tokenValue); //验证access_token并获取对应的用户信息

		//查询用户的资源信息,查询缓存到redis中，access_token为key
		String userResourceRedisKey = SEC_USER_MENU_KEY + user.getUserId();
		SecResource userResource = resourceRedisTemplate.opsForValue().get(userResourceRedisKey);

		if(null == userResource){
			List<SecResource> allResourceList = secResourceServ.findListByUserIdAndParentId(user.getUserId(), null);

			allResourceList = allResourceList.stream().collect(
					collectingAndThen(
							toCollection(() -> new TreeSet<>(comparingInt(SecResource::getResourceId))), ArrayList<SecResource>::new)
			);

			SecResource rootResource = new SecResource(null, ROOT_RESOURCE_PARENT_ID,"模拟根节点", "-");
			userResource = TreeUtil.buildByRecursive(allResourceList, rootResource);

			resourceRedisTemplate.opsForValue().set(userResourceRedisKey, userResource, SysRedisConstants.LOGIN_USER_EXPIRATION, SysRedisConstants.LOGIN_USER_EXPIRATION_TIMEUNIT);
		}

		return userResource;
	}
	
}
