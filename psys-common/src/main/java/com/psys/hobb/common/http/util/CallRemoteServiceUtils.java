package com.psys.hobb.common.http.util;

import com.alibaba.fastjson.JSONObject;

import java.util.function.Function;

/**
 * 请求远程服务工具类
 * 编  号：
 * 名  称：CallRemoteServiceUtils
 * 描  述：
 * 完成日期：2018/7/15 14:19
 * 编码作者：SHJ
 */
public class CallRemoteServiceUtils {

    /** 限制请求的次数 */
    private int limitTimes;

    public CallRemoteServiceUtils(int limitTimes){
        this.limitTimes = limitTimes;
    }

    public <T> JSONObject apply(T obj, Function<T, JSONObject> adapter){
        JSONObject response = null;
        for(int i=0; i<limitTimes; i++){
            response = adapter.apply(obj);
            if(null != response){
                break;
            }
        }
        return response;
    }

}
