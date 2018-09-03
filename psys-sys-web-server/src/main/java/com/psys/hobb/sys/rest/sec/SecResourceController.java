package com.psys.hobb.sys.rest.sec;

import com.psys.hobb.sys.rest.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.*;

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
public class SecResourceController extends BaseController {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(SecResourceController.class);

    /**
     * 请求资源管理首页
     * @Title: toList
     * @return
     */
    @RequestMapping(path="to/list")
    public ModelAndView toList(){
        ModelMap modeMap = new ModelMap();

        modeMap.put("module", SYS_RESOURCE_INDEX_URL);
        setUserLoginInfo(modeMap);

        return new ModelAndView("index", modeMap);
    }

}

