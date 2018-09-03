package com.psys.hobb.sys.rest;

import com.psys.hobb.sys.rest.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 游客控制器
 * 编  号：
 * 名  称：TouristController
 * 描  述：
 * 完成日期：2018/7/9 23:10
 * 编码作者：SHJ
 */
@Controller
@RequestMapping(produces="application/json;charset=UTF-8")
public class TouristController extends BaseController {

	@RequestMapping(value = {"/tourist"})
	public ModelAndView toIndex(Map<String, Object> map, HttpServletRequest request){
        ModelMap modeMap = new ModelMap();

		modeMap.put("module", "/tourist/content.ftl");
		setUserLoginInfo(modeMap);

		return new ModelAndView("/index", modeMap);
	}

}
