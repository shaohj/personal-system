package com.psys.hobb.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

public class FastJsonUtils {

    /**
     * getServiceResult
     * @param jsonObject :
     * @param objClass :
     * @return T
     * @author SHJ
     * @since 2018/7/1 14:30
     */
    public static  <T> T getServiceResult(JSONObject jsonObject, Class<T> objClass){
        JSONObject jsonMap = new JSONObject((Map<String, Object>)jsonObject.get("result"));
        return jsonMap.toJavaObject(objClass);
    }

}
