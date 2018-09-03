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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.*;

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
public class SecGroupController extends BaseController {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(SecGroupController.class);

    /**
     * 请求组织首页
     * @Title: toList
     * @return
     */
    @RequestMapping(path="to/list")
    public ModelAndView toList(){
        ModelMap modeMap = new ModelMap();

        modeMap.put("module", SYS_GROUP_INDEX_URL);
        setUserLoginInfo(modeMap);

        return new ModelAndView("index", modeMap);
    }

}
