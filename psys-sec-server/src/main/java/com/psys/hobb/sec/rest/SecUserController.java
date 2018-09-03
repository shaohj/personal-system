package com.psys.hobb.sec.rest;

import com.psys.hobb.common.sso.bean.SsoUser;
import com.psys.hobb.common.sys.bean.ExistResponse;
import com.psys.hobb.common.sys.util.AssertUtil;
import com.psys.hobb.common.tree.util.TreeUtil;
import com.psys.hobb.sec.dao.sec.SecGroupRepo;
import com.psys.hobb.sec.dao.sec.SecRoleRepo;
import com.psys.hobb.sec.dao.sec.SecUserRepo;
import com.psys.hobb.sec.model.sec.SecGroup;
import com.psys.hobb.sec.model.sec.SecRole;
import com.psys.hobb.sec.model.sec.SecUser;
import com.psys.hobb.sec.model.sec.TbCode;
import com.psys.hobb.sec.service.sec.SecGroupServ;
import com.psys.hobb.sec.service.sec.SecUserServ;
import com.psys.hobb.sec.service.sec.impl.CodeCacheService;
import com.psys.hobb.sec.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "sys/user", produces="application/json;charset=UTF-8")
public class SecUserController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SecUserController.class);

	@Autowired
	private SecUserRepo userRepo;

	@Autowired
	private SecRoleRepo roleRepo;

	@Autowired
	private SecUserServ secUserServ;

	@Autowired
	private SecGroupRepo secGroupRepo;

	@Autowired
	private SecGroupServ secGroupServ;

	/**
	 * 新增或修改用户
	 * @Title: saveOrUpdateUser 
	 * @param user
	 * @return
	 */
	@PostMapping(value={"saveOrUpdate"})
	@ResponseBody
	public SecUser saveOrUpdateUser(@RequestBody SecUser user){
		return secUserServ.saveOrUpdateUser(user);
	}
	
	/**
	 * 新增或修改用户角色,即角色分配
	 * @Title: saveOrUpdateUserRole 
	 * @param user
	 * @return
	 */
	@PostMapping(value={"role/saveOrUpdate"})
	@ResponseBody
	public SecUser saveOrUpdateUserRole(@RequestBody SecUser user){
		return secUserServ.saveOrUpdateUserRole(user);
	}
	
	/**
	 * 启用用户
	 * @Title: enableUsers 
	 * @param userIds
	 * @return
	 */
	@PostMapping(value={"enable/userIds/{userIds}"})
	@ResponseBody
	public boolean enableUsers(@PathVariable List<Integer> userIds){
		return userRepo.updateUsersStatus(userIds, CODE_USER_STATE_NORMAL) > 0;
	}
	
	/**
	 * 禁用用户
	 * @Title: enableUsers 
	 * @param userIds
	 * @return
	 */
	@PostMapping(value={"disable/userIds/{userIds}"})
	@ResponseBody
	public boolean disableUsers(@PathVariable List<Integer> userIds){
		return userRepo.updateUsersStatus(userIds, CODE_USER_STATE_DISABLED) > 0;
	}
	
	/**
	 * 查询用户(根据id)
	 * @Title: getById 
	 * @param id
	 * @return
	 */
	@GetMapping(value={"query/getById/{id}"})
	@ResponseBody
	public SecUser getById(@PathVariable Integer id){
		return userRepo.findById(id).get();
	}

	/**
	 * getByUserName
	 * @param userName :
	 * @return com.psys.hobb.sec.model.sec.SecUser
	 * @author SHJ
	 * @since 2018/5/19 10:21
	 */
	@GetMapping(value={"query/getByUserName"})
	@ResponseBody
	public SecUser getByUserName(@RequestParam String userName){
		return userRepo.findByUserName(userName);
	}

	/**
	 * getByUserNameAndPassword
	 * @param userName :
	 * @param password :
	 * @return com.psys.hobb.sec.model.sec.SecUser
	 * @author SHJ
	 * @since 2018/5/19 10:23
	 */
	@GetMapping(value={"query/getByUserNameAndPassword"})
	@ResponseBody
	public SecUser getByUserNameAndPassword(@RequestParam String userName, @RequestParam String password){
		return userRepo.findByUserNameAndPassword(userName, password);
	}

	/**
	 * 查询用户(根据code)
	 * @Title: getByCode 
	 * @param code
	 * @return
	 */
	@GetMapping(value={"query/getByCode/{code}"})
	@ResponseBody
	public SecUser getById(@PathVariable String code){
		return userRepo.findByUserCode(code);
	}
	
	/**
	 * 进入查询用户数据主页
	 * @Title: toList 
	 * @return
	 */
	@RequestMapping(path="/query/pageBySearch")
	@ResponseBody
	public Page<SecUser> pageBySearch(@RequestParam(defaultValue="") String userName,
			@RequestParam(defaultValue="") String status,
			@RequestParam(defaultValue="") String groupCode,
			@PageableDefault(page = 0, size = 10) Pageable pageable){

		/** 0. 处理查询参数 */
		userName = StringUtils.isEmpty(userName) ? "" : userName;
		groupCode = StringUtils.isEmpty(groupCode) ? null : groupCode;
		
		List<TbCode> userStateCodeList = CodeCacheService.getCodesByCodeType(CODE_USER_STATE);
		AssertUtil.notEmpty(userStateCodeList, "用户状态数据字典缓存为空，请联系管理员检查数据");
		if(!StringUtils.isEmpty(status) ){
			userStateCodeList = userStateCodeList.stream()
					.filter(userState -> status.equals(userState.getCodeType())).collect(Collectors.toList());
		}
		List<String> userStateList = userStateCodeList.stream().map(TbCode::getCodeType).collect(Collectors.toList());
		
		/** 1.查询数据 */
		Page<Object[]> pageTemp = userRepo.findPageByName(userName, groupCode, userStateList, pageable);
		
		/** 2.原生sql查询数据处理 */
		List<SecUser> users = new ArrayList<>(pageTemp.getContent().size());
		
		pageTemp.getContent().stream().forEach(objs -> {
			SecUser user = new SecUser();
			
			user.setUserId((Integer) objs[0]);
			user.setUserCode(ObjectUtils.getDisplayString(objs[1]));
			user.setUserName(ObjectUtils.getDisplayString(objs[2]));
			user.setRealName(ObjectUtils.getDisplayString(objs[3]));
			user.setEmail(ObjectUtils.getDisplayString(objs[4]));
			
			user.setMobile(ObjectUtils.getDisplayString(objs[5]));
			user.setStatus(ObjectUtils.getDisplayString(objs[6]));
			user.setSex(ObjectUtils.getDisplayString(objs[7]));
			Timestamp birthday = (Timestamp) objs[8];
			if(null != birthday){
				user.setBirthday(birthday.toLocalDateTime());
			}
			user.setIsAdmin(ObjectUtils.getDisplayString(objs[9]));

			user.setEnabledFlag(ObjectUtils.getDisplayString(objs[10]));
			user.setRoleNames(ObjectUtils.getDisplayString(objs[12]));
			
			/* 调用存储过程查询组织全称,  */
			String groupCodeTemp = ObjectUtils.getDisplayString(objs[11]);
			if(!StringUtils.isEmpty(groupCodeTemp)){
				SecGroup userGroup = new SecGroup();
				userGroup.setGroupCode(groupCodeTemp);
				user.setSecGroup(userGroup);
			}
			
			users.add(user);
		});

		/** 查询组织信息,优化处理,只查询一次数据库 */
		List<SecGroup> allGroupList = secGroupRepo.findAllByEnabled();
		SecGroup groupTree = TreeUtil.buildByRecursiveByHasRootTree(allGroupList,
				treeNode -> ROOT_GROUP_PARENT_CODE.equals(treeNode.getTreeParentId()));

		/* groupMap存放组织信息,key为组织code,value为组织全称 */
		final  Map<String, String> groupMap = new HashMap<>();

		users.stream().forEach(u -> {
			String groupCodeTemp = null == u.getSecGroup() ? null : u.getSecGroup().getGroupCode();
			if(!StringUtils.isEmpty(groupCodeTemp)){
				if(!groupMap.containsKey(groupCodeTemp)){
					String fullName = secGroupServ.getFullGroupName(groupTree, groupCodeTemp);
					groupMap.put(groupCodeTemp, fullName);
				}
				u.setGroupNames(groupMap.get(groupCodeTemp));
			}
		});

		Page<SecUser> pageResult = new PageImpl<>(users, pageable, pageTemp.getTotalElements());

		return pageResult;
	}
	
	/**
	 * 进入角色授予对话框
	 */
	@RequestMapping(value = {"/query/getUserRoles"})
	@ResponseBody
	public List<SecRole> getUserRoles(@RequestParam Integer userId) {
		AssertUtil.notEmptyStr(userId, "userId不能为空！");
		SecUser user = userRepo.findById(userId).get();
		AssertUtil.notEmptyStr(user, "userId为" + userId + "的用户不存在！");
		
		List<SecRole> allRole = roleRepo.findAll();
		
		final List<SecRole> userRoles = roleRepo.findRoleByUserCode(user.getUserCode());
		
		if(!CollectionUtils.isEmpty(allRole) && !CollectionUtils.isEmpty(userRoles)){
			allRole.stream().forEach(role -> {
				//根据用户是否有该角色，设置角色是否被选中
				long count = userRoles.stream()
						.filter(userRole -> userRole.getRoleId().equals(role.getRoleId()))
						.count();
				
				if(count > 0){
					role.setIsChecked("true");
				} 
			});
		}
		return allRole;
	}
	
	/**
	 * 判断同级节点名称是否存在
	 * @return
	 */
	@RequestMapping(value={"query/existName"})
	@ResponseBody
	public ExistResponse existNameByParentId(@RequestParam(value="param") String name, @RequestParam(required=false) String originalName){
		SecUser user = userRepo.findByUserName(name);

		if(null != user ){
			if(null != originalName && originalName.equalsIgnoreCase(user.getUserName())){
				//数据字典名存在,且等于原有数据字典名
				return ExistResponse.getExistSuccess();
			}else{
				//数据字典存在,且不等于原有数据字典
				return ExistResponse.getDefaultExist(); 
			}
		}
		return ExistResponse.getDefaultSuccess();
	}

	@GetMapping(path="query/getLoginUser")
	@ResponseBody
	public SsoUser getLoginUser(){
		String tokenValue = SecurityUtils.getTokenWithValid(); //获取access_token
		return  secUserServ.getSsoUser(tokenValue);
	}
	
}
