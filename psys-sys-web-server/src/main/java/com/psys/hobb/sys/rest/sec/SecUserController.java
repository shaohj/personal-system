package com.psys.hobb.sys.rest.sec;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.common.sys.util.AssertUtil;
import com.psys.hobb.sys.rest.base.BaseController;
import com.psys.hobb.sys.feign.sec.SecUserFeign;
import com.psys.hobb.sys.feign.sec.TbCodeFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static com.psys.hobb.common.sys.util.constant.UiPathConstants.*;

@Controller
@RequestMapping(value = "sys/user", produces="application/json;charset=UTF-8")
public class SecUserController extends BaseController {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(SecUserController.class);

    @Autowired
    private TbCodeFeign tbCodeFeign;

    @Autowired
    private SecUserFeign secUserFeign;

    /**
     * 请求用户管理首页
     * @Title: toList
     * @return
     */
    @RequestMapping(path="/to/list")
    public ModelAndView toList(){
        ModelMap modeMap = new ModelMap();
        setUserLoginInfo(modeMap);

        modeMap.put("module", SYS_USER_INDEX_URL);
        JSONObject userStateListResp = tbCodeFeign.getCodesByCodeType(CODE_USER_STATE);
        modeMap.put("userStateList", userStateListResp.get("result"));

        return new ModelAndView("index", modeMap);
    }

    /**
     * 进入查询用户数据主页
     * @Title: toList
     * @return
     */
    @RequestMapping(path="/to/listData")
    public ModelAndView listData(@RequestParam(defaultValue="") String userName,
                                 @RequestParam(defaultValue="") String status,
                                 @RequestParam(defaultValue="") String groupCode,
                                 @PageableDefault(page = 0, size = 10) Pageable pageable){
        ModelMap modeMap = new ModelMap();

        JSONObject jsonObject = secUserFeign.pageBySearch(userName, status, groupCode, pageable.getPageNumber(), pageable.getPageSize());
        modeMap.put("pageResult", jsonObject.get("result"));

        return new ModelAndView(SYS_USER_INDEX_TABLE_URL, modeMap);
    }

    /**
     * 进入角色授予对话框
     */
    @RequestMapping(value = {"/role/to/grant/{userId}"})
    public ModelAndView toGrantRole(@PathVariable Integer userId) {
        AssertUtil.notEmptyStr(userId, "userId不能为空！");
        ModelMap modeMap = new ModelMap();
        JSONObject jsonObject = secUserFeign.getUserRoles(userId);
        modeMap.put("roleList", jsonObject.get("result"));
        return new ModelAndView(SYS_USER_ROLE_GRANT_URL, modeMap);
    }

}

