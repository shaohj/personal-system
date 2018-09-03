package com.psys.hobb.sec.config;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.psys.hobb.common.sys.bean.ServiceResponse;
import com.psys.hobb.common.sys.exception.SysException;
import com.psys.hobb.common.sys.exception.SysRuntimeException;
import com.psys.hobb.common.sys.util.constant.SysExceptionEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 定制mvc配置
 * 1.定制MappingJackson2HttpMessageConverter返回ServiceResponse对象
 * 2.定制ServiceResponseExceptionHandler捕获异常处理
 * 编  号：<br/>
 * 名  称：RestWebMvcConfigurer<br/>
 * 描  述：<br/>
 * 完成日期：2017年12月19日 上午8:58:32<br/>
 * 编码作者：shj<br/>
 */
public class RestWebMvcConfigurer implements WebMvcConfigurer {
    
    @Bean
    public FilterRegistrationBean custWebRequestTraceFilter(){
        FilterRegistrationBean frbean = new FilterRegistrationBean();
        frbean.setFilter(new CustWebRequestTraceFilter());
        frbean.setOrder(-101); //springSecurityFilterChain's order is -100 default
        return frbean;
    }

	@Aspect
	@Component
	public class HTTPMessageConverterConfigurerAspect {

		@SuppressWarnings("unchecked")

		@Around("execution(public void org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter.configureMessageConverters(..))")
		public void customConverters(ProceedingJoinPoint pjp){
			List<HttpMessageConverter<?>> converters = (List<HttpMessageConverter<?>>)pjp.getArgs()[0];

			RestMappingJackson2HttpMessageConverter jsonConverter = new RestMappingJackson2HttpMessageConverter();
			AllEncompassingFormHttpMessageConverter fromConverter = new AllEncompassingFormHttpMessageConverter();
			StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();

			//jsonConverter优先,string最低
			converters.add(jsonConverter);
			converters.add(fromConverter);
			converters.add(stringConverter);
		}

	}

	public class RestMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter{

		@Override
		protected void writeInternal(Object obj, Type type, HttpOutputMessage outMessage)
				throws IOException, HttpMessageNotWritableException {
			//排除type：springfox.documentation.*
			if(type.toString().indexOf("springfox.documentation") > 0){
				super.writeInternal(obj, type, outMessage);
			} else {
				Object result = obj instanceof ServiceResponse ? obj :
					new ServiceResponse<Object>(SysExceptionEnum.SUCCESS, "操作成功！", obj);

				super.writeInternal(result, type, outMessage);
			}
		}

	}

	@ControllerAdvice
	public class ServiceResponseExceptionHandler implements ResponseBodyAdvice<Object>{

		public final Logger logger = LoggerFactory.getLogger(ServiceResponseExceptionHandler.class);

		 /**
	     * 所有异常报错
	     * @param request
	     * @param exception
	     * @return
	     * @throws Exception
	     */
	    @ExceptionHandler(value = Exception.class)
	    public @ResponseBody ServiceResponse<String> allExceptionHandler(HttpServletRequest request, Exception exception) {
	    	logger.error("{}", exception);
			SysExceptionEnum senum = SysExceptionEnum.ERR500;
	    	String errorMsg = null;

	    	if(exception instanceof IllegalArgumentException){
	    		errorMsg = exception.getMessage();
	    	} else if(exception instanceof DataAccessException){
	    		errorMsg = null != exception.getCause().getCause() ?
	    				exception.getCause().getCause().toString() : exception.getMessage();
	    	} else if(exception instanceof SysException){
				SysException se = (SysException)exception;
				senum = SysExceptionEnum.getByCode(se.getCode());
				errorMsg = se.getMessage();
			} else if(exception instanceof SysRuntimeException){
				SysRuntimeException sre = (SysRuntimeException)exception;
				senum = SysExceptionEnum.getByCode(sre.getCode());
	    		errorMsg = sre.getMessage();
	    	} else {
	    		errorMsg = exception.getMessage();
	    	}
	        return new ServiceResponse<String>(senum, errorMsg);
	    }

	    /**
	     * 处理@ResponseBody为null的情况
	     */
		@Override
		public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectContentType,
				Class<? extends HttpMessageConverter<?>> selectConverterType, ServerHttpRequest request, ServerHttpResponse response) {
			if(null == body){
				return new ServiceResponse<Object>(SysExceptionEnum.SUCCESS, "操作成功！", null);
			}
			return body;
		}

		/**
	     * 处理@ResponseBody为null的情况
	     */
		@Override
		public boolean supports(MethodParameter arg0, Class<? extends HttpMessageConverter<?>> converterType) {
			if(RestMappingJackson2HttpMessageConverter.class.equals(converterType)){
				return true;
			}
			return false;
		}

	}
}
