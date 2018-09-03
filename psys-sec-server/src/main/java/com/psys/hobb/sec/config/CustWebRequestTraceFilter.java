package com.psys.hobb.sec.config;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

/**
 * 在请求打印前处理日志,在请求打印后处理日志
 * 编  号：<br/>
 * 名  称：CustWebRequestTraceFilter<br/>
 * 描  述：<br/>
 * 完成日期：2018年3月23日 上午9:05:09<br/>
 * 编码作者：shj<br/>
 */
public class CustWebRequestTraceFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(CustWebRequestTraceFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse reqonse = (HttpServletResponse) res;
        logger.debug(">>>>>>>> print request info for request:{}", request.getRequestURL());
        
        getTrace(request).forEach((name, val) -> {
            logger.debug("{}={}", name, val);
        });
        
        try {
            chain.doFilter(request, reqonse);
        } finally {
            //确保finally能执行
            logger.debug(">>>>>>>> print response info for request:{}", request.getRequestURL());
            getResponseHeaders(reqonse).forEach((name, val) -> {
                logger.debug("{}={}", name, val);
            });
        }
    }
    
    private Map<String, Object> getResponseHeaders(final HttpServletResponse response){
        final Map<String, Object> headers = new LinkedHashMap<>();
        Collection<String> headerNames = response.getHeaderNames();
        
        if(CollectionUtils.isEmpty(headerNames)){
            headerNames.stream().forEach(headerName -> {
                String value = response.getHeader(headerName);
                headers.put(headerName, value);
            });
        }
        headers.put("status", String.valueOf(response.getStatus()));
        return headers;
    }
    
    private Map<String, Object> getTrace(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Principal userPrincipal = request.getUserPrincipal();
        Map<String, Object> trace = new LinkedHashMap<>();
        Map<String, Object> headers = new LinkedHashMap<>();
        headers.put("request", getRequestHeaders(request));
        
        trace.put("method", request.getMethod());
        trace.put("path", request.getRequestURI());
        trace.put("headers", headers);
        trace.put("principal", userPrincipal);

        trace.put("pathInfo", request.getPathInfo());
        trace.put("contextPath", request.getContextPath());
        
        trace.put("parameters", request.getParameterMap());
        
        trace.put("query", request.getQueryString());
        trace.put("authType", request.getAuthType());
        trace.put("remoteAddress", request.getRemoteAddr());
        trace.put("sessionId", null == session ? null : session.getId());
        trace.put("remoteUser", request.getRemoteUser());
        
        return trace;
    }
    
    private Map<String, Object> getRequestHeaders(HttpServletRequest request){
        Map<String, Object> headers = new LinkedHashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            headers.put(name, getHeaderValue(request, name));
        }
        return headers;
    }
    
    private Object getHeaderValue(HttpServletRequest request, String name){
        List<String> value = Collections.list(request.getHeaders(name));
        
        if(CollectionUtils.isEmpty(value)){
            return "";
        }
        
        if(1 == value.size()){
            return value.get(0);
        }
        return value;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("CustWebRequestTraceFilter...inited ");
    }
    
    @Override
    public void destroy() {}
    
}
