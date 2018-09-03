package com.psys.hobb.sys.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.psys.hobb.sys.rest.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(produces="application/json;charset=UTF-8")
public class IndexController extends BaseController {

	@RequestMapping(value = {"/", "/index"})
	public ModelAndView toIndex(Map<String, Object> map, HttpServletRequest request){
        ModelMap modeMap = new ModelMap();

		modeMap.put("module", "/index/content.ftl");
		setUserLoginInfo(modeMap);

		return new ModelAndView("/index", modeMap);
	}

	@GetMapping("hello")
	@ResponseBody
	public Map<String, String> hello(){
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("name", "zs");
		resultMap.put("age", "19");
		return  resultMap;
	}

}
