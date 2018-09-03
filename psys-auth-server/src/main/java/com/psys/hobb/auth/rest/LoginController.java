package com.psys.hobb.auth.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.auth.feign.TbCodeFeign;
import com.psys.hobb.common.http.constants.HttpConstants;
import com.psys.hobb.common.http.util.CallRemoteServiceUtils;
import com.psys.hobb.common.sys.util.RandomValidateCode;
import com.psys.hobb.common.sys.util.constant.SysRedisConstants;
import com.psys.hobb.redis.service.IRedisCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static com.psys.hobb.common.sys.util.constant.SysRedisConstants.*;
import static com.psys.hobb.common.sys.util.constant.UiPathConstants.*;

@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private IRedisCommonService redisCommonService;

	@Autowired
	private TbCodeFeign tbCodeFeign;

	@GetMapping(value = {"/login"})
	public ModelAndView toLogin(@RequestParam(required=false) boolean failed, HttpServletRequest request){
        ModelMap modeMap = new ModelMap();
        
        if(failed){
        	String msgKey = "SPRING_SECURITY_LAST_EXCEPTION";
        	Object bcObj = request.getSession().getAttribute(msgKey);
        	if(null != bcObj){
        		request.getSession().removeAttribute(msgKey);
        		BadCredentialsException bc = (BadCredentialsException) bcObj;
            	modeMap.put("loginErrorMsg", bc.getMessage());
        	}
        }
        
		return new ModelAndView("/login", modeMap);
	}

	/**
	 * http://localhost:18090/auth-server/index
	 * @return
	 */
	@GetMapping(value = {"/index1"})
	public ModelAndView toMyIndex(){
		ModelMap modeMap = new ModelMap();
		return new ModelAndView("/index", modeMap);
	}

	@GetMapping("/index")
	public String toIndex() {
		String loginSuccessUrl = redisCommonService.getString(AUTH_LOGIN_SUCCESS_URL_KEY);
		if(StringUtils.isEmpty(loginSuccessUrl)){
			/** 使用自定义的工具类,实现若远程服务调用不成功,则指定最大次数调用,直到成功为止 */
			CallRemoteServiceUtils callRSUtils = new CallRemoteServiceUtils(HttpConstants.REQUEST_SERVICE_TIMES_LIMIT);
			JSONObject codeResponse = callRSUtils.apply(tbCodeFeign, (secUserFeign) -> tbCodeFeign.getByCodeType(LOGIN_SUCCESS_URL));
			if(null == codeResponse){
				throw new IllegalArgumentException("加载系统首页异常," + CALL_SERVICE_NO_RESPONSE_MSG);
			}
			Map<String,Object> codeMap = codeResponse.getObject("result", Map.class);
			JSONObject codeJson = new JSONObject(codeMap);
			loginSuccessUrl = codeJson.getString("value");
			redisCommonService.putString(AUTH_LOGIN_SUCCESS_URL_KEY, loginSuccessUrl);
		}
		return "redirect:" + loginSuccessUrl;
	}

	@GetMapping(value = {"/login/randomValidateCode"})
	public void getRandomValidateCode(HttpServletRequest request, HttpServletResponse response){
		String sessionId  = request.getSession().getId();

		response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
		response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);

		RandomValidateCode randomValidateCode = new RandomValidateCode();
		try {
			String validValue = randomValidateCode.getRandcode(request, response);//输出图片方法

			redisCommonService.putString(SysRedisConstants.AUTH_VERCODE_KEY + sessionId, validValue);
		} catch (Exception e) {
			logger.error("Randcode Exception{}", e);
		}
	}

}
