package com.psys.hobb.sys.rest.blog;

import java.util.ArrayList;

import com.psys.hobb.sys.rest.base.BaseController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.*;

/**
 * 博客群组
 * @author shaohj
 *
 */
@Controller
@RequestMapping("blog/group")
public class BlogGroupController extends BaseController {

	/**
	 * 请求博客群组首页
	 * @Title: toList 
	 * @return
	 */
	@RequestMapping(path="/to/list")
	public ModelAndView toList(){
		ModelMap modeMap = new ModelMap();
		
		modeMap.put("module", BLOG_GROUP_INDEX_URL);
		setUserLoginInfo(modeMap);

		return new ModelAndView("index", modeMap);
	}
	
	/**
	 * 进入博客群组数据主页
	 * @Title: toList 
	 * @return
	 */
	@RequestMapping(path="/to/listData")
	public ModelAndView listData(@RequestParam(defaultValue="") String userName,
			@RequestParam(defaultValue="") String status,
			@RequestParam(defaultValue="") String groupCode,
			@PageableDefault(page = 0, size = 10) Pageable pageable){
		ModelMap modeMap = new ModelMap();
		
		Page<Object> pageResult = new PageImpl<>(new ArrayList<Object>(), pageable, 0);
		modeMap.put("pageResult", pageResult);
		
		return new ModelAndView(BLOG_GROUP_TABLE_URL, modeMap);
	}
	
}
