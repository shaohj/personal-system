package com.psys.hobb.sys.rest.sec;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.sys.rest.base.BaseController;
import com.psys.hobb.sys.feign.sec.SecRoleFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.*;

/**
 * 角色controller
 * 编  号：<br/>
 * 名  称：SecRoleController<br/>
 * 描  述：<br/>
 * 完成日期：2017年12月17日 下午9:19:53<br/>
 * 编码作者：shaohj<br/>
 */
@Controller
@RequestMapping(value = "sys/role", produces="application/json;charset=UTF-8")
public class SecRoleController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SecRoleController.class);

    private @Autowired
    SecRoleFeign secRoleFeign;

    /**
     * 进入角色首页
     * @Title: toList
     * @return
     */
    @RequestMapping(path="/to/list")
    public ModelAndView toList(){
        ModelMap modeMap = new ModelMap();

        modeMap.put("module", SYS_ROLE_INDEX_URL);
        setUserLoginInfo(modeMap);

        return new ModelAndView("index", modeMap);
    }

    /**
     * 进入查询角色数据主页
     * @Title: toList
     * @return
     */
    @RequestMapping(path="/to/listData")
    public ModelAndView listData(@RequestParam(defaultValue="") String roleName,
                                 @PageableDefault(page = 0, size = 10) Pageable page){
        ModelMap modeMap = new ModelMap();

        JSONObject pageResult = secRoleFeign.pageByName(roleName, page.getPageNumber(), page.getPageSize());
        modeMap.put("pageResult", pageResult.get("result"));

        return new ModelAndView(SYS_ROLE_INDEX_TABLE_URL, modeMap);
    }

}
