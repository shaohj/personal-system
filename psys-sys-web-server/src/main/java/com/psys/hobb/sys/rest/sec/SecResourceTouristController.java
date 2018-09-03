package com.psys.hobb.sys.rest.sec;

import com.psys.hobb.sys.rest.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.SYS_RESOURCE_TOURIST_INDEX_URL;

/**
 * 游客资源管理Controller
 * 编  号：
 * 名  称：SecResourceTouristController
 * 描  述：
 * 完成日期：2018/7/14 13:04
 * 编码作者：SHJ
 */
@Controller
@RequestMapping(value = "sys/resourcetourist", produces="application/json;charset=UTF-8")
public class SecResourceTouristController extends BaseController {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(SecResourceTouristController.class);

    /**
     * 请求资源管理首页
     * @Title: toList
     * @return
     */
    @RequestMapping(path="to/list")
    public ModelAndView toList(){
        ModelMap modeMap = new ModelMap();

        modeMap.put("module", SYS_RESOURCE_TOURIST_INDEX_URL);
        setUserLoginInfo(modeMap);

        return new ModelAndView("index", modeMap);
    }

}

