package com.psys.hobb.sys.rest.blog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.common.sso.bean.SsoUser;
import com.psys.hobb.common.sys.bean.ServiceResponse;
import com.psys.hobb.common.sys.util.AssertUtil;
import com.psys.hobb.common.sys.util.constant.SysExceptionEnum;
import com.psys.hobb.common.utils.FastJsonUtils;
import com.psys.hobb.sys.rest.base.BaseController;
import com.psys.hobb.sys.service.blog.IBlogService;
import com.psys.hobb.sys.service.sec.SecUserService;
import com.psys.hobb.sys.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.BLOG_CENTER_INDEX_URL;
import static com.psys.hobb.common.sys.util.constant.UiPathConstants.BLOG_CENTER_TABLE_URL;
import static com.psys.hobb.redis.util.BlogConstants.*;

/**
 * 博客系统中心
 * @author shaohj
 *
 */
@Controller
@RequestMapping(value = "blog/center", produces="application/json;charset=UTF-8")
public class BlogController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(BlogController.class);

	private @Autowired IBlogService blogServ;

	@Autowired
	protected SecUserService secUserService;

	@PostMapping(value={"saveOrUpdate"})
	@ResponseBody
	public ServiceResponse<String> saveOrUpdateBlog(@RequestBody Map<String, String> blogParam){
		String tokenValue = SecurityUtils.getTokenWithValid();
		SsoUser ssoUser = secUserService.getUserByCache(tokenValue);

		String userKey = USER_PRE + ssoUser.getUserId();
		String title = ObjectUtils.getDisplayString(blogParam.get("title"));
		String content = ObjectUtils.getDisplayString(blogParam.get("content"));
		String link = ObjectUtils.getDisplayString(blogParam.get("link"));

		blogServ.postArticle(userKey, title, content, link);

		return new ServiceResponse<String>(SysExceptionEnum.SUCCESS, "操作成功！", "success");
	}

	@PostMapping(value={"voteArticle"}, consumes = "application/json; charset=UTF-8")
	@ResponseBody
	public ServiceResponse<Boolean> voteArticle(@RequestBody Map<String, String> voteParam){
		String tokenValue = SecurityUtils.getTokenWithValid();
		SsoUser ssoUser = secUserService.getUserByCache(tokenValue);

		String userKey = USER_PRE + ssoUser.getUserId();
		String artHKey = ART_PRE + ObjectUtils.getDisplayString(voteParam.get("artId"));

		return new ServiceResponse<Boolean>(SysExceptionEnum.SUCCESS, "操作成功！", blogServ.articleVote(userKey, artHKey));
	}

	@GetMapping("hello")
	@ResponseBody
	public Map<String, String> hello(){
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("name", "zs");
		resultMap.put("age", "19");
		return  resultMap;
	}

	/**
	 * 请求博客系统中心首页
	 * @Title: toList
	 * @return
	 */
	@RequestMapping(path="/to/list")
	public ModelAndView toList(){
		logger.debug("---->博客系统中心首页<----");
		ModelMap modeMap = new ModelMap();

		modeMap.put("module", BLOG_CENTER_INDEX_URL);
		setUserLoginInfo(modeMap);

		List<Map<String, String>> blogSortList = new ArrayList<>();

		Map<String, String> blogSortField01 = new HashMap<String, String>();
		blogSortField01.put("fname", "发布时间");
		blogSortField01.put("field", ART_PUB_TIME);

		Map<String, String> blogSortField02 = new HashMap<String, String>();
		blogSortField02.put("fname", "投票得分");
		blogSortField02.put("field", ART_SCORE);

		blogSortList.add(blogSortField01);
		blogSortList.add(blogSortField02);

		modeMap.put("blogSortFieldList", blogSortList);

		return new ModelAndView("index", modeMap);
	}

	/**
	 * 进入博客系统中心数据主页
	 * @Title: toList
	 * @return
	 */
	@RequestMapping(path="/to/listData")
	public ModelAndView listData(@RequestParam(defaultValue="") String userName,
								 @RequestParam(defaultValue="") String status,
								 @RequestParam(defaultValue="") String groupCode,
								 @PageableDefault(page = 0, size = 10) Pageable pageable){
		ModelMap modeMap = new ModelMap();

		String sortField = null == pageable.getSort() || "UNSORTED".equalsIgnoreCase(pageable.getSort().toString())
				? ART_PUB_TIME : pageable.getSort().iterator().next().getProperty(); //排序字段

		Page<Map<Object, Object>> pageResult = blogServ.getPage(pageable, sortField);
		modeMap.put("pageResult", pageResult);

		return new ModelAndView(BLOG_CENTER_TABLE_URL, modeMap);
	}

}
