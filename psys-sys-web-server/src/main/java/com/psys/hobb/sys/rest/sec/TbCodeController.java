package com.psys.hobb.sys.rest.sec;

import com.psys.hobb.common.sys.bean.ExistResponse;
import com.psys.hobb.common.tree.fancytree.FancyTreeNode;
import com.psys.hobb.common.tree.fancytree.FancyTreeNodeData;
import com.psys.hobb.common.tree.fancytree.FancyTreeUtil;
import com.psys.hobb.common.tree.util.TreeUtil;
import com.psys.hobb.sys.rest.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.*;

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
public class TbCodeController  extends BaseController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TbCodeController.class);

	/**
	 * 请求数据字典首页
	 * @Title: toList 
	 * @return
	 */
	@RequestMapping(path="to/list")
	public ModelAndView toList(){
		ModelMap modeMap = new ModelMap();
		
		modeMap.put("module", SYS_CODE_INDEX_URL);
		setUserLoginInfo(modeMap);

		return new ModelAndView("index", modeMap);
	}

}
