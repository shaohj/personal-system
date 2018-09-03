package com.psys.hobb.sys.feign.sec.hystric;

import com.psys.hobb.sys.feign.sec.SecApplicationFeign;
import org.springframework.stereotype.Component;

@Component
public class SecApplicationFeignHystric implements SecApplicationFeign {
    @Override
    public void removeUserRedisCache() {

    }
}
